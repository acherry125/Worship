package game.Town.villagers.behaviors.gatherer;

import java.util.ArrayList;
import java.util.Collections;

import game.Town.RESOURCES;
import game.Town.villagers.Villager;

public class WaterGatherer extends Gatherer {

  public WaterGatherer(Villager villager) {
    super(villager);
  }

  @Override
  protected void setPossibleResources() {
    possibleResources = new ArrayList<RESOURCES>(Collections.singletonList(RESOURCES.WATER));
  }
}
