package game.handlers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;

import static processing.core.PApplet.println;

public class ClickHandler {
    GodSim g;
    public ClickHandler(GodSim g) {
        this.g = g;
    }
    public void handle() {
        Board board = g.getBoard();
        ATile clickedTile = board.getTile(g.getMouse());
        board.buildHutOnTile(clickedTile);
    }
}
