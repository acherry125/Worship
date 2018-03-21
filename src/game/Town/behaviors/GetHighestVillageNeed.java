package game.Town.behaviors;

import game.Board.ATile;
import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;
import processing.core.PVector;

public class GetHighestVillageNeed extends Task {

  public GetHighestVillageNeed(Villager villager, VillageNeeds villageNeeds, Board board) {
    super(villager, villageNeeds, board);
  }

  @Override
  public int execute() {

    // Get the village's highest need resource, and find the closest one to the villager.
    ATile resourceToGet;
    resourceToGet = board.getClosestResourceTile(villageNeeds.getHighestNeed(),
            villager.getPosition());

    // If you can get to it, Set that villager's target to get the resource.
    if (resourceToGet.isReachable()) {
      villager.setTarget(new PVector(resourceToGet.getXPx(), resourceToGet.getYPx()));
    }

    // Set the villager's target resource to this, to remember to collect it later.
    villager.setResourceToTarget(villageNeeds.getHighestNeed());
    villager.setTargetTile(resourceToGet);

    return 1;
  }
}
