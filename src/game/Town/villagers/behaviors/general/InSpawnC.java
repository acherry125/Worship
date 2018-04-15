package game.Town.villagers.behaviors.general;

import game.Board.ATile;
import game.Board.Board;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class InSpawnC extends ATask {
    public InSpawnC(Villager v) {
        super(v);
    }
    @Override
    public TASKRESULT execute() {
        ATile spawn = Board.single().getSpawn();
        if (spawn.isAtTile(villager.getPosition())) {
            return TASKRESULT.SUCCESS;
        } else {
            return TASKRESULT.FAILURE;
        }
    }
}
