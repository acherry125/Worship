package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.Blackboard;
import game.Town.villagers.behaviors.TASKRESULT;
import processing.core.PVector;

public class TargetBuildablePlot extends ATask {

    public TargetBuildablePlot(Villager villager) {
        super(villager);
    }

    @Override
    public TASKRESULT execute() {
        // Ask the town for building orders
        ATile tileToBuildOn = board.getNextBuildableTile();
        villager.setTarget(tileToBuildOn.getPosition());
        villager.setTargetTile(tileToBuildOn);
        tileToBuildOn.highlightTile(0, 255, 255);
        return TASKRESULT.SUCCESS;
    }
}
