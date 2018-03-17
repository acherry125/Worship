package game.Board;
import game.GodSim;

public class SpawnTile extends ReachableTile {
    public SpawnTile(int x, int y, float cell_w, float cell_h, GodSim game) {
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

    @Override
    protected void calculateResource() {
        return;
    }
}
