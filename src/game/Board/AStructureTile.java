package game.Board;

import game.Board.structures.HutTile;
import game.GodSim;
import game.Town.RESOURCES;

public abstract class AStructureTile extends ATile {
    RESOURCES building;
    protected AStructureTile(int indX, int indY, float cell_w, float cell_h, GodSim game) {
        super(indX, indY, cell_w, cell_h, game);
    }

    @Override
    public void draw() {
        int otherColors = 80;
        g.ellipseMode(g.CORNER);
        g.rectMode(g.CORNER);
        g.noStroke();
        drawSquareBase(otherColors, (int) getTemp(), otherColors);
        g.textSize(cell_w / 5);
    }

    @Override
    public int getResourceCount() {
        return 0;
    }

    @Override
    public boolean isReachable() {
        return true;
    }

    @Override
    public boolean hasStructure() {
        return true;
    }

    @Override
    protected void calculateResource() {
        resource = RESOURCES.CRAFTED;
    }

}
