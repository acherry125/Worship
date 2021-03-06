package game.Handlers;

import game.GodSim;

import processing.event.KeyEvent;

public class KeyHandler {
    private final GodSim g;
    public KeyHandler(GodSim g) {
        this.g = g;
        int LEFT_WASD = 65;
        int UP_WASD = 87;
        int RIGHT_WASD = 68;
        int DOWN_WASD = 83;
        g.setKeyPressed(g.SHIFT, false);
        g.setKeyPressed(g.LEFT, false);
        g.setKeyPressed(g.RIGHT, false);
        g.setKeyPressed(g.UP, false);
        g.setKeyPressed(g.DOWN, false);
        g.setKeyPressed(LEFT_WASD, false);
        g.setKeyPressed(RIGHT_WASD, false);
        g.setKeyPressed(UP_WASD, false);
        g.setKeyPressed(DOWN_WASD, false);
    }

    public void handlePress(KeyEvent event) {
        int keyCode = event.getKeyCode();
        g.setKeyPressed(keyCode, true);
        if (keyCode == 69) {
            g.getPlayer().rotatePower(true);
        } else if (keyCode == 81) {
            g.getPlayer().rotatePower(false);
        }
    }

    public void handleRelease(KeyEvent event) {
        int keyCode = event.getKeyCode();
        g.setKeyPressed(keyCode, false);
    }
}
