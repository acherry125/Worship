package game.Board;

import game.GodSim;

public class SpawnTile extends ReachableTile {
    int resourceCount = 0;
    public SpawnTile(int indX, int indY, float cell_w, float cell_h, GodSim game) {
        super(indX, indY, cell_w, cell_h, game);
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

    @Override
    protected void calculateResource() {
        return;
    }
}
