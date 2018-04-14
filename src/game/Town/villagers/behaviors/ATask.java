package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.Town;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public abstract class ATask {
    protected Villager villager;
    protected TownResources townResources;
    protected Board board;
    protected Blackboard blackboard;

    protected ATask(Villager villager) {
        this.villager = villager;
        this.townResources = Town.single().getTownResources();
        this.board = Board.single();
        this.blackboard = Blackboard.single();
    }

    public abstract TASKRESULT execute();
}
