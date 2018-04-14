package game.Board.structures;

import game.Board.AStructureTile;
import game.Board.Board;
import game.GodSim;

public class SpawnTile extends AStructureTile {
    int resourceCount = 0;
    public SpawnTile(int indX, int indY, float cell_w, float cell_h) {
        super(indX, indY, cell_w, cell_h);
    }

    @Override
    public boolean isSpawner() {
        return true;
    }

    @Override
    public boolean isReachable() {
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
