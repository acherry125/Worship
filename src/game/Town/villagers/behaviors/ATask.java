package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;

public abstract class ATask {
    protected Villager villager;
    protected TownResources townResources;
    protected Board board;

    protected ATask(Villager villager, TownResources townResources, Board board) {
        this.villager = villager;
        this.townResources = townResources;
        this.board = board;
    }

    public abstract int execute();
}
