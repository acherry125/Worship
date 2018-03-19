package game.Town.behaviors;

import game.Board.ATile;
import game.Board.Board;
import game.Town.VillageNeeds;
import game.Town.Villager;
import processing.core.PVector;

/**
 * Does the behavior of making the villager given go to the tile given.
 */
public abstract class GoToTile extends Task {

    public GoToTile(Villager villager, VillageNeeds villageNeeds, Board board) {
        super(villager, villageNeeds, board);
    }

    public void goToTile(ATile tile) {
        villager.setTarget(new PVector(tile.getXPx(), tile.getXPx()));
    }
}
