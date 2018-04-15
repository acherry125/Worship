package game.Town.villagers.behaviors.idle;

import game.Board.Board;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.general.AdvanceTowardsTargetA;
import game.Town.villagers.behaviors.general.SetTargetA;
import game.Town.villagers.behaviors.TASKRESULT;
import game.Town.villagers.behaviors.composites.Sequence;

public class IdleA extends ATask {
    public IdleA(Villager v) {
        super(v);
    }

    @Override
    public TASKRESULT execute() {
        ATask[] orderToRun = new ATask[]{
                new SetTargetA(villager, Board.single().getSpawnTile()),
                new AdvanceTowardsTargetA(villager)};

        ATask taskToRun = new Sequence(villager, orderToRun);
        return taskToRun.execute();
    }
}
