package game.Town.villagers.behaviors.builder;

import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.GoToSpawn;
import game.Town.villagers.behaviors.Task;

public class Builder extends Task {
    public Builder(Villager villager, TownNeeds townNeeds, Board board) {
        super(villager, townNeeds, board);
    }

    @Override
    public int execute() {
        if (!villager.isOnAMission()) {
            // Go to the spawn... to get a target.
            villager.setBtree(new GoToSpawn(villager, townNeeds, board));
        } else {
            if (!board.getSpawnTile().isAtTile(villager.getPosition())) {
                // System.out.println("out of spawn proximity");
                villager.act();
            } else {
                for (RESOURCES r : villager.getResourcesInHand()) {
                    townNeeds.reduceNeed(r);
                }
                villager.getResourcesInHand().clear();
                //villager.setBtree();
                villager.act();

                // villager is now on a mission.
                villager.setOnAMission(true);
            }
        }
        return 1;
    }
}
