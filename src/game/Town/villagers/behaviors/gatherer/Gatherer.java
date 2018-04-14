package game.Town.villagers.behaviors.gatherer;

import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.*;

public class Gatherer extends ATask {

  private float increaseBeliefRateEachFrame = 100f * board.huts;

  public Gatherer(Villager villager, TownResources townResources, Board board) {
    super(villager, townResources, board);
  }

  @Override
  public int execute() {

    // If not on a mission
    if (!villager.getOnAMission()) {

      // System.out.println("get to mission");
      // Go to the spawn... to get a target.
      villager.setBtree(new GoToSpawn(villager, townResources, board));

      if (!board.getSpawnTile().isAtTile(villager.getPosition())) {
        // System.out.println("out of spawn proximity");
        villager.act();
      } else {
        for (RESOURCES r : villager.getResourcesInHand()) {
          townResources.raiseNeed(r);
          float distanceTraveled = villager.getTarget().sub(villager.getPosition()).mag();
          villager.changeBelief(-distanceTraveled);
        // System.out.println(distanceTraveled);
        // System.out.println(villager.getBelief());
        }
        if (villager.getBelief() > 1000000) {
          villager.setBelief(500000);
        }
        villager.getResourcesInHand().clear();


        if (new FollowGodBasedOnBelief(villager, townResources, board).execute() >= 0) {
          ATask targetTownNeed = new TargetTownNeed(villager, townResources, board);
          villager.setBtree(targetTownNeed);
          targetTownNeed.execute();
          // villager is now on a mission.
          villager.setOnAMission(true);
        } else {
          villager.changeBelief(increaseBeliefRateEachFrame);
        }
      }
    } else {

      if (!villager.getTargetTile().isAtTile(villager.getPosition())) {
        // System.out.println("on a mission to my target resource" + villager.getTarget());
        ATask targetTown = new TargetTownNeed(villager, townResources, board);
        villager.setBtree(targetTown);
        targetTown.execute();
        ATask target = new ApproachTarget(villager, townResources, board);
        villager.setBtree(target);
        target.execute();
      } else {
        ATask collect = new CollectTargetResource(villager, townResources, board);
        villager.setBtree(collect);
        collect.execute();
      }
    }
    return 1;
  }
}
