package game.Town.villagers.behaviors.composites;

import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.TASKRESULT;

public class Selector extends ATask {
    private final ATask[] tasks;
    public Selector(Villager villager, ATask[] tasks) {
        super(villager);
        this.tasks = tasks;
    }

    public TASKRESULT execute() {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].execute() == TASKRESULT.SUCCESS) {
                return TASKRESULT.SUCCESS;
            }
        }
        return TASKRESULT.FAILURE;
    }
}
