package game.Town.villagers.behaviors.gatherer;

import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class NotFilledInventoryC extends ATask {
    public NotFilledInventoryC(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        if(villager.getResourcesInHand().size() >= villager.getMaxResourcesToCarry()) {
            villager.getTargetTile().stopHighlight();
            return TASKRESULT.FAILURE;
        } else {
            return TASKRESULT.SUCCESS;
        }
    }
}
