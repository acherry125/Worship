package game.Town.villagers.behaviors.general;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;
import processing.core.PVector;

/**
 * Does the behavior of making the villager given go to the tile given.
 */
public class AdvanceTowardsTargetA extends ATask {
    PVector target;
    public AdvanceTowardsTargetA(Villager villager) {
        super(villager);
    }

    public TASKRESULT execute() {
        villager.move();
        return TASKRESULT.SUCCESS;
    }
}
