package game.Town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.villagers.VILLAGER_ROLES;
import game.Town.villagers.Villager;

public class Town {
    GodSim g;
    Board board;
    ArrayList<Villager> villagers = new ArrayList<Villager>();
    TownNeeds townNeeds;
    // what portion of the population should be each role
    HashMap<VILLAGER_ROLES, Integer> roleRatio = new HashMap<VILLAGER_ROLES, Integer>();
    HashMap<VILLAGER_ROLES, Integer> villagerCount = new HashMap<VILLAGER_ROLES, Integer>();

    public Town(GodSim g) {
        this.g = g;
        this.board = g.getBoard();
        townNeeds = new TownNeeds();
        initialize();
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
        Villager villager = new Villager(this, randomX, randomY, role, g);
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

    public TownNeeds getTownNeeds() {
        return townNeeds;
    }

    public boolean canSupportHut() {
        return townNeeds.get(RESOURCES.WOOD) <= 0;
    }

    public void draw() {
        manageVillagers();
        for (Villager villager : villagers) {
            villager.initializeBTree();
            villager.act();
            villager.draw();
        }
    }

    private void manageVillagers() {
        int numBuildings = board.numStructuresOnMap();
        if (villagers.size() < numBuildings / 2) {
            // Explorers spawn first, make sure there's always around 3 explorers for every builder
            if (villagerCount.get(VILLAGER_ROLES.EXPLORER) < roleRatio.get(VILLAGER_ROLES.EXPLORER) * villagerCount.get(VILLAGER_ROLES.BUILDER)) {
                spawn(VILLAGER_ROLES.EXPLORER);
            } else {
                // when there are 3n explorers, we can spawn a new builder
                spawn(VILLAGER_ROLES.BUILDER);
            }
        }
    }

    /**
     * Creates a new villager in the village.
     */
    private void initialize() {
        int ratioExplorer = 3;
        int ratioBuilder = 1;
        roleRatio.put(VILLAGER_ROLES.EXPLORER, ratioExplorer);
        roleRatio.put(VILLAGER_ROLES.BUILDER, ratioBuilder);

        villagerCount.put(VILLAGER_ROLES.EXPLORER, ratioExplorer);
        villagerCount.put(VILLAGER_ROLES.BUILDER, ratioBuilder);

        spawnMany(VILLAGER_ROLES.BUILDER, ratioBuilder);
        spawnMany(VILLAGER_ROLES.EXPLORER, ratioExplorer);
    }
}
