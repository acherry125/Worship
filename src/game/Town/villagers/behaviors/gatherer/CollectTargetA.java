package game.Town.villagers.behaviors.gatherer;

import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class CollectTargetA extends ATask {
    public CollectTargetA(Villager v) {
        super(v);
    }
    public TASKRESULT execute() {
        if (villager.getTargetTile().getResourceCount() != 0) {
            villager.addResource(villager.getTargetTile().getResource());
            return TASKRESULT.SUCCESS;
        }
        villager.getTargetTile().stopHighlight();
        return TASKRESULT.FAILURE;
    }
}
