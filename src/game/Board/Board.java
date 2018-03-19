package game.Board;

import game.GodSim;
import game.Town.RESOURCES;
import processing.core.PVector;

public class Board {
    private GodSim g;
    private ATile[][] board;
    private ATile spawn;

    public Board(GodSim g) {
        this.g = g;
        initialize();
    }

    /**
     * Get the spawn tile.
     *
     * @return The spawn tile.
     */
    public ATile getSpawnTile() {
        return spawn;
    }

    /**
     * Retrieve the tile at the given pixel location
     *
     * @param vector the PVector describing the px location of the tile
     * @return the Tile at the location
     */
    public ATile getTile(PVector vector) {
        return getTile(vector.x, vector.y);
    }

    /**
     * Retrieve the tile at the given pixel location
     *
     * @param x the x value of the px location of the tile
     * @param y the y value of the px location of the tile
     * @return the Tile at the location
     */
    public ATile getTile(float x, float y) {
        if (x > g.MAP_PX_WIDTH || x < 0 || y > g.MAP_PX_HEIGHT || y < 0) {
            throw new IndexOutOfBoundsException("Outside of board bounds.");
        }
        int tileIndexX = (int) (x / g.CELL_W);
        int tileIndexY = (int) (y / g.CELL_H);

        return board[tileIndexX][tileIndexY];
    }

    public void draw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ATile tile = board[i][j];
                tile.draw();
            }
        }
    }

    private void initialize() {
        board = new ATile[(int) (g.CELLS_WIDE)][(int) (g.CELLS_TALL)];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = initializeTile(i, j);
            }
        }
    }

    /**
     * Initialize a specific tile at the given board slot
     *
     * @param x the horizontal index of the tile in the board
     * @param y the vertical index of the tile in the board
     * @return the created tile
     */
    private ATile initializeTile(int x, int y) {
        if (x == board.length / 2 && y == board[x].length / 2) {
            spawn = new SpawnTile(x, y, g.CELL_W, g.CELL_H, g);
            return spawn;
        } else {
            float waterNoise = (float) g.noise(x, y);
            if (waterNoise > 0.66) {
                return new WaterTile(x, y, g.CELL_W, g.CELL_H, g);
            }
            return new ReachableTile(x, y, g.CELL_W, g.CELL_H, g);
        }
    }

    /**
     * Returns the closest desired resource from the location of the current villager.
     *
     * @param locationOfVillager
     * @return
     */
    public ATile getClosestResourceTile(RESOURCES resource, PVector locationOfVillager) {
        // TODO: Replace with actual code/cases.
        switch (resource) {
            case WOOD:
                break;
            case WATER:
                break;
            case STONE:
                break;
            default:
                break;
        }

        ATile closestResource = spawn;
        return closestResource;
    }

    /**
     * Returns the spawn. Currently returns the actual spawn reference rather
     * than a copy.  Will consider changing in the future.
     *
     * @return the spawn.
     */
    public ATile getSpawn() {
        return spawn;
    }
}
