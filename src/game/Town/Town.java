package game.Town;

import java.util.ArrayList;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.behaviors.Explorer;
import game.Town.behaviors.FollowMouse;
import game.Town.behaviors.GoToSpawn;

import static processing.core.PApplet.println;

public class Town {
    GodSim g;
    Board board;
    ArrayList<Villager> villagers = new ArrayList<Villager>();
    VillageNeeds villageNeeds;

    public Town(GodSim g) {
        this.g = g;
        this.board = g.getBoard();
        initialize();
        villageNeeds = new VillageNeeds();
    }

    /**
     * Spawns a villager on the map at the spawn point
     *
     * @return the spawned Villager
     */
    public Villager spawn() {
        ATile spawnTile = board.getSpawnTile();
        Villager villager = new Villager(g, spawnTile.getXPx() - 200, spawnTile.getYPx() - 200,
                VILLAGER_ROLES.EXPLORER);
        villagers.add(villager);

        Villager villager2 = new Villager(g, spawnTile.getXPx() -230, spawnTile.getYPx() - 230,
                VILLAGER_ROLES.EXPLORER);
        villagers.add(villager2);

        // TODO: can make village needs itself increment, but at what rate? putting here for now.

        return villager;
    }

    public void draw() {

        // TODO: make new behavior, change code to move this to that default starting behavior.
        for (Villager villager : villagers) {
            villager.setBtree(new Explorer(villager, villageNeeds, board));
            villager.act();
            villager.draw();
        }
    }

    /**
     * Creates a new villager in the village.
     */
    private void initialize() {
        spawn();
    }
}
