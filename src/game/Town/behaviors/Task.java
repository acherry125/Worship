package game.Town.behaviors;

import game.Town.VillageNeeds;

public abstract class Task {
  VillageNeeds villageNeeds;
  public abstract int execute();
}
