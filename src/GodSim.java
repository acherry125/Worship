import components.*;
import components.Tiles.ATile;
import components.Tiles.ReachableTile;
import components.Tiles.SpawnTile;
import components.Tiles.WaterTile;
import processing.core.PApplet;
import processing.event.KeyEvent;
import java.util.ArrayList;

public class GodSim extends PApplet {

    static final float SCREEN_WIDTH = 1000;
    static final float SCREEN_HEIGHT = 700;

    static final float CELLS_WIDE = 30;
    static final float CELLS_TALL = 30;

    static final float CELL_W = 100;
    static final float CELL_H = 100;

    static final float MAP_PX_WIDTH = CELL_W * CELLS_WIDE;
    static final float MAP_PX_HEIGHT = CELL_H * CELLS_TALL;

    Camera camera = new Camera(MAP_PX_WIDTH, MAP_PX_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);

    ATile[][] board;
    ArrayList<ATile> spawnPts = new ArrayList<ATile>();
    Villager villager;

    @Override
    public void setup() {
        initializeBoard();
    }

    @Override
    public void settings() {
        size((int) (SCREEN_WIDTH), (int) (SCREEN_HEIGHT));
        //fullScreen();
    }

    /**
     * Initialize a specific tile at the given board slot
     * @param x the horizontal index of the tile in the board
     * @param y the vertical index of the tile in the board
     * @return the created tile
     */
    ATile initializeTile(int x, int y) {
        if (x == board.length / 2 && y == board[x].length / 2) {
            return new SpawnTile(x, y, CELL_W, CELL_H, this);
        } else {
            float waterNoise = (float) noise(x, y);
            if (waterNoise > 0.66) {
                return new WaterTile(x, y, CELL_W, CELL_H, this);
            }
            return new ReachableTile(x, y, CELL_W, CELL_H, this);
        }
    }

    /**
     * Initialize the board
     */
    void initializeBoard() {
        board = new ATile[(int)(CELLS_WIDE)][(int)(CELLS_TALL)];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = initializeTile(i, j);
            }
        }

        villager = new Villager(this, 200, 200, VillagerRoles.EXPLORER);
        villager.setTarget(mouseX, mouseY);
        villager.setBtree(new TestTask(villager));
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int LEFT = 37;
        int LEFT_WASD = 65;
        int UP = 38;
        int UP_WASD = 87;
        int RIGHT = 39;
        int RIGHT_WASD = 68;
        int DOWN = 40;
        int DOWN_WASD = 83;
        if (keyCode == LEFT || keyCode == LEFT_WASD) {
            camera.moveLeft();
        } else if (keyCode == UP || keyCode == UP_WASD) {
            camera.moveUp();
        } else if (keyCode == DOWN || keyCode == DOWN_WASD) {
            camera.moveDown();
        } else if (keyCode == RIGHT || keyCode == RIGHT_WASD) {
            camera.moveRight();
        }
    }

    @Override
    public void draw() {
        background(255);
        translate(camera.getX(), camera.getY());
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ATile tile = board[i][j];
                tile.draw();
            }
        }
        villager.setTarget(mouseX, mouseY);
        villager.btree.execute();
        villager.draw();
    }

    /**
     * Start the game
     * @param args no additional args used
     */
    public static void main(String[] args) {
        PApplet.main("GodSim");
    }
}
