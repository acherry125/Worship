package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;

public class ApproachTarget extends ATask {
  public ApproachTarget(Villager villager, TownNeeds townNeeds, Board board) {
    super(villager, townNeeds, board);
  }

  @Override
  public int execute() {
    villager.move();
    return 1;
  }
}
