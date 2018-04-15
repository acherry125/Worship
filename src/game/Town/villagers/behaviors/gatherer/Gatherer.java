package game.Town.villagers.behaviors.gatherer;

import game.Board.ATile;
import game.Board.Board;
import game.Town.RESOURCES;
import game.Town.villagers.Villager;
import game.Town.villagers.behaviors.*;
import game.Town.villagers.behaviors.composites.Selector;
import game.Town.villagers.behaviors.composites.Sequence;
import game.Town.villagers.behaviors.general.AdvanceTowardsTargetA;
import game.Town.villagers.behaviors.general.InSpawnC;
import game.Town.villagers.behaviors.general.ReachedTargetC;
import game.Town.villagers.behaviors.general.SetTargetA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Gatherer extends ATask {

  private float increaseBeliefRateEachFrame = 100f * board.huts;

  public Gatherer(Villager villager) {
    super(villager);
  }

  @Override
  public TASKRESULT execute() {

    ArrayList<RESOURCES> possibleResources = new ArrayList<RESOURCES>(Arrays.asList(RESOURCES.FOOD, RESOURCES.WOOD, RESOURCES.STONE, RESOURCES.WATER));
    RESOURCES resource = possibleResources.get(new Random().nextInt(possibleResources.size()));
    String thisId = Integer.toString(villager.hashCode());
    ATile targetTile = (ATile) Blackboard.single().get(thisId);
    if (targetTile == null || !possibleResources.contains(targetTile.peekResource()) || targetTile.getResourceCount() == 0) {
        targetTile = board.getClosestResourceTile(resource, villager.getPosition());
        if (targetTile == null) {
            // TODO, what to do when resources all run out?
            return TASKRESULT.FAILURE;
        }
        Blackboard.single().put(thisId, targetTile);
    }

    Villager v = villager;

    // TODO, rewrite this so it's easy to follow the trees
    ATask reachedTargetCollectSeqDone = new Sequence(v, new ATask[]{new ReachedTargetC(v), new CollectTargetA(v)});
    ATask findThenAdvanceSeqDone = new Sequence(v, new ATask[]{new SetTargetA(v, targetTile), new AdvanceTowardsTargetA(v)});
    ATask advanceOrCollectSel = new Selector(v, new ATask[]{reachedTargetCollectSeqDone, findThenAdvanceSeqDone});
    ATask fullInventoryNotAtSpawnSeqDone = new Sequence(v, new ATask[]{new SetTargetA(v, Board.single().getSpawnTile()), new AdvanceTowardsTargetA(v)});
    ATask fullInventoryAtSpawnSeqDone = new Sequence(v, new ATask[]{new InSpawnC(v), new DepositResourcesA(v)});

    ATask notFullInventorySeq = new Sequence(v, new ATask[]{new NotFilledInventoryC(v), advanceOrCollectSel});
    ATask fullInventorySeq = new Selector(v, new ATask[]{fullInventoryAtSpawnSeqDone, fullInventoryNotAtSpawnSeqDone});

    ATask topSelector = new Selector(v, new ATask[]{notFullInventorySeq, fullInventorySeq});

    return topSelector.execute();
  }
}
