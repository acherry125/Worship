package game.Board;

import game.Town.RESOURCES;

public class LandResourceTile extends ATile {
    private int resourceCount ;

    public LandResourceTile(int indX, int indY, float cell_w, float cell_h) {
        super(indX, indY, cell_w, cell_h);
        resetResourceCount();
    }

    /*** GETTERS ***/
    @Override
    public int getResourceCount() {
        return resourceCount;
    }

    @Override
    public RESOURCES getResource() {
        if (resourceCount > 0) {
            if (--resourceCount <= 0) {
                Board.single().updateTileSlot(this, RESOURCES.NONE);
            }
            return resource;
        } else {
            return null;
        }
    }

    @Override
    public boolean isReachable() {
        return true;
    }

    @Override
    public boolean canBeBuiltOn() {
        return true;
    }

    /*** SETTERS ***/

    @Override
    public void setResource(RESOURCES res) {
        resource = res;
        resetResourceCount();
    }

    @Override
    public void draw() {
        int otherColors = 80;
        g.ellipseMode(g.CORNER);
        g.rectMode(g.CORNER);
        g.noStroke();
        drawSquareBase(otherColors, (int) getTemp(), otherColors);
        g.textSize(cell_w / 5);
        g.textAlign(g.RIGHT, g.BOTTOM);
        g.imageMode(g.CORNER);
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
    protected void calculateResource() {
        float res = g.noise((float) (indX * 1.25), (float) (indY * 1.25));
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

    private void resetResourceCount() {
        resourceCount = 50;
    }
    /**
     * Draw a tree on this tile
     */
    private void drawTree() {
        float padding = 0;
        float pX = (indX * cell_w) + padding;
        float pY = (indY * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;
        g.image(g.treeImg, pX, pY, pWidth, pHeight);
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
        g.image(g.rocksImg, pX, pY, pWidth, pHeight);
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

        g.image(g.bushImg, pX, pY, pWidth, pHeight);
    }
}
