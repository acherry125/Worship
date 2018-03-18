package game.Town.behaviors;

import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;
import processing.core.PVector;

/**
 * Behavior just for sample structure.
 */
public class FollowMouse extends Task {
  int mouseX;
  int mouseY;

  public FollowMouse(Villager villager, VillageNeeds villageNeeds, Board board,
                     int mouseX, int mouseY) {

    super(villager, villageNeeds, board);
    this.mouseX = mouseX;
    this.mouseY = mouseY;
  }

  @Override
  public int execute() {
    this.villager.setTarget(new PVector(mouseX, mouseY));
    this.villager.move();
    return 0;
  }
}
