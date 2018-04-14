package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.ATask;
import game.Town.villagers.behaviors.Blackboard;
import game.Town.villagers.behaviors.TASKRESULT;

public class BuildHut extends ATask {
    public BuildHut(Villager villager) {
        super(villager);
    }

    @Override
    public TASKRESULT execute() {
        ATile targTile = villager.getTargetTile();
        board.buildHutOnTile(targTile);
        // we've built the hut, villager is done
        villager.setOnAMission(false);
        targTile.stopHighlight();
        return TASKRESULT.SUCCESS;
    }
}
