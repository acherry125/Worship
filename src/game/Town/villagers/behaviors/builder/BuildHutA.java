package game.Town.villagers.behaviors.builder;

import game.Calendar;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class BuildHutA extends ATask {
    public BuildHutA(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        String timerName = "buildhut";
        int timer = 5000;
        Integer timestamp = villager.getTimer(timerName);
        if (timestamp != 0 && Calendar.single().millis() - timestamp > timer) {
            board.buildHutOnTile(villager.getTargetTile());
            villager.getTargetTile().stopHighlight();
            villager.getResourcesInHand().clear();
            villager.removeTimer(timerName);
            return TASKRESULT.SUCCESS;
        } else if (timestamp == 0) {
            villager.setTimerNow(timerName);
        }
        return TASKRESULT.FAILURE;
    }
}
