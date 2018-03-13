package components;
import processing.core.PApplet;
import static processing.core.PApplet.println;

public abstract class ATile {
    // tile index horizontally
    protected int x;
    // tile index vertically
    protected int y;
    // the width of this tile in px
    protected float cell_w;
    // the height of this tile in px
    protected float cell_h;

    // the GodSim instance that instantiates this tile
    protected PApplet g;
    private float temp;
    private float water;
    /* Constructor */
    public ATile(int x, int y, float cell_w, float cell_h, PApplet game) {
        this.x = x;
        this.y = y;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = game;
        initialize();
    }

    /** API **/
    /* Get the water of this tile */
    public float getWater() {
        return this.water;
    }
    /* Get the temperature of this tile */
    public float getTemp() {
        return this.temp;
    }
    /* Can this tile spawn units? */
    public boolean isSpawner() {
        return false;
    }
    /* Draw this tile to the screen */
    abstract public void draw();

    /** Protected Methods **/
    protected void drawSquareBase(int red, int green, int blue) {
        g.fill(red, green, blue);
        float xPixel = x * cell_w;
        float yPixel = y * cell_h;
        g.rect(xPixel, yPixel, cell_w, cell_h);
    }

    /* Determine if this tile has a resource based on its position, and set it */
    abstract protected void calculateResource();

    /** Private Methods **/
    /* Initialize this tile based on its location */
    protected void initialize() {
        calculateTemp();
        calculateWater();
        calculateResource();
    }
    /* Find the temperature of the tile */
    private void calculateTemp() {
        float noise1 = (float) g.noise((float) (x*2.5), (float) (y*2.5));
        float minTemp = 120;
        float offset = 230 - minTemp;
        float temp = (noise1 * offset) + minTemp;
        this.temp = temp;
    }
    /* Find the water content of this tile, used for deciding if this is a water tile */
    private void calculateWater() {
        float water = (float) g.noise((float) (x * 1.5), (float) (y * 1.5));
        this.water = water;
    }
}
