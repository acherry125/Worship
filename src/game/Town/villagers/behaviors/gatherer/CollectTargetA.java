package game.Town.villagers.behaviors.gatherer;

import game.Board.ATile;
import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

import java.util.Random;

public class CollectTargetA extends ATask {
  public CollectTargetA(Villager v) {
    super(v);
  }

  public TASKRESULT execute() {
    if (villager.getTargetTile().peekResource() == villager.getResourceToTarget()) {


      villager.updateMaxResourcesToCarry();
      String timerName = "collectres";
      int timer = new Random().nextInt(500) + 500;
//        int timer = (int) (500 * villager.getBelief());
      Integer timestamp = villager.getTimer(timerName);
      ATile targetTile = villager.getTargetTile();
      RESOURCES res = targetTile.peekResource();
      if (villager.getTargetTile().getResourceCount() != 0 && res != RESOURCES.NONE && res != RESOURCES.CRAFTED) {
        if (timestamp != 0 && Town.single().getGodSim().millis() - timestamp > timer) {
          villager.addResource(villager.getTargetTile().getResource());
          villager.removeTimer(timerName);
          return TASKRESULT.SUCCESS;
        } else if (timestamp == 0) {
          villager.setTimerNow(timerName);
        }
        return TASKRESULT.FAILURE;
      }
      villager.getTargetTile().stopHighlight();
      villager.removeTimer(timerName);
      return TASKRESULT.FAILURE;
    }

    return TASKRESULT.FAILURE;
  }
}
