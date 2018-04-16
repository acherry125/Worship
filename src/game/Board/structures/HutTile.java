package game.Board.structures;

import game.Board.AStructureTile;
import game.Board.Board;
import game.GodSim;

public class HutTile extends AStructureTile {
    public HutTile(int indX, int indY, float cell_w, float cell_h) {
        super(indX, indY, cell_w, cell_h);
    }

    @Override
    public void draw() {
        super.draw();
        drawHut();
    }

    /**
     * Draw a hut on this tile
     */
    private void drawHut() {
        float padding = 5;
        float pX = (indX * cell_w) + padding;
        float pY = (indY * cell_h) + 3*padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 6*padding;

        g.image(g.defaultHut, pX, pY, pWidth, pHeight);
    }
}
