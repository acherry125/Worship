package components;

public class Tile {
    float temp;
    float water;
    float cell_w;
    float cell_h;
    public Tile(float cell_w, float cell_h) {
        this.cell_w = cell_w;
        this.cell_h = cell_h;
    }
    public Tile(float temp, float water, float cell_w, float cell_h) {
        this.temp = temp;
        this.water = water;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
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
}
