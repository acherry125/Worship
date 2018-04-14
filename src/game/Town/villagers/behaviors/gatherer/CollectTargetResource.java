package game.Town.villagers.behaviors.gatherer;

import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;

/**
 * Collects the resource until the villager can't carry anymore.
 */
public class CollectTargetResource extends ATask {
  public CollectTargetResource(Villager villager, TownResources townResources, Board board) {
    super(villager, townResources, board);
  }

  @Override
  public int execute() {
    // While the villager can still attain more resources....
    if (villager.getResourcesInHand().size() < villager.getMaxResourcesToCarry()) {
      // Deduct the resource from the tile.
      villager.getTargetTile().getResource();
      // Add the resource to the villager to carry.
      villager.addResource(villager.getResourceToTarget());
    } else {
      // default behavior when villager has no mission is to return to spawn with resources
      villager.getTargetTile().stopHighlight();
      villager.setResourceToTarget(RESOURCES.NONE);
      villager.setOnAMission(false);
    }
    return 1;
  }
}
