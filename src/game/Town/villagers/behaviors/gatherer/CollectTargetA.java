package game.Town.villagers.behaviors.gatherer;

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
        String timerName = "collectres";
        int timer = new Random().nextInt(500) + 500;
        Integer timestamp = villager.getTimer(timerName);
        if (villager.getTargetTile().getResourceCount() != 0) {
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
}