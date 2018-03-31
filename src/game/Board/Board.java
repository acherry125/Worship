package game.Board;

import game.GodSim;
import game.Town.RESOURCES;
import processing.core.PVector;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;

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

    public ATile[] getAdjacentTiles(ATile tile) {
        ArrayList<ATile> result = new ArrayList<ATile>();
        int x = tile.getIndX();
        int y = tile.getIndY();
        if (x > 0) {
            result.add(board[x - 1][y]);
        }
        if (x < board.length - 1) {
            result.add(board[x][y]);
        }
        if (y > 0) {
            result.add(board[x][y - 1]);
        }
        if (y < board[0].length - 1) {
                result.add(board[x][y + 1]);
        }
        return result.toArray(new ATile[result.size()]);
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

    public ATile getClosestTilePasses(ITileChecker checker, PVector locationOfVillager) {
        // Setup an explored and found queue
        ArrayDeque<ATile> explored = new ArrayDeque<ATile>();
        ArrayDeque<ATile> found = new ArrayDeque<ATile>();
        found.offerFirst(getTile(locationOfVillager));
        ATile curr = null;

        // Breadth first search essentially on the 2d array from the villagers position
        while (found.size() > 0) {
            curr = found.pollLast();
            if (checker.passes(curr)) {
                break;
            } else {
                explored.offerFirst(curr);
                int indX = curr.indX;
                int indY = curr.indY;
                if (indX > 0 && !explored.contains(board[indX - 1][indY])) {
                    found.offerFirst(board[indX - 1][indY]);
                }
                if (indX < g.CELLS_WIDE && !explored.contains(board[indX + 1][indY])) {
                    found.offerFirst(board[indX + 1][indY]);
                }
                if (indY > 0 && !explored.contains(board[indX][indY - 1])) {
                    found.offerFirst(board[indX][indY - 1]);
                }
                if (indY < g.CELLS_TALL && !explored.contains(board[indX][indY + 1])) {
                    found.offerFirst(board[indX][indY - 1]);
                }
            }
        }
        return curr;
    }

    /**
     * Returns the closest desired resource from the location of the current villager.
     *
     * @param locationOfVillager
     * @return
     */
    public ATile getClosestResourceTile(RESOURCES resource, PVector locationOfVillager) {
        return getClosestTilePasses(new TileCheckerHasResource(resource), locationOfVillager);
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

    /**
     * Temporary method to get constants.
     */
    public GodSim getG() {
        return g;
    }
}
