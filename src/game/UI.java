package game;

public class UI {
    GodSim g;
    public UI(GodSim g) {
        this.g = g;
    }

    public void draw() {
        g.translate(-g.getCamera().getX(), -g.getCamera().getY());
        g.rectMode(g.CORNER);
        g.noStroke();
        g.fill(230, 230, 230, 200);
        g.rect(0, 0, 500, 100);
        g.fill(30);
        g.textAlign(g.LEFT, g.TOP);
        g.text("Village", 12, 18);
        String needsString = g.getTown().getTownNeeds().toString();
        String needs[] = needsString.split("\n");
        String needsFormatted = String.join(", ", needs);
        g.textAlign(g.LEFT, g.TOP);
        g.text(needsFormatted, 12, 50);
    }
}
