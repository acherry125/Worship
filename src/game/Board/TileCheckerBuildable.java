package game.Board;

public class TileCheckerBuildable implements ITileChecker {
    @Override
    public boolean passes(ATile tile) {
        if (!tile.isReachable()) {
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
