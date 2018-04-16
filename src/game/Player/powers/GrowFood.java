package game.Player.powers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.RESOURCES;

public class GrowFood implements IPower {
  private static GrowFood ourInstance = new GrowFood();

  public static GrowFood single() {
    return ourInstance;
  }

  private GrowFood() {
  }

  @Override
  public void use(ATile clickedTile, GodSim g) {
    Board.single().updateTileSlot(clickedTile, RESOURCES.FOOD);
  }
}
