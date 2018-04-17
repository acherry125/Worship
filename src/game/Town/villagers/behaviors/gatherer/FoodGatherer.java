package game.Town.villagers.behaviors.gatherer;

import java.util.ArrayList;
import java.util.Collections;

import game.Town.RESOURCES;
import game.Town.villagers.Villager;

public class FoodGatherer extends Gatherer {
  public FoodGatherer(Villager villager) {
    super(villager);
  }

  @Override
  protected void setPossibleResources() {
    possibleResources = new ArrayList<RESOURCES>(Collections.singletonList(RESOURCES.FOOD));
  }
}
