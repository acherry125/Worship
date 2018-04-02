package game.Town.villagers.behaviors.explorer;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.Task;
import processing.core.PVector;

public class TargetHighestVillageNeed extends Task {

  public TargetHighestVillageNeed(Villager villager, TownNeeds townNeeds, Board board) {
    super(villager, townNeeds, board);
  }

  @Override
  public int execute() {

    // Get the village's highest need resource, and find the closest one to the villager.
    ATile resourceToGet;
    resourceToGet = board.getClosestResourceTile(townNeeds.getHighestNeed(),
            villager.getPosition());

    // If you can get to it, Set that villager's target to get the resource.
    villager.setTarget(new PVector(resourceToGet.getXPx(), resourceToGet.getYPx()));

    // Set the villager's target resource to this, to remember to collect it later.
    villager.setResourceToTarget(townNeeds.getHighestNeed());
    villager.setTargetTile(resourceToGet);

    return 1;
  }
}
