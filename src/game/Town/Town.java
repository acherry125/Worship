package game.Town;

import java.util.ArrayList;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.behaviors.FollowMouse;
import game.Town.behaviors.GoToSpawn;
import game.Town.behaviors.TestTask;

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
        Villager villager = new Villager(g, spawnTile.getXPx(), spawnTile.getYPx(),
                VILLAGER_ROLES.EXPLORER);
        villagers.add(villager);
        return villager;
    }

    //DELETE count code, just for testing.
    int count = 0;

    public void draw() {
        count += 1;
        // TODO: make new behavior, change code to move this to that default starting behavior.
        for (Villager villager : villagers) {
            if (count < 100) {
                villager.setBtree(new FollowMouse(villager, villageNeeds, board,
                        (int) g.getMouse().x,
                        (int) g.getMouse().y));
            } else {
                villager.setBtree(new GoToSpawn(villager, villageNeeds, board));
            }
            if (count > 200) {
                count = 0;
            }

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
