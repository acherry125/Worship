package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public class ApproachTarget extends ATask {
  public ApproachTarget(Villager villager, TownResources townResources, Board board) {
    super(villager, townResources, board);
  }

  @Override
  public int execute() {
    villager.move();
    return 1;
  }
}
