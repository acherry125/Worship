package game.Town;

import java.util.ArrayList;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;

public class Town {
    GodSim g;
    Board board;
    ArrayList<Villager> villagers = new ArrayList<Villager>();
    public Town(Board board, GodSim g) {
        this.board = board;
        this.g = g;
        initialize();
    }

    private void initialize() {
        Villager villager = spawn();
        //villager.setTarget(g.mouseX, g.mouseY);
        villager.setBtree(new TestTask(villager));
    }

    /**
     * Spawns a villager on the map at the spawn point
     * @return the spawned Villager
     */
    public Villager spawn() {
        ATile spawnTile = board.getSpawnTile();
        Villager villager = new Villager(g, spawnTile.getXPx(), spawnTile.getYPx(), VillagerRoles.EXPLORER);
        villagers.add(villager);
        return villager;
    }

    public void draw() {
        for (Villager villager: villagers) {
            villager.setTarget(g.mouseX, g.mouseY);
            villager.btree.execute();
            villager.draw();
        }
    }
}
