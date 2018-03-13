package components;
import processing.core.PApplet;
import static processing.core.PApplet.println;

public class Tile {
    private int x;
    private int y;
    private boolean spawner = false;
    private float temp;
    private float water;
    private float cell_w;
    private float cell_h;
    private PApplet g;
    public Tile(int x, int y, float cell_w, float cell_h, PApplet game) {
        this.x = x;
        this.y = y;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = game;
        initialize();
    }
    private void initialize() {
        float noise1 = (float) g.noise((float) (x*2.5), (float) (y*2.5));
        float noise2 = (float) g.noise((float) (x * 1.5), (float) (y * 1.5));
        setTemp(g.max(noise1 * 255, 100));
        setWater(noise2);
    }
    private void setTemp(float temp) {
        this.temp = temp;
    }
    private void setWater(float water) {
        this.water = water;
    }
    public float getWater() {
        return this.water;
    }
    public float getTemp() {
        return this.temp;
    }
    public void setAsSpawner() {
        this.spawner = true;
    }
    public void draw() {
        float xPixel = x * cell_w;
        float yPixel = y * cell_h;

        if (getWater() > 0.6 && !spawner) {
            g.fill(0, 180, getWater() * 255);
        } else {
            g.fill(60, getTemp(), 60);
        }

        g.rect(xPixel, yPixel, cell_w, cell_h);
        g.textSize(cell_w / 5);

        if (spawner) {
            g.fill(200, 0, 0);
            int padding = 10;
            g.rect(xPixel + padding, yPixel + padding, cell_w - 2*padding, cell_h - 2*padding);
        }

        g.fill(200, 200, 210);
        //g.text(String.format("%d, %d", x, y), xPixel + 5, yPixel + (cell_h / 2));
    }
}
