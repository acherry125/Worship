package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import processing.core.PVector;

public class TargetBuildablePlot extends ATask {

    public TargetBuildablePlot(Villager villager, TownResources townResources, Board board) {
        super(villager, townResources, board);
    }

    @Override
    public int execute() {

        // Get the village's highest need resource, and find the closest one to the villager.
        ATile tileToBuildOn = board.getClosestBuildableTile(villager.getPosition());

        // If you can get to it, Set that villager's target to get the resource.
        villager.setTarget(new PVector(tileToBuildOn.getXPx(), tileToBuildOn.getYPx()));

        villager.setTargetTile(tileToBuildOn);
        tileToBuildOn.highlightTile(0, 255, 255);
        return 1;
    }
}
