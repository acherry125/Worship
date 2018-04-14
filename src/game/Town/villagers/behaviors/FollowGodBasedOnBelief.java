package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public class FollowGodBasedOnBelief extends ATask {

  public FollowGodBasedOnBelief(Villager villager, TownResources townResources, Board board) {
    super(villager, townResources, board);
  }

  @Override
  public int execute() {
    // listen to the god
    if (this.villager.getBelief() > 0) {
      return 1;
    // don't listen to the god
    } else {
      return -1;
    }
  }
}
