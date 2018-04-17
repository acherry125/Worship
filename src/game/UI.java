package game;

import game.Town.Town;

public class UI {
    GodSim g;
    public static boolean gameOver = false;

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

        villageResourcesDraw();
        controlsDraw();
        gameOverDraw();

    }

    private void villageResourcesDraw() {
        //Village resources
        g.translate(-g.getCamera().getX(), -g.getCamera().getY());
        g.rectMode(g.CORNER);
        g.noStroke();
        g.fill(230, 230, 230, 220);
        g.rect(0, 0, 120, 170);
        g.fill(30);
        g.textAlign(g.LEFT, g.TOP);
        g.textSize(16);
        g.text("Resources:", 12, 18);
        String needsString = Town.single().getTownResources().toString();
        String needs[] = needsString.split("\n");
        String needsFormatted = String.join("\n", needs);
        g.textAlign(g.LEFT, g.TOP);
        g.text(needsFormatted, 12, 50);
    }

    private void controlsDraw() {
        //Controls
        g.fill(100, 100, 100, 230);
        g.rect(0, GodSim.SCREEN_HEIGHT - 80, GodSim.SCREEN_WIDTH, GodSim.SCREEN_HEIGHT);
        g.fill(0);
        g.text("Controls", 10, GodSim.SCREEN_HEIGHT - 75);
        g.fill(255, 255, 255);
        g.textSize(14);
        g.text("Move Screen: Up/Down/Left/Right or W/S/A/D",10, GodSim.SCREEN_HEIGHT - 50);
        g.text("Change Villager Roles: Left click on hut",10, GodSim.SCREEN_HEIGHT - 30);
        g.text("Change God Power: Mouse Scroll", GodSim.SCREEN_WIDTH/2, GodSim.SCREEN_HEIGHT - 50);
        g.text("Use God Power: Right Click", GodSim.SCREEN_WIDTH/2, GodSim.SCREEN_HEIGHT - 30);
    }

    private void gameOverDraw() {
        if (Town.single().getVillagers().size() == 0) {
            UI.gameOver = true;
            g.fill(175, 0, 0);
            g.stroke(0);
            g.textSize(40);
            g.rect(GodSim.SCREEN_WIDTH / 2 - 250, GodSim.SCREEN_HEIGHT / 2 - 150,
                    GodSim.SCREEN_WIDTH / 2, GodSim.SCREEN_HEIGHT / 2 - 125);
            g.fill(255, 255, 255);
            g.textSize(64);
            g.text("Game Over.", GodSim.SCREEN_WIDTH / 2 - 180, GodSim.SCREEN_HEIGHT / 2 - 125);
            g.textSize(14);
            g.textAlign(2, 2);
            g.text("All your villagers have died.",
                    GodSim.SCREEN_WIDTH / 2 - 150, GodSim.SCREEN_HEIGHT / 2 - 25);
            g.text("Next try to keep belief above 1",
                    GodSim.SCREEN_WIDTH / 2 - 150, GodSim.SCREEN_HEIGHT / 2);
            g.text("and maintain Food & Water resources.",
                    GodSim.SCREEN_WIDTH / 2 - 150, GodSim.SCREEN_HEIGHT / 2 + 25);
            g.fill(0);
            g.text("Left click anywhere to exit", GodSim.SCREEN_WIDTH / 2 - 240, GodSim.SCREEN_HEIGHT / 2 + 60);
        }
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