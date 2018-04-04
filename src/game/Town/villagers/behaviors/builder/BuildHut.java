package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;

public class BuildHut extends ATask {
    public BuildHut(Villager villager, TownResources townResources, Board board) {
        super(villager, townResources, board);
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
