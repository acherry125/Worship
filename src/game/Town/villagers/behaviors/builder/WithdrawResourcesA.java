package game.Town.villagers.behaviors.builder;

import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class WithdrawResourcesA extends ATask {
    WithdrawResourcesA(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        if (Town.single().canSupportHut()) {
            int numResourcesToAdd = Town.single().requiredWoodToBuildHut();
            for (int i = 0; i < numResourcesToAdd; i++) {
                Town.single().getTownResources().reduceNeed(RESOURCES.WOOD);
                villager.addResource(RESOURCES.WOOD);
            }
            return TASKRESULT.SUCCESS;
        }
        return TASKRESULT.FAILURE;
    }
}
