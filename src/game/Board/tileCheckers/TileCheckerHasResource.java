package game.Board.tileCheckers;

import game.Board.ATile;
import game.Board.tileCheckers.ITileChecker;
import game.Town.RESOURCES;

public class TileCheckerHasResource implements ITileChecker {
    private final RESOURCES resource;
    public TileCheckerHasResource(RESOURCES resource) {
        this.resource = resource;
    }
    @Override
    public boolean passes(ATile tile) {
        return tile.getResourceCount() > 0 && tile.peekResource() == resource;
    }
}
