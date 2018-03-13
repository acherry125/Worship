package components;

import processing.core.PApplet;

public class SpawnTile extends Tile {
    public SpawnTile(int x, int y, float cell_w, float cell_h, PApplet game) {
        super(x, y, cell_w, cell_h, game);
    }

    @Override
    public boolean isSpawner() {
        return true;
    }

    @Override
    public void draw() {
        int red = 255;
        int green = 140;
        int blue = 0;
        g.fill(red, green, blue);
        drawSquareBase(red, green, blue);
    }
}
