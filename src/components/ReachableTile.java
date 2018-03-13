package components;

import processing.core.PApplet;

public class ReachableTile extends ATile {
    protected boolean hasTree;
    protected boolean hasStone;
    public ReachableTile(int x, int y, float cell_w, float cell_h, PApplet game) {
        super(x, y, cell_w, cell_h, game);
    }

    public void draw() {
        if (getWater() > 0.66) {
            int green = 200;
            drawSquareBase(0, green, (int)(getWater() * 255));
        } else {
            int otherColors = 80;
            drawSquareBase(otherColors, (int) getTemp(), otherColors);
            if (hasTree) {
                drawTree();
            } else if (hasStone) {
                drawStone();
            }
        }
        g.textSize(cell_w / 5);
        g.fill(200, 200, 210);
        //g.text(String.format("%d, %d", x, y), xPixel + 5, yPixel + (cell_h / 2));
    }

    /* Draw a hut on this tile */
    private void drawHut() {
        g.fill(255, 100, 0);
        float padding = 10;
        float pX = (x * cell_w) + padding;
        float pY = (y * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        float x1 = pX + (pWidth / 2);
        float y1 = pY;
        float x2 = pX;
        float y2 = pY + pHeight;
        float x3 = pX + pWidth;
        float y3 = pY + pHeight;

        g.triangle(x1, y1, x2, y2, x3, y3);
        g.triangle(x1, y1 - 3, x2, y2 - 20, x3, y3 - 20);
    }

    /* Draw a tree on this tile */
    private void drawTree() {
        float padding = 10;
        float pX = (x * cell_w) + padding;
        float pY = (y * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        float x1 = pX + (pWidth / 2);
        float y1 = pY;
        float x2 = pX;
        float y2 = pY + pHeight;
        float x3 = pX + pWidth;
        float y3 = pY + pHeight;

        g.fill(160, 82, 45);
        g.rect(x1 - 4, y1 + 10, 8, pHeight);
        g.fill(0, 130, 0);
        g.triangle(x1, y1, x2, y2, x3, y3);
    }

    /* Draw stone on this tile */
    private void drawStone() {
        float padding = 10;
        float pX = (x * cell_w) + padding;
        float pY = (y * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        g.fill(200,200,200);
        g.ellipseMode(g.CORNER);
        g.ellipse(pX, pY, pWidth, pHeight);
        g.ellipse(pX + 40, pY + 34, pWidth / 3, pHeight / 3);
        g.ellipse(pX + 5, pY + 5, pWidth / 4, pHeight / 3);
    }

    /* Determine if this tile has a resource based on its position, and set it */
    protected void calculateResource() {
        float res = (float) g.noise((float) (x * 1.25), (float) (y * 1.25));
        if (res > 0.6) {
            hasTree = true;
        } else if (res < 0.25) {
            hasStone = true;
        }
    }
}
