package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;

public class BuildHut extends ATask {
    public BuildHut(Villager villager, TownNeeds townNeeds, Board board) {
        super(villager, townNeeds, board);
    }

    @Override
    public int execute() {
        ATile targTile = villager.getTargetTile();
        board.buildHutOnTile(targTile);
        villager.setOnAMission(false);
        targTile.stopHighlight();
        return 1;
    }
}
