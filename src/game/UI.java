package game;

public class UI {
    GodSim g;
    public UI(GodSim g) {
        this.g = g;
    }

    public void draw() {
        g.println(g.camera.getX(), g.camera.getY());
        g.translate(-g.camera.getX(), -g.camera.getY());
        g.rectMode(g.CORNER);
        g.noStroke();
        g.fill(230, 230, 230, 200);
        g.rect(0, 0, 500, 150);
        g.fill(30);
        //g.textSize(32);
        g.textAlign(g.LEFT, g.TOP);
        g.text("Village", 12, 18);
        g.textAlign(g.RIGHT, g.TOP);
        g.text(g.town.getVillageNeeds().toString(), 500 - 12, 18);
    }
}
