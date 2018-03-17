package game.Town;

import processing.core.PVector;

public class TestTask extends Task {
  private Villager villager;

  public TestTask(Villager villager) {
    this.villager = villager;
  }

  @Override
  public int execute() {
    PVector target = villager.getTarget();
    if ((target.x != villager.getPosition().x)
            && (target.y != villager.getPosition().y)) {

      PVector directionToMove = target.sub(villager.getPosition());

      directionToMove.normalize();
      villager.setVector(directionToMove);
    }
    return 0;
  }
}
