package game.Handlers;

import game.GodSim;
import game.UI;

public class MouseHandler {
    private final GodSim g;
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

        if (UI.gameOver) {
            System.exit(0);
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
