package game.Town.behaviors;

import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;
import processing.core.PVector;

public class TestTask extends Task {

    public TestTask(Villager villager, VillageNeeds villageNeeds, Board board) {
        super(villager, villageNeeds, board);
    }

    @Override
    public int execute() {
        return 0;
    }
}
