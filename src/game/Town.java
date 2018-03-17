package game;

import java.util.ArrayList;

import game.Tiles.ATile;
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
        Villager villager = new Villager(g, 200, 200, VillagerRoles.EXPLORER);
        //villager.setTarget(g.mouseX, g.mouseY);
        villager.setBtree(new TestTask(villager));
        villagers.add(villager);
    }

    public Villager spawn() {
        Villager villager = new Villager(g, 200, 200, VillagerRoles.EXPLORER);
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
