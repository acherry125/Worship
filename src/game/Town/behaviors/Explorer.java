package game.Town.behaviors;

import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.VillageNeeds;
import game.Town.Villager;

public class Explorer  extends Task {

  private float inProximity;

  public Explorer(Villager villager, VillageNeeds villageNeeds, Board board) {
    super(villager, villageNeeds, board);
    this.inProximity = board.getG().CELL_W / 2;
  }

  @Override
  public int execute() {

    // If not on a mission
    if (!villager.isOnAMission()) {

      System.out.println("get to mission");
      // Go to the spawn... to get a target.
      villager.setBtree(new GoToSpawn(villager, villageNeeds, board));

      if (!board.getSpawnTile().isAtTile(villager.getPosition())) {
        System.out.println("out of spawn proximity");
        villager.act();
      } else {
        System.out.println(board.getSpawnTile().getXPx() + "," + board.getSpawnTile().getYPx() + " | " + villager.getPosition());
        System.out.println("get a new target");
        villager.setBtree(new GetHighestVillageNeed(villager, villageNeeds, board));
        villager.act();

        // villager is now on a mission.
        villager.setOnAMission(true);
      }
    } else {

      if (!villager.getTargetTile().isAtTile(villager.getPosition())) {
        System.out.println("on a mission to my target resource" + villager.getTarget());
        villager.setBtree(new ContinueOnTarget(villager, villageNeeds, board));
        villager.act();
      } else {
        System.out.println("collecting resource: " + villager.getResourceToTarget());
        villager.setBtree(new CollectTargetResource(villager, villageNeeds, board));
        villager.act();
      }

    }


    //TODO: add code to go back to spawn.

    return 1;
  }
}
