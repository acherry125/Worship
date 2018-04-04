package game.Board.tileCheckers;

import game.Board.ATile;
import game.Board.tileCheckers.ITileChecker;
import game.Town.RESOURCES;

public class TileCheckerHasStructure implements ITileChecker {
    public TileCheckerHasStructure() {}

    @Override
    public boolean passes(ATile tile) {
        return tile.hasStructure();
    }
}
