package game.Board.tileCheckers;

import game.Board.ATile;
import game.Board.tileCheckers.ITileChecker;

public class TileCheckerHasStructure implements ITileChecker {
    public TileCheckerHasStructure() {}

    @Override
    public boolean passes(ATile tile) {
        return tile.hasStructure();
    }
}
