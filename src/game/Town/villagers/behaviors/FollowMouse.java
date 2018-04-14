package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import processing.core.PVector;

/**
 * Behavior just for sample structure.
 */
public class FollowMouse extends ATask {
    int mouseX;
    int mouseY;

    public FollowMouse(Villager villager) {
        super(villager);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public TASKRESULT execute() {
        this.villager.setTarget(new PVector(mouseX, mouseY));
        this.villager.move();
        return TASKRESULT.SUCCESS;
    }
}
