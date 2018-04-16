package game.Board.structures;

import game.Board.AStructureTile;
import game.Board.Board;
import game.GodSim;

public class HutTile extends AStructureTile {
    protected HUTTYPE type;
    public HutTile(int indX, int indY, float cell_w, float cell_h) {
        super(indX, indY, cell_w, cell_h);
        type = HUTTYPE.DEFAULT;
    }

    /** GETTERS **/
    /**
     * Get the structure's type
     */
    public HUTTYPE getType() {
        return type;
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
        float pY = (indY * cell_h) + 5*padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 10*padding;

        switch(type) {
            case DEFAULT:
                g.image(g.defaultHut, pX, pY, pWidth, pHeight);
                break;
            case FOOD:
                g.image(g.berryHut, pX, pY, pWidth, pHeight);
                break;
            default: break;
        }
    }
}
