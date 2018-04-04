package game.Board;

import game.Board.structures.HutTile;
import game.Board.structures.SpawnTile;
import game.Board.tileCheckers.ITileChecker;
import game.Board.tileCheckers.TileCheckerBuildable;
import game.Board.tileCheckers.TileCheckerHasResource;
import game.Board.tileCheckers.TileCheckerHasStructure;
import game.GodSim;
import game.Town.RESOURCES;
import processing.core.PVector;

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

    /**
     * Replaces the given tile with a tile that contains a structure
     * @param tile
     * @return the new tile
     */
    public AStructureTile buildHutOnTile(ATile tile) {
        if (tile.peekResource() != RESOURCES.NONE) {
            // we don't want to build on a built plot of land
            return null;
        }
        int indX = tile.getIndX();
        int indY = tile.getIndY();
        HutTile structure = new HutTile(indX, indY, g.CELL_W, g.CELL_H, g);
        board[indX][indY] = structure;
        return structure;
    }

    /**
     * Replaces the given tile with a tile that contains a structure
     * @param tile
     * @return the new tile
     */
    public WaterTile floodTile(ATile tile) {
        if (tile.isSpawner() || tile.peekResource() == RESOURCES.WATER) {
            // we don't want to build on a built plot of land
            return null;
        }
        int indX = tile.getIndX();
        int indY = tile.getIndY();
        WaterTile water = new WaterTile(indX, indY, g.CELL_W, g.CELL_H, g);
        board[indX][indY] = water;
        return water;
    }

    /**
     * @param tile
     * @return the tiles adjacent to the given tile
     */
    public ATile[] getAdjacentTiles(ATile tile) {
        ArrayList<ATile> result = new ArrayList<ATile>();
        int x = tile.getIndX();
        int y = tile.getIndY();
        if (x > 0) {
            result.add(board[x - 1][y]);
        }
        if (x < board.length - 1) {
            result.add(board[x + 1][y]);
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
            float waterNoise = g.noise(x, y);
            if (waterNoise > 0.66) {
                return new WaterTile(x, y, g.CELL_W, g.CELL_H, g);
            }
            return new LandResourceTile(x, y, g.CELL_W, g.CELL_H, g);
        }
    }


    /**
     * @param checker
     * @param locationOfVillager
     * @return The closest tile on the board that satisfies the given tile checker
     */
    public ATile getClosestTileThatPasses(ITileChecker checker, PVector locationOfVillager) {
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
                ATile[] adjacent = getAdjacentTiles(curr);
                for (ATile tile: adjacent) {
                    if (!explored.contains(tile)) {
                        found.offerFirst(tile);
                    }
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
        return getClosestTileThatPasses(new TileCheckerHasResource(resource), locationOfVillager);
    }

    /**
     * Returns the closest available plot of land
     *
     * @param locationOfVillager
     * @return
     */
    public ATile getClosestBuildableTile(PVector locationOfVillager) {
        return getClosestTileThatPasses(new TileCheckerBuildable(), locationOfVillager);
    }

    public int numStructuresOnMap() {
        return countTilesThatPass(new TileCheckerHasStructure());
    }

    public ATile[] getTilesThatPass(ITileChecker checker) {
        ArrayList<ATile> passing = new ArrayList<ATile>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if(checker.passes(board[x][y])) {
                    passing.add(board[x][y]);
                }
            }
        }
        return passing.toArray(new ATile[passing.size()]);
    }


    public int countTilesThatPass(ITileChecker checker) {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if(checker.passes(board[x][y])) {
                    count++;
                }
            }
        }
        return count;
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
