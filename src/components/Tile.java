package components;
import processing.core.PApplet;

public class Tile {
    float temp;
    float water;
    float cell_w;
    float cell_h;
    PApplet g;
    public Tile(float cell_w, float cell_h, PApplet game) {
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = game;
    }
    public Tile(float temp, float water, float cell_w, float cell_h, PApplet game) {
        this.temp = temp;
        this.water = water;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = game;
    }
    public void setTemp(float temp) {
        this.temp = temp;
    }
    public void setWater(float water) {
        this.water = water;
    }
    public float getWater() {
        return this.water;
    }
    public float getTemp() {
        return this.temp;
    }
    public void draw(int i, int j) {
        float x = i * cell_w;
        float y = j * cell_h;
        g.fill(0, this.getWater(), 0);
        g.rect(x, y, cell_w, cell_h);
        g.fill(0);
        g.text(y, x, y + (cell_h / 2));
    }
}
