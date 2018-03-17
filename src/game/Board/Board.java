package game.Board;

import game.GodSim;
import processing.core.PVector;

public class Board {
    private GodSim g;
    private ATile[][] board;
    private ATile spawn;

    public Board(GodSim g) {
        this.g = g;
        initialize();
    }

    private void initialize() {
        board = new ATile[(int)(g.CELLS_WIDE)][(int)(g.CELLS_TALL)];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = initializeTile(i, j);
            }
        }
    }

    /**
     * Initialize a specific tile at the given board slot
     * @param x the horizontal index of the tile in the board
     * @param y the vertical index of the tile in the board
     * @return the created tile
     */
    ATile initializeTile(int x, int y) {
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

    public ATile getSpawnTile() {
        return spawn;
    }

    public ATile getTile(PVector vector) {
        return getTile(vector.x, vector.y);
    }

    public ATile getTile(float x, float y) {
        if (x > g.MAP_PX_WIDTH || x < 0 || y > g.MAP_PX_HEIGHT || y < 0 ) {
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
}
