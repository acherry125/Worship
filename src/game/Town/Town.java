package game.Town;

import java.util.ArrayList;
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

        for (Villager villager : villagers) {
            villager.initializeBTree();
            villager.act();
            villager.draw();
        }
    }

    /**
     * Creates a new villager in the village.
     */
    private void initialize() {
        spawnMany(VILLAGER_ROLES.BUILDER, 1);
        spawnMany(VILLAGER_ROLES.EXPLORER, 2);
    }
}
