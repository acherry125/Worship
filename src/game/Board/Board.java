package game.Board;

import game.Board.structures.HUTTYPE;
import game.Board.structures.HutTile;
import game.Board.structures.SpawnTile;
import game.Board.tileCheckers.ITileChecker;
import game.Board.tileCheckers.TileCheckerBuildable;
import game.Board.tileCheckers.TileCheckerHasStructure;
import game.GodSim;
import game.Town.RESOURCES;
import game.Town.villagers.behaviors.Blackboard;
import processing.core.PVector;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static processing.core.PApplet.println;

public class Board {
    public GodSim g;
    private ATile[][] board;
    private ATile spawn;
    private HashMap<RESOURCES, ArrayList<ATile>> resourceLists = new HashMap<RESOURCES, ArrayList<ATile>>();
    private ATile nextBuildableTile;

    private static Board ourInstance;

    public static Board create(GodSim g){
        ourInstance = new Board(g);
        return ourInstance;
    }

    public static Board single() {
        return ourInstance;
    }

    private Board(GodSim g) {
        this.g = g;
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
            println("ERROR: Outside of board bounds.");
            return spawn;
        }
        int tileIndexX = (int) (x / g.CELL_W);
        int tileIndexY = (int) (y / g.CELL_H);

        return board[tileIndexX][tileIndexY];
    }

    /**
     * Retrieve the tile at the given pixel location
     *
     * @param x the x value of the px location of the tile
     * @param y the y value of the px location of the tile
     * @return the Tile at the location
     */
    public ATile getTileAtIndex(int x, int y) {
        return board[x][y];
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

    public int getNumHuts() {
        ArrayList<ATile> resources = resourceLists.get(RESOURCES.CRAFTED);
        if (resources != null) {
            return resourceLists.get(RESOURCES.CRAFTED).size();
        } else {
            return 0;
        }
    }

    public ArrayList<HutTile> getHuts() {
        ArrayList<ATile> huts = resourceLists.get(RESOURCES.CRAFTED);
        if (huts != null) {
            ArrayList<HutTile> hutList = new ArrayList<HutTile>();
            for (ATile a: huts) {
                hutList.add((HutTile) a);
            }
            return hutList;
        } else {
            return new ArrayList<HutTile>();
        }
    }

    /**
     * Replaces the given tile with a tile that contains a structure
     * @param tile
     * @return the new tile
     */
    public AStructureTile buildHutOnTile(ATile tile) {
        ArrayList<RESOURCES> depletable = new ArrayList<>(Arrays.asList(new RESOURCES[]{RESOURCES.WOOD, RESOURCES.FOOD, RESOURCES.STONE}));
        // only on empty tiles (including tiles with empty resources)
        if (tile.peekResource() != RESOURCES.NONE && !depletable.contains(tile.peekResource()) || (tile.getResourceCount() != 0 && depletable.contains(tile.peekResource()))) {
            return null;
        }
        int indX = tile.getIndX();
        int indY = tile.getIndY();
        HutTile structure = new HutTile(indX, indY, g.CELL_W, g.CELL_H);
        updateTileSlot(structure, RESOURCES.CRAFTED);
        nextBuildableTile = getClosestBuildableTile(spawn.getPosition());
        return structure;
    }

    /**
     * Replaces the given tile with a tile that contains a structure
     * @param tile
     * @return the new tile
     */
    public WaterTile floodTile(ATile tile) {
        if (tile.isSpawner() || tile.peekResource() == RESOURCES.WATER) {
            // we don't want to overwrite water tiles or the spawn
            return null;
        }
        int indX = tile.getIndX();
        int indY = tile.getIndY();

        WaterTile water = new WaterTile(indX, indY, g.CELL_W, g.CELL_H);
        updateTileSlot(water, RESOURCES.WATER);
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
            result.add(getTileAtIndex(x-1, y));
        }
        if (x < board.length - 1) {
            result.add(getTileAtIndex(x+1, y));
        }
        if (y > 0) {
            result.add(getTileAtIndex(x, y-1));
        }
        if (y < board[0].length - 1) {
                result.add(getTileAtIndex(x, y + 1));
        }
        return result.toArray(new ATile[result.size()]);
    }

    public void draw() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ATile tile = getTileAtIndex(i, j);
                tile.draw();
            }
        }
    }

    public void initialize() {
        board = new ATile[(int) (g.CELLS_WIDE)][(int) (g.CELLS_TALL)];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ATile newTile = initializeTile(i,j);
                updateTileSlot(newTile, newTile.peekResource());
            }
        }
        nextBuildableTile = getClosestBuildableTile(spawn.getPosition());
    }

    public void updateTileSlot(ATile tile, RESOURCES res) {
        int indX = tile.getIndX();
        int indY = tile.getIndY();
        if (getTileAtIndex(indX, indY) != null) {
            removeTileMeta(getTileAtIndex(indX, indY));
        }
        tile.setResource(res);
        if (res != RESOURCES.NONE) {
            ArrayList<ATile> listToPut;
            if (resourceLists.containsKey(res)) {
                listToPut = resourceLists.get(res);
            } else {
                listToPut = new ArrayList<ATile>();
            }
            listToPut.add(tile);
            resourceLists.put(res, listToPut);
        }
        board[indX][indY] = tile;
    }

    /**
     * Initialize a specific tile at the given board slot
     *
     * @param x the horizontal index of the tile in the board
     * @param y the vertical index of the tile in the board
     * @return the created tile
     */
    private ATile initializeTile(int x, int y) {
        int middleX = board.length / 2;
        int middleY = board[x].length / 2;
        if (x == middleX && y == middleY) {
            spawn = new SpawnTile(x, y, g.CELL_W, g.CELL_H);
            return spawn;
        } else if (x == middleX + 2 && y == middleY) {
            HutTile hut = new HutTile(x, y, g.CELL_W, g.CELL_H);
            hut.setType(HUTTYPE.DEFAULT);
            return hut;
        } else if (x == middleX - 2 && y == middleY) {
            HutTile hut = new HutTile(x, y, g.CELL_W, g.CELL_H);
            hut.setType(HUTTYPE.BUILD);
            return hut;
        } else if (x == middleX && y == middleY - 2) {
            HutTile hut = new HutTile(x, y, g.CELL_W, g.CELL_H);
            hut.setType(HUTTYPE.DEFAULT);
            return hut;
        }
        else {
            float waterNoise = g.noise(x, y);
            if (waterNoise > 0.66) {
                return new WaterTile(x, y, g.CELL_W, g.CELL_H);
            }
            return new LandResourceTile(x, y, g.CELL_W, g.CELL_H);
        }
    }

    private void removeTileMeta(ATile tile) {
        RESOURCES res = tile.peekResource();
        if (resourceLists.containsKey(res)) {
            ArrayList<ATile> existingList = resourceLists.get(res);
            tile.stopHighlight();
            existingList.remove(tile);
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
            if (checker.passes(curr) && !curr.isHighlighted()) {
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
        float closestDist = Float.MAX_VALUE;
        if (resourceLists.get(resource) == null || resourceLists.get(resource).size() == 0) {
            return null;
        }
        ATile closest = resourceLists.get(resource).get(0);
        Blackboard bb = Blackboard.single();
        for (ATile tile: resourceLists.get(resource)) {
            float dist = PVector.sub(locationOfVillager, tile.getPosition()).mag();
            if (dist < closestDist && !tile.isHighlighted()) {
                closest = tile;
                closestDist = dist;
            }
        }
        return closest;
    }

    /**
     * Returns the closest available plot of land
     *
     * @param loc
     * @return
     */
    private ATile getClosestBuildableTile(PVector loc) {
        return getClosestTileThatPasses(new TileCheckerBuildable(), loc);
    }

    public ATile getNextBuildableTile() {
        ATile buildable = nextBuildableTile;
        nextBuildableTile = getClosestBuildableTile(spawn.getPosition());
        return buildable;
    }

    public int countTilesThatPass(ITileChecker checker) {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if(checker.passes(getTileAtIndex(x, y))) {
                    count++;
                }
            }
        }
        return count;
    }
}
