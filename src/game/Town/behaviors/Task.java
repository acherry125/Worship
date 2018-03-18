package game.Town.behaviors;

import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;

public abstract class Task {
  protected Villager villager;
  protected VillageNeeds villageNeeds;
  protected Board board;

  public Task(Villager villager, VillageNeeds villageNeeds, Board board) {
    this.villager = villager;
    this.villageNeeds = villageNeeds;
    this.board = board;
  }

  public abstract int execute();
}
