package game.Town;

import java.util.*;

import game.Board.ATile;
import game.Board.Board;
import game.Board.structures.HutTile;
import game.Calendar;
import game.GodSim;
import game.Town.villagers.VILLAGER_ROLES;
import game.Town.villagers.Villager;

public class Town {
    private final GodSim g;
    private Board board;
    private final LinkedList<Villager> villagers = new LinkedList<Villager>();
    private final LinkedList<Villager> toDieQueue = new LinkedList<Villager>();
    private final TownResources townResources;
    // what portion of the population should be each role
    private final HashMap<VILLAGER_ROLES, Integer> roleCounts = new HashMap<VILLAGER_ROLES, Integer>();
    private final int numWoodToBuildHut = 10;
    private String currentMonth;
    private final int foodPerPerson = 3;
    private final int waterPerPerson = 3;
    private int godPowerTimer = 0;
    private final int godUsedPowerInterval = 5000;
    private boolean powerUsedRecently = false;

    private static Town ourInstance;

    public static Town create(GodSim g) {
        ourInstance = new Town(g);
        return ourInstance;
    }

    public static Town single() {
        return ourInstance;
    }

    private Town(GodSim g) {
        this.g = g;
        townResources = new TownResources();
        currentMonth = Calendar.single().getMonth();
    }

    /*** GETTERS ***/
    public TownResources getTownResources() {
        return townResources;
    }
    public List<Villager> getVillagers() {
        return this.villagers;
    }
    public GodSim getGodSim() {
        return this.g;
    }
    public int requiredWoodToBuildHut() {
        return numWoodToBuildHut;
    }
    public int getNumVillagersInRole(VILLAGER_ROLES role) {
        return roleCounts.get(role);
    }
    public void incrementVillagerRoleCount(VILLAGER_ROLES role) {
        if (roleCounts.containsKey(role)) {
            roleCounts.put(role, roleCounts.get(role) + 1);
        } else {
            roleCounts.put(role, 1);
        }
    }
    public void decrementVillagerRoleCount(VILLAGER_ROLES role) {
        if (roleCounts.containsKey(role)) {
            roleCounts.put(role, roleCounts.get(role) - 1);
        } else {
            roleCounts.put(role, 0);
        }
    }

    public void draw() {
        manageVillagers(false);
        for (Villager villager : villagers) {
            villager.initializeBTree();
            villager.act();
            villager.draw();
        }
        killVillagers();
        checkGodPowersUsed();
    }

    /**
     * Spawns a villager on the map at the spawn point
     *
     * @return the spawned Villager
     */
    private void spawn(VILLAGER_ROLES role, ATile spawn) {
        Random r = new Random();
        float x = spawn.getXPx();
        float y = spawn.getYPx();
        Villager villager = new Villager(x, y, role);
        villagers.add(villager);
        incrementVillagerRoleCount(role);
    }

    public void queueVillagerDeath(Villager v) {
        toDieQueue.add(v);
    }
    private void killVillagers() {
        int count = toDieQueue.size();
        for (int i = 0; i < count; i++) {
            Villager v = toDieQueue.poll();
            if (v != null) {
                VILLAGER_ROLES role = v.getRole();
                decrementVillagerRoleCount(role);
                v.getTargetTile().stopHighlight();
                villagers.remove(v);
            }
        }
    }

    public boolean canSupportHut() {
        if (board.getHuts().size() < 10) {
            return townResources.get(RESOURCES.WOOD) > 10;
        } else {
            return townResources.get(RESOURCES.WOOD) > 15 && townResources.get(RESOURCES.STONE) > 5;
        }
    }

    /**
     * Reduces god's belief every 30 seconds in every villager.
     * God's belief will increase for every villager if a power is used (code is triggered
     * in the power's package), which can effect the timer.
     */
    private void checkGodPowersUsed() {
        if (Calendar.single().millis() - godPowerTimer > godUsedPowerInterval) {
            this.powerUsedRecently = false;
            godPowerTimer = Calendar.single().millis();
            int multiplier = (Calendar.single().millis() - godPowerTimer) % godUsedPowerInterval;
            multiplier = Math.max(multiplier, 4); // Cap the multiplier by 4
            for (Villager villager : villagers) {
                villager.changeBelief(g.map((float) Math.random(), 0, 1, -0.01f,
                        // if 1, -0.15
                        // if 2, -0.30, etc, up to -0.6
                        g.map(multiplier, 1, 4, -0.05f, -0.1f)));
            }
        }
    }

    /**
     * Resets the god power timer to the current time to avoid decreasing the belief
     * in checkGodPowerUsed.
     */
    public void resetGodPowerTimer() {
        this.godPowerTimer = Calendar.single().millis();
    }

    public void powerWasUsedRecently() {
        this.powerUsedRecently = true;
    }

    public boolean powerUsedRecently() {
        return this.powerUsedRecently;
    }

    private void manageVillagers(boolean initial) {
        for (Villager v : villagers) {
            if (v.getBelief() <= 0.1) {
                v.die();
            }
        }
        ArrayList<HutTile> huts = board.getHuts();
        int numHuts = huts.size();
        if (initial || !Objects.equals(Calendar.single().getMonth(), currentMonth)) {
            currentMonth = Calendar.single().getMonth();
            int count = villagers.size();
            // go through existing people, see which survive, spawn more if sustainable
            for (int i = 0; i < Math.max(count, numHuts); i++) {
                int foodCount = townResources.get(RESOURCES.FOOD);
                int waterCount = townResources.get(RESOURCES.WATER);
                // still checking the existing villagers
                if (i < count && i < numHuts) {
                    // remove the villager from the end
                    Villager currVill = villagers.get(i);
                    if (foodCount >= foodPerPerson && waterCount >= waterPerPerson) {
                        HutTile currHut = huts.get(i);
                        // add the villager back at the beginning so it's in the same order
                        townResources.reduceNeed(RESOURCES.FOOD, foodPerPerson);
                        townResources.reduceNeed(RESOURCES.WATER, waterPerPerson);
                        currVill.setVillageRole(currHut.supportedRole());

                    } else {
                        currVill.die();
                    }
                } else if (i < count) {
                    // villagers need a home to survive, these are extra
                    Villager currVill = villagers.get(i);
                    // if there's no food, kill them
                    currVill.getTargetTile().stopHighlight();
                    currVill.die();
                } else if (i < numHuts && foodCount >= foodPerPerson && waterCount >= waterPerPerson) {
                    HutTile currHut = huts.get(i);
                    // open slots that villagers can fill
                    townResources.reduceNeed(RESOURCES.FOOD, foodPerPerson);
                    townResources.reduceNeed(RESOURCES.WATER, waterPerPerson);
                    spawn(currHut.supportedRole(), currHut);
                }
            }
        }
    }

    /**
     * Creates a new villager in the village.
     */
    public void initialize() {
        this.board = Board.single();
        manageVillagers(true);
    }
}
