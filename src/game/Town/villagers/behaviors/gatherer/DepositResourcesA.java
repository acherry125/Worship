package game.Town.villagers.behaviors.gatherer;

import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

import java.util.Deque;

public class DepositResourcesA extends ATask {
    public DepositResourcesA(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        Deque<RESOURCES> resources = villager.getResourcesInHand();
        // out of resources to deposit, switch to idle mode
        if (resources.size() <= 0) {
            villager.setIdle(true);
            return TASKRESULT.FAILURE;
        }
        // have a resource to deposit
        int size = resources.size();
        for (int i = 0; i < size; i++) {
            Town.single().getTownResources().raiseNeed(resources.poll(), villager.getBelief());
        }
        return TASKRESULT.SUCCESS;
    }
}
