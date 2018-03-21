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
        g.rect(0, 0, 500, 100);
        g.fill(30);
        //g.textSize(32);
        g.textAlign(g.LEFT, g.TOP);
        g.text("Village", 12, 18);
        String s = g.town.getVillageNeeds().toString();
        String slist[] = s.split("\n");
        String s2 = String.join(", ", slist);
        g.textAlign(g.LEFT, g.TOP);
        g.text(s2, 12, 50);
    }
}
