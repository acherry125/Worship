import components.SpawnTile;
import processing.core.PApplet;
import processing.event.KeyEvent;
import java.util.ArrayList;

import components.Camera;
import components.Tile;
import components.Town;

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

    Tile[][] board;
    ArrayList<Tile> spawnPts = new ArrayList<Tile>();

    public void setup() {
        initializeBoard();
    }

    public void settings() {
        size((int) (SCREEN_WIDTH), (int) (SCREEN_HEIGHT));
        //fullScreen();
    }

    void initializeBoard() {
        // initialize the board
        board = new Tile[(int)(CELLS_WIDE)][(int)(CELLS_TALL)];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Tile currTile;
                if (i == board.length / 2 && j == board[i].length / 2) {
                    currTile = new SpawnTile(i, j, CELL_W, CELL_H, this);
                } else {
                    currTile = new Tile(i, j, CELL_W, CELL_H, this);
                }
                board[i][j] = currTile;
            }
        }
    }

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
    public void draw() {
        background(255);
        translate(camera.getX(), camera.getY());
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Tile tile = board[i][j];
                tile.draw();
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("GodSim");
    }
}
