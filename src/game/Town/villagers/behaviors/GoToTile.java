package game.Town.villagers.behaviors;

import game.Board.ATile;
import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;
import processing.core.PVector;

/**
 * Does the behavior of making the villager given go to the tile given.
 */
public abstract class GoToTile extends ATask {
    PVector target;
    public GoToTile(Villager villager, TownNeeds townNeeds, Board board) {
        super(villager, townNeeds, board);
    }

    public void goToTile(ATile tile) {
        villager.setTarget(new PVector(tile.getXPx(), tile.getXPx()));
    }
}
