package game.Board.tileCheckers;

import game.Board.ATile;
import game.Board.Board;
import game.Town.RESOURCES;

public class TileCheckerBuildable implements ITileChecker {
    @Override
    public boolean passes(ATile tile) {
        if (!tile.isReachable() || tile.hasStructure() || tile.peekResource() != RESOURCES.NONE) {
            return false;
        } else {
            ATile[] adjacentTiles = Board.single().getAdjacentTiles(tile);
            for (ATile t: adjacentTiles) {
                if (t.hasStructure()) {
                    return false;
                }
            }
            return true;
        }
    }
}
