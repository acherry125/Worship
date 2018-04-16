package game.Board;

import game.Board.structures.HUTTYPE;
import game.Board.structures.HutTile;
import game.GodSim;
import game.Town.RESOURCES;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AStructureTile extends ATile {
    RESOURCES building;
    protected ArrayList<HUTTYPE> huttypes;
    protected int typeIndex;
    protected AStructureTile(int indX, int indY, float cell_w, float cell_h) {
        super(indX, indY, cell_w, cell_h);
        huttypes = new ArrayList<>(Arrays.asList(HUTTYPE.DEFAULT, HUTTYPE.WOOD, HUTTYPE.FOOD, HUTTYPE.WATER, HUTTYPE.STONE, HUTTYPE.BUILD));
        typeIndex = 0;
    }

    /** GETTERS **/
    /**
     * Get the structure's type
     */
    public HUTTYPE getType() {
        return huttypes.get(typeIndex);
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

    /** SETTERS **/
    public void toggleType() {
        typeIndex = (typeIndex + 1) % huttypes.size();
    }
    public void setType(HUTTYPE type) {
        typeIndex = huttypes.indexOf(type);
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
    protected void calculateResource() {
        resource = RESOURCES.CRAFTED;
    }

}
