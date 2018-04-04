package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.GoToTile;
import game.Town.villagers.behaviors.Task;
import processing.core.PVector;

public class TargetBuildablePlot extends Task {

    public TargetBuildablePlot(Villager villager, TownNeeds townNeeds, Board board) {
        super(villager, townNeeds, board);
    }

    @Override
    public int execute() {

        // Get the village's highest need resource, and find the closest one to the villager.
        ATile tileToBuildOn = board.getClosestBuildableTile(villager.getPosition());

        // If you can get to it, Set that villager's target to get the resource.
        villager.setTarget(new PVector(tileToBuildOn.getXPx(), tileToBuildOn.getYPx()));

        villager.setTargetTile(tileToBuildOn);

        return 1;
    }
}