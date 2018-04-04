package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.Task;

public class BuildHut extends Task {
    public BuildHut(Villager villager, TownNeeds townNeeds, Board board) {
        super(villager, townNeeds, board);
    }

    @Override
    public int execute() {
        ATile targTile = villager.getTargetTile();
        board.buildHutOnTile(targTile);
        villager.setOnAMission(false);

        return 1;
    }
}
