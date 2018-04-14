package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public class FollowGodBasedOnBelief extends ATask {

  public FollowGodBasedOnBelief(Villager villager) {
    super(villager);
  }

  @Override
  public TASKRESULT execute() {
    // listen to the god
    if (this.villager.getBelief() > 0) {
      return TASKRESULT.SUCCESS;
    // don't listen to the god
    } else {
      return TASKRESULT.FAILURE;
    }
  }
}
