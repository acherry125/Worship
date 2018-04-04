package game.Handlers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;

import static processing.core.PApplet.println;

public class ClickHandler {
    GodSim g;
    public ClickHandler(GodSim g) {
        this.g = g;
    }
    public void handleLeft() {
        g.getPlayer().act(g.getMouse(), true);
    }
    public void handleRight() {
        g.getPlayer().act(g.getMouse(), false);
    }
}
