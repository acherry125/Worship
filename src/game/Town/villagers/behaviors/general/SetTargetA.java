package game.Town.villagers.behaviors.general;

import game.Board.ATile;
import game.Board.Board;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.Blackboard;
import game.Town.villagers.behaviors.TASKRESULT;

public class SetTargetA extends ATask {
    ATile target;
    public SetTargetA(Villager v, ATile target) {
        super(v);
        this.target = target;
    }

    @Override
    public TASKRESULT execute() {
        target = Board.single().getTile(target.getPosition());
        villager.setTargetTile(target);
        villager.setResourceToTarget(target.peekResource());
        if (!target.isSpawner()) {
            target.highlightTile(229, 22, 94);
        }
        Blackboard.single().put(Integer.toString(villager.hashCode()), target);
        return TASKRESULT.SUCCESS;
    }
}
