package game.Board;
import game.GodSim;
import game.Town.RESOURCES;
import jdk.management.resource.ResourceRequest;
import processing.core.PVector;

public abstract class ATile {
    /**
     * tile index horizontally
     */
    protected int x;

    /**
     * tile index vertically
     */
    protected int y;

    /**
     * the width of this tile in px
     */
    protected float cell_w;

    /**
     * the height of this tile in px
     */
    protected float cell_h;

    /**
     * the game.GodSim instance that instantiates this tile
      */
    protected GodSim g;
    protected RESOURCES resource;

    private float temp;

    /* Constructor */
    public ATile(int x, int y, float cell_w, float cell_h, GodSim game) {
        this.x = x;
        this.y = y;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = game;
        initialize();
    }

    /* API */

    /**
     * Get the x position of the tile
     * @return the x position of the tile
     */
    public float getXPx() { return this.x * g.CELL_W; }

    /**
     * Get the y position of the tile
     * @return the y position of the tile
     */
    public float getYPx() {
        return this.y * g.CELL_H;
    }

    /**
     * Get the temperature of the tile
     * @return the temperature of this tile
     */
    public float getTemp() {
        return this.temp;
    }

    /**
     * Determine if this is a spawning tile
     * @return whether this tile spawns units
     */
    public boolean isSpawner() {
        return false;
    }

    /**
     * Draw this tile to the screen
     */
    abstract public void draw();

    /**
     * Determine if this is a reachable tile.
     * @return whether this tile be entered by a villager
     */
    abstract public boolean isReachable();

    /**
     * Get the tile's resource.
     * @return the tile's RESOURCE
     */
    public RESOURCES getResource() {
        return resource;
    }

    /* Protected Methods */

    /**
     *  Draw the tile's square with the given color
     *  @param red the color value {@code int} for red from 0 to 255
     *  @param green the color value {@code int} for green from 0 to 255
     *  @param blue the color value {@code int} for blue from 0 to 255
     */
    protected void drawSquareBase(int red, int green, int blue) {
        g.fill(red, green, blue);
        float xPixel = x * cell_w;
        float yPixel = y * cell_h;
        g.rect(xPixel, yPixel, cell_w, cell_h);
    }

    /**
     * Determine if this tile has a resource based on its position, and set it
     */
    protected void calculateResource() {
        resource = RESOURCES.NONE;
    };

    /* Private Methods */

    /**
     * Initialize this tile based on its location
     */
    protected void initialize() {
        calculateTemp();
        calculateResource();
    }

    /**
     * Set the temperature of the tile
     */
    private void calculateTemp() {
        float noise1 = (float) g.noise((float) (x*2.5), (float) (y*2.5));
        float minTemp = 120;
        float offset = 230 - minTemp;
        float temp = (noise1 * offset) + minTemp;
        this.temp = temp;
    }

    /**
     * Checks if the location of the villager given is at this tile (near it, determined by
     * the cell width/height.
     * @param locationOfVillager the location of the villager.
     * @return whether or not the villager is at (near) this tile.
     */
    public boolean isAtTile(PVector locationOfVillager) {
        // TODO: confirm math.
        if ((Math.abs(locationOfVillager.x - getXPx()) < cell_w)
                && (Math.abs(locationOfVillager.y - getYPx()) < cell_h)) {
            return true;
        }
        return false;
    }
}
