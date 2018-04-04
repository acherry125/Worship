package game.Board;

import game.GodSim;
import game.Town.RESOURCES;

public class LandResourceTile extends ATile {
    int resourceCount = 20;
    public LandResourceTile(int indX, int indY, float cell_w, float cell_h, GodSim game) {
        super(indX, indY, cell_w, cell_h, game);
    }

    @Override
    public void draw() {
        int otherColors = 80;
        g.ellipseMode(g.CORNER);
        g.rectMode(g.CORNER);
        g.noStroke();
        drawSquareBase(otherColors, (int) getTemp(), otherColors);
        g.textSize(cell_w / 5);
        if (resourceCount == 0) {

        } else if (resource == RESOURCES.WOOD) {
            drawTree();
            g.fill(255);
            g.text(resourceCount, getXPx(), getYPx());
        } else if (resource == RESOURCES.STONE) {
            drawStone();
            g.fill(0);
            g.text(resourceCount, getXPx(), getYPx());
        } else if (resource == RESOURCES.FOOD) {
            drawBerryBush();
            g.fill(255);
            g.text(resourceCount, getXPx(), getYPx());
        }
    }

    @Override
    public int getResourceCount() {
        return resourceCount;
    }

    @Override
    public boolean isReachable() {
        return true;
    }

    @Override
    public RESOURCES getResource() {
        if (resourceCount > 0) {
            resourceCount--;
            return resource;
        } else {
            return null;
        }
    }

    @Override
    public boolean canBeBuiltOn() {
        return true;
    }

    @Override
    protected void calculateResource() {
        float res = (float) g.noise((float) (indX * 1.25), (float) (indY * 1.25));
        if (res < 0.3) {
            resource = RESOURCES.WOOD;
        } else if (res < 0.32) {
            resource = RESOURCES.FOOD;
        } else if (res < 0.35) {
            resource = RESOURCES.STONE;
        } else {
            resourceCount = 0;
        }
    }

    /**
     * Draw a tree on this tile
     */
    private void drawTree() {
        float padding = 10;
        float pX = (indX * cell_w) + padding;
        float pY = (indY * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        float x1 = pX + (pWidth / 2);
        float y1 = pY;
        float x2 = pX;
        float y2 = pY + pHeight;
        float x3 = pX + pWidth;
        float y3 = pY + pHeight;

        g.fill(160, 82, 45);
        g.noStroke();
        g.rect(x1 - 4, y1 + 10, 8, pHeight);
        g.fill(0, 130, 0);
        g.triangle(x1, y1, x2, y2, x3, y3);
    }

    /**
     * Draw stone on this tile
     */
    private void drawStone() {
        float padding = 10;
        float pX = (indX * cell_w) + padding;
        float pY = (indY * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        g.fill(200,200,200);
        g.noStroke();
        g.ellipseMode(g.CORNER);
        g.ellipse(pX, pY, pWidth, pHeight);
        g.stroke(150, 150, 150);
        g.ellipse(pX + 40, pY + 34, pWidth / 3, pHeight / 3);
        g.ellipse(pX + 5, pY + 5, pWidth / 4, pHeight / 3);
    }

    /**
     * Draw stone on this tile
     */
    private void drawBerryBush() {
        float padding = 10;
        float pX = (indX * cell_w) + padding;
        float pY = (indY * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        g.fill(0,210,0);
        g.noStroke();
        g.ellipse(pX, pY, pWidth, pHeight);
        g.fill(200,0,0);
        g.ellipseMode(g.CORNER);
        g.ellipse(pX + 30, pY + 24, pWidth / 5, pHeight / 5);
        g.ellipse(pX + 40, pY + 54, pWidth / 6, pHeight / 6);
        g.ellipse(pX + 20, pY + 5, pWidth / 5, pHeight / 5);
    }
}
