package game.Town.behaviors;

import game.Board.ATile;
import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;

public class ContinueOnTarget extends Task {
  public ContinueOnTarget(Villager villager, VillageNeeds villageNeeds, Board board) {
    super(villager, villageNeeds, board);
  }

  @Override
  public int execute() {
    villager.move();
    return 1;
  }
}
