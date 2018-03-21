package game.Town.behaviors;

import game.Board.ATile;
import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;

/**
 * Collects the resource until the villager can't carry anymore.
 */
public class CollectTargetResource extends Task {
  public CollectTargetResource(Villager villager, VillageNeeds villageNeeds, Board board) {
    super(villager, villageNeeds, board);
  }

  @Override
  public int execute() {

    // While the villager can still attain more resources....
    if (villager.getResourcesInHand().size() < villager.getMaxResourcesToCarry()) {
      // Get the tile that the villager is supposed to be at.

      // Deduct the resource from the tile.
      villager.getTargetTile().getResource();

      // Add the resource to the villager to carry.
      villager.addResource(villager.getResourceToTarget());
    } else {
      villager.setOnAMission(false);
    }
    return 1;
  }
}
