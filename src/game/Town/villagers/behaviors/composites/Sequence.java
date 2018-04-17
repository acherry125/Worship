package game.Town.villagers.behaviors.composites;

import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class Sequence extends ATask {
    private final ATask[] tasks;
    public Sequence(Villager villager, ATask[] tasks) {
        super(villager);
        this.tasks = tasks;
    }

    public TASKRESULT execute() {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].execute() == TASKRESULT.FAILURE) {
                return TASKRESULT.FAILURE;
            }
        }
        return TASKRESULT.SUCCESS;
    }
}
