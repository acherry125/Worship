package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.Town;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public abstract class ATask {
    protected final Villager villager;
    private final TownResources townResources;
    protected final Board board;
    private final Blackboard blackboard;

    protected ATask(Villager villager) {
        this.villager = villager;
        this.townResources = Town.single().getTownResources();
        this.board = Board.single();
        this.blackboard = Blackboard.single();
    }

    public abstract TASKRESULT execute();
}
