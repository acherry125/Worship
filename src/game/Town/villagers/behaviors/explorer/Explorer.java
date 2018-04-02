package game.Town.villagers.behaviors.explorer;

import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.*;

public class Explorer  extends Task {

  public Explorer(Villager villager, TownNeeds townNeeds, Board board) {
    super(villager, townNeeds, board);
  }

  @Override
  public int execute() {

    // If not on a mission
    if (!villager.isOnAMission()) {

      // System.out.println("get to mission");
      // Go to the spawn... to get a target.
      villager.setBtree(new GoToSpawn(villager, townNeeds, board));

      if (!board.getSpawnTile().isAtTile(villager.getPosition())) {
        // System.out.println("out of spawn proximity");
        villager.act();
      } else {
        System.out.println(townNeeds);
        for (RESOURCES r : villager.getResourcesInHand()) {
          townNeeds.reduceNeed(r);
        }
        villager.getResourcesInHand().clear();
        System.out.println(townNeeds);
        System.out.println(board.getSpawnTile().getXPx() + "," + board.getSpawnTile().getYPx() + " | " + villager.getPosition());
        System.out.println("get a new target");
        villager.setBtree(new TargetHighestVillageNeed(villager, townNeeds, board));
        villager.act();

        // villager is now on a mission.
        villager.setOnAMission(true);
      }
    } else {

      if (!villager.getTargetTile().isAtTile(villager.getPosition())) {
        // System.out.println("on a mission to my target resource" + villager.getTarget());
        villager.setBtree(new ApproachTarget(villager, townNeeds, board));
        villager.act();
      } else {
        System.out.println("collecting resource: " + villager.getResourceToTarget());
        villager.setBtree(new CollectTargetResource(villager, townNeeds, board));
        villager.act();
      }

    }


    //TODO: add code to go back to spawn.

    return 1;
  }
}
