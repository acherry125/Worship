package game.Town.villagers.behaviors.builder;

import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.Town;
import game.Town.TownResources;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.*;

public class Builder extends ATask {
    public Builder(Villager villager) {
        super(villager);
    }

    @Override
    public TASKRESULT execute() {
        // If not on a mission
        if (!villager.getOnAMission()) {
            // System.out.println("get to mission");
            // Go to the spawn... to get a target.
            villager.setBtree(new GoToSpawn(villager));
            if (!board.getSpawnTile().isAtTile(villager.getPosition())) {
                // System.out.println("out of spawn proximity");
                villager.act();
            } else {
                for (RESOURCES r : villager.getResourcesInHand()) {
                    townResources.reduceNeed(r);
                }
                villager.getResourcesInHand().clear();
                if (villager.getTown().canSupportHut()) {
                    villager.setBtree(new TargetBuildablePlot(villager));
                    villager.act();
                    // villager is now on a mission.
                    villager.setOnAMission(true);
                }
            }
        } else {
            // villager is on a mission, but hasn't reached the target
            if (!villager.getTargetTile().isAtTile(villager.getPosition())) {
                ATask targetBuildablePlot = new TargetBuildablePlot(villager);
                villager.setBtree(targetBuildablePlot);
                targetBuildablePlot.execute();
                ATask approachTarget = new ApproachTarget(villager);
                villager.setBtree(approachTarget);
                approachTarget.execute();
            // has reached the target
            } else if (Town.single().canSupportHut()) {
                ATask buildhut = new BuildHut(villager);
                villager.setBtree(buildhut);
                buildhut.execute();
                townResources.reduceNeed(RESOURCES.WOOD, 10);
            }
        }
        return TASKRESULT.SUCCESS;
    }
}
