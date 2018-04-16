package game;

import game.Town.Town;

public class UI {
    GodSim g;

    public UI(GodSim g) {
        this.g = g;
    }

    public void draw() {
        if (g.gameStarted) {
            drawGame();
        } else {
            drawMenu();
        }
    }

    private void drawGame() {
        g.translate(-g.getCamera().getX(), -g.getCamera().getY());
        g.rectMode(g.CORNER);
        g.noStroke();
        g.fill(230, 230, 230, 220);
        g.rect(0, 0, 200, 200);
        g.fill(30);
        g.textAlign(g.LEFT, g.TOP);
        g.text("Resource Counts:", 12, 18);
        String needsString = Town.single().getTownResources().toString();
        String needs[] = needsString.split("\n");
        String needsFormatted = String.join("\n", needs);
        g.textAlign(g.LEFT, g.TOP);
        g.text(needsFormatted, 12, 50);
    }

    public boolean mouseOnButton() {
        return g.mouseX > g.width / 3 && g.mouseX < 2* g.width /3 && g.mouseY > g.height/2+50 && g.mouseY < g.height/2 + 150;
    }

    private void drawMenu() {
        g.translate(-g.getCamera().getX(), -g.getCamera().getY());
        g.rectMode(g.CORNER);
        g.fill(50, 0, 0);
        g.rect(0,0, g.width, g.height);
        g.textAlign(g.CENTER, g.BOTTOM);
        g.fill(255, 255, 255);
        g.textSize(32);
        g.text("You are a god.\n Prepare to meet your chosen followers.", g.width/2, g.height/2);
        if (mouseOnButton()) {
            g.fill(0, 255, 255);
        }
        g.rectMode(g.CORNER);
        g.rect(g.width / 3, g.height / 2 + 50, g.width / 3, 100);
        g.textAlign(g.CENTER, g.TOP);
        g.fill(33, 33, 33);
        g.text("Begin.", g.width/2, g.height / 2 + 80);

    }
}