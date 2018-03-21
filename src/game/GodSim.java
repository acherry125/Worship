package game;

import game.Board.Board;
import game.Town.Town;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.event.KeyEvent;

public class GodSim extends PApplet {

    public final float SCREEN_WIDTH = 1000;
    public final float SCREEN_HEIGHT = 700;

    public final float CELLS_WIDE = 30;
    public final float CELLS_TALL = 30;

    public final float CELL_W = 100;
    public final float CELL_H = 100;

    public final float MAP_PX_WIDTH = CELL_W * CELLS_WIDE;
    public final float MAP_PX_HEIGHT = CELL_H * CELLS_TALL;

    public Camera camera = new Camera(MAP_PX_WIDTH, MAP_PX_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);

    public Board board;
    public Town town;
    public UI ui;

    @Override
    public void setup() {
        initializeBoard();
        initializeTown();
        ui = new UI(this);
    }

    @Override
    public void settings() {
        size((int) (SCREEN_WIDTH), (int) (SCREEN_HEIGHT));
        //fullScreen();
    }

    /**
     * Initialize the board
     */
    private void initializeBoard() {
        board = new Board(this);
    }

    /**
     * Initialize the town
     */
    private void initializeTown() {
        town = new Town(this);
    }

    /**
     * Get the mouse's position on the board
     *
     * @return PVector with the mouse's position on the board
     */
    public PVector getMouse() {
        return new PVector(mouseX - camera.getX(), mouseY - camera.getY());
    }

    public Board getBoard() {
        return board;
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
        g.noStroke();
        translate(camera.getX(), camera.getY());
        board.draw();
        town.draw();
        ui.draw();
    }

    /**
     * Start the game
     *
     * @param args no additional args used
     */
    public static void main(String[] args) {
        PApplet.main("game.GodSim");
    }
}
