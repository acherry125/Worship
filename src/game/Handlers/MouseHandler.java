package game.Handlers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;

import static processing.core.PApplet.println;

public class MouseHandler {
    GodSim g;
    public MouseHandler(GodSim g) {
        this.g = g;
    }
    public void handleLeft() {
        if (g.gameStarted) {
            g.getPlayer().actLeft(g.getMouse());
        } else {
            if (g.getUI().mouseOnButton()) {
                g.startGame();
            }
        }
    }
    public void handleRight() {
        if (g.gameStarted) {
            g.getPlayer().actRight(g.getMouse());
        }
    }
    public void handleScrollUp() {
        if (g.gameStarted) {
            g.getPlayer().rotatePower(true);
        }
    }
    public void handleScrollDown() {
        if (g.gameStarted) {
            g.getPlayer().rotatePower(false);
        }
    }
}
