package game.Town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.villagers.VILLAGER_ROLES;
import game.Town.villagers.Villager;

public class Town {
    private GodSim g;
    private Board board;
    private LinkedList<Villager> villagers = new LinkedList<Villager>();
    private TownResources townResources;
    // what portion of the population should be each role
    private HashMap<VILLAGER_ROLES, Integer> roleRatio = new HashMap<VILLAGER_ROLES, Integer>();
    private HashMap<VILLAGER_ROLES, Integer> villagerCount = new HashMap<VILLAGER_ROLES, Integer>();

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

    public void draw() {
        manageVillagers();
        for (Villager villager : villagers) {
            villager.initializeBTree();
            villager.act();
            villager.draw();
        }
    }

    /**
     * Spawns a villager on the map at the spawn point
     *
     * @return the spawned Villager
     */
    public Villager spawn(VILLAGER_ROLES role) {
        ATile spawnTile = board.getSpawnTile();
        Random r = new Random();
        float randomMin = -200;
        float randomMax = 200;
        float randomX = spawnTile.getXPx() + randomMin + r.nextFloat() * (randomMax - randomMin);
        float randomY= spawnTile.getXPx() + randomMin + r.nextFloat() * (randomMax - randomMin);
        Villager villager = new Villager(randomX, randomY, role);
        villagers.add(villager);
        villagerCount.put(role, villagerCount.get(role) + 1);
        return villager;
    }

    /**
     * Spawns a number of villagers on the map at the spawn point
     *
     * @return the spawned Villager
     */
    public void spawnMany(VILLAGER_ROLES role, int quantity) {
        for (int i = 0; i < quantity; i++) {
            spawn(role);
        }
    }

    public boolean canSupportHut() {
        return townResources.get(RESOURCES.WOOD) > 10;
    }

    private void manageVillagers() {
        int numBuildings = board.numStructuresOnMap();
        if (villagers.size() < numBuildings / 2) {
            // Explorers spawn first, make sure there's always around 3 explorers for every builder
            if (villagerCount.get(VILLAGER_ROLES.GATHERER) < roleRatio.get(VILLAGER_ROLES.GATHERER) * villagerCount.get(VILLAGER_ROLES.BUILDER)) {
                spawn(VILLAGER_ROLES.GATHERER);
            } else {
                // when there are 3n explorers, we can spawn a new builder
                spawn(VILLAGER_ROLES.BUILDER);
            }
        }
    }

    /**
     * Creates a new villager in the village.
     */
    public void initialize() {
        this.board = Board.single();
        int ratioExplorer = 3;
        int ratioBuilder = 1;
        roleRatio.put(VILLAGER_ROLES.GATHERER, ratioExplorer);
        roleRatio.put(VILLAGER_ROLES.BUILDER, ratioBuilder);
        villagerCount.put(VILLAGER_ROLES.GATHERER, ratioExplorer);
        villagerCount.put(VILLAGER_ROLES.BUILDER, ratioBuilder);
        spawnMany(VILLAGER_ROLES.BUILDER, ratioBuilder);
        spawnMany(VILLAGER_ROLES.GATHERER, ratioExplorer);
    }
}
