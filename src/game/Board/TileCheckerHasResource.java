package game.Board;

import game.Town.RESOURCES;

public class TileCheckerHasResource implements ITileChecker {
    RESOURCES resource;
    public TileCheckerHasResource(RESOURCES resource) {
        this.resource = resource;
    }
    @Override
    public boolean passes(ATile tile) {
        return tile.peekResource() == resource;
    }
}
