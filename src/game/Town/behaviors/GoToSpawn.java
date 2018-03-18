package game.Town.behaviors;

import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;

/**
 * Does the behavior for the villager given to go to the spawn.
 */
public class GoToSpawn extends GoToTile {

  public GoToSpawn(Villager villager, VillageNeeds villageNeeds, Board board) {
    super(villager, villageNeeds, board);
    super.goToTile(board.getSpawnTile());
  }

  @Override
  public int execute() {
    this.villager.move();
    return 1;
  }
}
