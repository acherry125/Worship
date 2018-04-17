package game.Town.villagers.behaviors.general;

import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class UnmarkTileA extends ATask {
    public UnmarkTileA(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        villager.getTargetTile().stopHighlight();
        return TASKRESULT.SUCCESS;
    }
}
