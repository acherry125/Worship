package game.Town.villagers.behaviors.gatherer;

import java.util.ArrayList;
import java.util.Arrays;

import game.Town.RESOURCES;
import game.Town.villagers.Villager;

public class StoneGatherer extends Gatherer {

  public StoneGatherer(Villager villager) {
    super(villager);
  }

  @Override
  protected void setPossibleResources() {
    possibleResources = new ArrayList<RESOURCES>(Arrays.asList(RESOURCES.WATER));
  }
}
