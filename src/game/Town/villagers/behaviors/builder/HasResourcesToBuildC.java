package game.Town.villagers.behaviors.builder;

import game.Town.Town;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class HasResourcesToBuildC extends ATask {
    HasResourcesToBuildC(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        if (villager.getResourcesInHand().size() >= Town.single().requiredWoodToBuildHut()) {
            return TASKRESULT.SUCCESS;
        } else {
            return TASKRESULT.FAILURE;
        }
    }
}
