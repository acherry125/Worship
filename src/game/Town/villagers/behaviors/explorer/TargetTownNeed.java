package game.Town.villagers.behaviors.explorer;

import game.Board.ATile;
import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import processing.core.PVector;

public class TargetTownNeed extends ATask {

  public TargetTownNeed(Villager villager, TownNeeds townNeeds, Board board) {
    super(villager, townNeeds, board);
  }

  @Override
  public int execute() {

    RESOURCES resourceNeeded;
    if (villager.getResourceToTarget() == RESOURCES.NONE) {
      resourceNeeded = townNeeds.nextResourceToCollect();
    } else {
      resourceNeeded = villager.getResourceToTarget();
    }

    // Get the village's highest need resource, and find the closest one to the villager.
    ATile resourceTileToGet;

    resourceTileToGet = board.getClosestResourceTile(resourceNeeded, villager.getPosition());

    resourceTileToGet.highlightTile(255, 0, 0);

    // If you can get to it, Set that villager's target to get the resource.
    villager.setTarget(new PVector(resourceTileToGet.getXPx(), resourceTileToGet.getYPx()));

    // Set the villager's target resource to this, to remember to collect it later.
    villager.setResourceToTarget(resourceNeeded);
    villager.setTargetTile(resourceTileToGet);

    return 1;
  }
}
