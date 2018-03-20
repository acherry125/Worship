package game.Board;

import game.GodSim;
import game.Town.RESOURCES;

public class WaterTile extends ATile {
    protected float water;

    public WaterTile(int indX, int indY, float cell_w, float cell_h, GodSim game) {
        super(indX, indY, cell_w, cell_h, game);
        this.water = (float) g.noise((float) (indX * 1.5), (float) (indY * 1.5));
    }

    @Override
    public void draw() {
        int green = 200;
        drawSquareBase(0, green, (int) (getWater() * 100) + 155);
    }

    @Override
    public boolean isReachable() {
        return true;
    }

    @Override
    public RESOURCES getResource() {
        return resource;
    }

    /**
     * Get the water parameter
     *
     * @return the value of this tile's water ratio
     */
    protected float getWater() {
        return this.water;
    }

    @Override
    protected void calculateResource() {
        resource = RESOURCES.WATER;
    }
}
