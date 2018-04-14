package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public class ApproachTarget extends ATask {
  public ApproachTarget(Villager villager) {
    super(villager);
  }

  @Override
  public TASKRESULT execute() {
    villager.move();
    return TASKRESULT.SUCCESS;
  }
}
