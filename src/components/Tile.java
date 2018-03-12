package components;
import processing.core.PApplet;

public class Tile {
    private int x;
    private int y;
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
        setTemp((float) g.noise((float) (x * 2.5), (float) (y * 2.5)) * 100 + 50);
        setWater((float) g.noise((float) (x * 1.5), (float) (y * 1.5)) * 100 + 50);
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
    public void draw() {
        float xPixel = x * cell_w;
        float yPixel = y * cell_h;
        g.fill(0, this.getWater(), 0);
        g.rect(xPixel, yPixel, cell_w, cell_h);
        g.fill(0);
        g.text(yPixel, xPixel, yPixel + (cell_h / 2));
    }
}
