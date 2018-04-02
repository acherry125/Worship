package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;

public abstract class Task {
    protected Villager villager;
    protected TownNeeds townNeeds;
    protected Board board;

    public Task(Villager villager, TownNeeds townNeeds, Board board) {
        this.villager = villager;
        this.townNeeds = townNeeds;
        this.board = board;
    }

    public abstract int execute();
}
