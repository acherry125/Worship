package game.Town.villagers.behaviors.general;

import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class ReachedTargetC extends ATask {
    public ReachedTargetC(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        if(villager.getTargetTile().isAtTile(villager.getPosition())) {
            return TASKRESULT.SUCCESS;
        } else {
            return TASKRESULT.FAILURE;
        }
    }
}
