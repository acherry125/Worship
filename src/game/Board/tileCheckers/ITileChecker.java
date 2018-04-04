package game.Board.tileCheckers;

import game.Board.ATile;

public interface ITileChecker {
    /**
     * @return
     */
    public boolean passes(ATile tile);
}
