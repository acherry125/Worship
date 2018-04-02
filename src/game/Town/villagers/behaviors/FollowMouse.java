package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import processing.core.PVector;

/**
 * Behavior just for sample structure.
 */
public class FollowMouse extends Task {
    int mouseX;
    int mouseY;

    public FollowMouse(Villager villager, TownNeeds townNeeds, Board board,
                       int mouseX, int mouseY) {

        super(villager, townNeeds, board);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public int execute() {
        this.villager.setTarget(new PVector(mouseX, mouseY));
        this.villager.move();
        return 0;
    }
}
