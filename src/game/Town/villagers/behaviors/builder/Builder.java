package game.Town.villagers.behaviors.builder;

import game.Board.ATile;
import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.*;
import game.Town.villagers.behaviors.composites.Selector;
import game.Town.villagers.behaviors.composites.Sequence;
import game.Town.villagers.behaviors.gatherer.NotFilledInventoryC;
import game.Town.villagers.behaviors.general.AdvanceTowardsTargetA;
import game.Town.villagers.behaviors.general.ReachedTargetC;
import game.Town.villagers.behaviors.general.SetTargetA;

public class Builder extends ATask {
    public Builder(Villager villager) {
        super(villager);
    }

    @Override
    public TASKRESULT execute() {

        String thisId = Integer.toString(villager.hashCode());
        ATile targetTile = (ATile) Blackboard.single().get(thisId);
        ATile spawner = Board.single().getSpawnTile();

        if (targetTile == null || targetTile.peekResource() == RESOURCES.CRAFTED || targetTile == spawner) {
            targetTile = board.getNextBuildableTile();
            Blackboard.single().put(thisId, targetTile);
        }

        Villager v = villager;

        new ReachedTargetC(v);

        ATask build = new Sequence(v, new ATask[]{new SetTargetA(v, targetTile), new ReachedTargetC(v), new BuildHutA(v), new SetTargetA(v, spawner)});
        ATask goToBuild = new Sequence(v, new ATask[]{new AdvanceTowardsTargetA(v)});
        ATask buildOrGoToBuild = new Selector(v, new ATask[]{build, goToBuild});
        ATask prereqsFilled = new Sequence(v, new ATask[]{new HasResourcesToBuildC(v), buildOrGoToBuild});
        ATask prerequisites = new Sequence(v, new ATask[]{new NotFilledInventoryC(v), new WithdrawResourcesA(v)});

        ATask topSelector = new Selector(v, new ATask[]{prereqsFilled, prerequisites});

        return topSelector.execute();
    }
}
