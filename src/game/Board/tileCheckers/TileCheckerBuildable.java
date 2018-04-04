package game.Board.tileCheckers;

import game.Board.ATile;
import game.Board.tileCheckers.ITileChecker;
import game.Town.RESOURCES;

public class TileCheckerBuildable implements ITileChecker {
    @Override
    public boolean passes(ATile tile) {
        if (!tile.isReachable() || tile.hasStructure() || tile.peekResource() != RESOURCES.NONE) {
            return false;
        } else {
            ATile[] adjacentTiles = tile.getBoard().getAdjacentTiles(tile);
            for (ATile t: adjacentTiles) {
                if (t.hasStructure()) {
                    return false;
                }
            }
            return true;
        }
    }
}
