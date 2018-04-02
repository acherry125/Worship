package game.Board;

import game.GodSim;
import game.Town.RESOURCES;

public class StructureTile extends ATile {
    BUILDINGS building;
    public StructureTile(int indX, int indY, float cell_w, float cell_h, GodSim game, BUILDINGS building) {
        super(indX, indY, cell_w, cell_h, game);
        this.building = building;
    }

    @Override
    public void draw() {
        int otherColors = 80;
        g.ellipseMode(g.CORNER);
        g.rectMode(g.CORNER);
        g.noStroke();
        drawSquareBase(otherColors, (int) getTemp(), otherColors);
        g.textSize(cell_w / 5);
        drawHut();
    }

    @Override
    public int getResourceCount() {
        return 0;
    }

    @Override
    public boolean isReachable() {
        return true;
    }

    @Override
    public RESOURCES getResource() {
        return RESOURCES.CRAFTED;
    }

    /**
     * Draw a hut on this tile
     */
    private void drawHut() {
        float padding = 10;
        float pX = (indX * cell_w) + padding;
        float pY = (indY * cell_h) + 3*padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 6*padding;

        float x1 = pX + (pWidth / 2);
        float y1 = pY;
        float x2 = pX;
        float y2 = pY + pHeight;
        float x3 = pX + pWidth;
        float y3 = pY + pHeight;

        g.fill(120);
        g.stroke(0, 0, 0);
        g.triangle(x1, y1, x2, y2, x3, y3);
        g.fill(254, 176, 80);
        g.rect(x2 + 4, y2, pWidth - 8, 25);
    }
}
