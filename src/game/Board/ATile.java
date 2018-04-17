package game.Board;

import game.GodSim;
import game.Town.RESOURCES;
import game.Town.Town;
import processing.core.PVector;

import java.awt.*;

public abstract class ATile {
    /**
     * tile index horizontally
     */
    protected final int indX;

    /**
     * tile index vertically
     */
    protected final int indY;

    /**
     * the width of this tile in px
     */
    protected final float cell_w;

    /**
     * the height of this tile in px
     */
    protected final float cell_h;

    /**
     * the game.GodSim instance that instantiates this tile
     */
    protected final GodSim g;
    protected RESOURCES resource = RESOURCES.NONE;

    private float temp;

    private boolean highlight = false;
    private Color highlightColor = new Color(255, 0, 0);

    /* Constructor */
    ATile(int indX, int indY, float cell_w, float cell_h) {
        this.indX = indX;
        this.indY = indY;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = Town.single().getGodSim();
        initialize();
    }

    /* API */

    /*** GETTERS ***/
    abstract public int getResourceCount();
    public int getIndX() {
        return indX;
    }
    public int getIndY() {
        return indY;
    }
    /**
     * Get the x position of the tile
     *
     * @return the x position of the tile's center
     */
    public float getXPx() {
        return indX * g.CELL_W + (g.CELL_W / 2);
    }
    /**
     * Get the y position of the tile
     *
     * @return the y position of the tile's center
     */
    public float getYPx() {
        return indY * g.CELL_H + (g.CELL_H / 2);
    }
    /**
     * Get the temperature of the tile
     *
     * @return the temperature of this tile
     */
    float getTemp() {
        return this.temp;
    }
    /**
     * Get the tile's resource, and deplete if finite from the tile
     *
     * @return the tile's RESOURCE
     */
    public RESOURCES getResource() {
        return resource;
    }
    public PVector getPosition() {
        return new PVector(getXPx(), getYPx());
    }
    public boolean isHighlighted() { return highlight; }
    /**
     * View the tile's resource
     * @return the tile's RESOURCE
     */
    public RESOURCES peekResource() {
        return resource;
    }

    /*** SETTERS ***/
    public void setResource(RESOURCES res) {}


    /**
     * Draw this tile to the screen
     */
    abstract public void draw();

    /**
     * Determine if this is a spawning tile
     *
     * @return whether this tile spawns units
     */
    public boolean isSpawner() {
        return false;
    }
    /**
     * Determine if this is a reachable tile.
     *
     * @return whether this tile be entered by a villager
     */
    abstract public boolean isReachable();

    public boolean hasStructure() {
        return false;
    }
    /**
     * Checks if the location of the villager given is at this tile (near it, determined by
     * the cell width/height.
     *
     * @param locationOfVillager the location of the villager.
     * @return whether or not the villager is at (near) this tile.
     */
    public boolean isAtTile(PVector locationOfVillager) {
        return distanceFrom(locationOfVillager) == 0;
    }

    /**
     *
     * @return whether this tile can be built on
     */
    public boolean canBeBuiltOn() {
        return false;
    }

    public void highlightTile(int r, int g, int b) {
        this.highlight = true;
        this.highlightColor = new Color(r,g,b);
    }

    public void stopHighlight() {
        this.highlight = false;
    }

    /**
     * Calculates the distance from the vector to the boundary of this tile.
     *
     * @param loc the location to calculate the distance from.
     * @return the distance between the villager and the tile
     */
    private float distanceFrom(PVector loc) {
        float posX = loc.x;
        float posY = loc.y;

        return distanceFrom(posX, posY);
    }

    /**
     * Calculate the distance from the point to the boundary of this tile.
     *
     * @param locX
     * @param locY
     * @return the distance between the location and the tile
     */
    private float distanceFrom(float locX, float locY) {
        // if the location is within the tile
        if (locX >= getXPx() - (g.CELL_W / 2)
                && locX <= getXPx() + (g.CELL_W / 2)
                && locY >= getYPx() - (g.CELL_H / 2)
                && locY <= getYPx() + (g.CELL_H / 2)) {
            return 0;
        }
        return distrect(locX, locY, getXPx() - (g.CELL_W / 2), getYPx() - (g.CELL_H / 2),
                getXPx() + (g.CELL_W / 2), getYPx() + (g.CELL_H / 2));
    }

    @Override
    public String toString() {
        return String.format("x: %d, y: %d, res: %s", getIndX(), getIndY(), peekResource().toString());
    }

    /* Protected Methods */

    /**
     * Initialize this tile based on its location
     */
    private void initialize() {
        calculateTemp();
        calculateResource();
        distanceFrom(500, 500);
    }

    /**
     * Draw the tile's square with the given color
     *
     * @param red   the color value {@code int} for red from 0 to 255
     * @param green the color value {@code int} for green from 0 to 255
     * @param blue  the color value {@code int} for blue from 0 to 255
     */
    protected void drawSquareBase(int red, int green, int blue) {
        g.rectMode(g.CORNER);
        g.fill(red, green, blue);
        float xPixel = indX * cell_w;
        float yPixel = indY * cell_h;
        g.rect(xPixel, yPixel, cell_w, cell_h);
        if (highlight) {
            g.stroke(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getBlue());
            g.strokeWeight(2);
            g.rect(xPixel + 2, yPixel + 2, cell_w - 4, cell_h - 4);
            g.noStroke();
        }
    }

    /**
     * Determine if this tile has a resource based on its position, and set it
     */
    void calculateResource() {
        resource = RESOURCES.NONE;
    }

    /* Private Methods */

    /**
     * Set the temperature of the tile
     */
    private void calculateTemp() {
        float noise1 = g.noise((float) (indX * 2.5), (float) (indY * 2.5));
        float minTemp = 120;
        float offset = 230 - minTemp;
        this.temp = (noise1 * offset) + minTemp;
    }

    /**
     * Get the distance to the given rectangle
     *
     * @param x  x coordinate of the point
     * @param y  y coordinate of the point
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    // disclaimer, from:
    // https://stackoverflow.com/questions/5112584/how-to-calculate-the-dist-from-mousex-mousey-to-a-rectangle-in-processing
    private float distrect(float x, float y, float x1, float y1, float x2, float y2) {
        float dx1 = x - x1;
        float dx2 = x - x2;
        float dy1 = y - y1;
        float dy2 = y - y2;
        if (dx1 * dx2 < 0) { // x is between x1 and x2
            if (dy1 * dy2 < 0) { // (x,y) is inside the rectangle
                return g.min(g.min(g.abs(dx1), g.abs(dx2)), g.min(g.abs(dy1), g.abs(dy2)));
            }
            return g.min(g.abs(dy1), g.abs(dy2));
        }
        if (dy1 * dy2 < 0) { // y is between y1 and y2
            // we don't have to test for being inside the rectangle, it's already tested.
            return g.min(g.abs(dx1), g.abs(dx2));
        }
        return g.min(g.min(g.dist(x, y, x1, y1), g.dist(x, y, x2, y2)), g.min(g.dist(x, y, x1, y2), g.dist(x, y, x2, y1)));
    }
}
