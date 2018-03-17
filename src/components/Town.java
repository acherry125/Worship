package components;

import java.util.ArrayList;

import components.Tiles.ATile;
import processing.core.PApplet;

public class Town {
    PApplet g;
    ATile[][] board;
    ATile[][] buildings;
    ArrayList<Villager> villagers = new ArrayList<Villager>();
    public Town(ATile[][] board, PApplet g) {
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

    public void draw() {
        for (Villager villager: villagers) {
            villager.setTarget(g.mouseX, g.mouseY);
            villager.btree.execute();
            villager.draw();
        }
    }
}
