package game;

import game.Board.Board;
import game.Handlers.KeyHandler;
import game.Player.Player;
import game.Player.powers.BuildHut;
import game.Player.powers.Flood;
import game.Player.powers.GrowTree;
import game.Player.powers.IPower;
import game.Town.Town;
import game.Handlers.ClickHandler;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.nio.file.Paths;
import java.util.HashMap;

public class GodSim extends PApplet {

    public final float SCREEN_WIDTH = 1000;
    public final float SCREEN_HEIGHT = 700;

    public final float CELLS_WIDE = 60;
    public final float CELLS_TALL = 60;

    public final float CELL_W = 100;
    public final float CELL_H = 100;

    public final float MAP_PX_WIDTH = CELL_W * CELLS_WIDE;
    public final float MAP_PX_HEIGHT = CELL_H * CELLS_TALL;

    private Camera camera;

    private Board board;
    private Town town;
    private UI ui;
    private Player player;

    private ClickHandler click;
    private KeyHandler keyboard;

    private HashMap<Integer, Boolean> keysPressed = new HashMap<Integer, Boolean>();

    private PImage cursorImg;
    private HashMap<IPower, PImage> cursorImages = new HashMap<IPower, PImage>();
    public boolean gameStarted = true;
    public PImage treeImage;


    /*** GETTERS ***/
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
    public Town getTown() {
        return town;
    }
    public Camera getCamera() {
        return camera;
    }
    public Player getPlayer() {
        return player;
    }
    public UI getUI() {
        return ui;
    }
    public boolean getKeyPressed(int keyCode) { return keysPressed.get(keyCode); }

    /*** SETTERS ***/
    /**
     * Set the cursor to be the state's current cursor image
     **/
    public void setCursor(IPower power) {
        cursorImg = cursorImages.get(power);
    }
    public void setKeyPressed(int keyCode, boolean value) {
        keysPressed.put(keyCode, value);
    }

    @Override
    public void setup() {
        click = new ClickHandler(this);
        keyboard = new KeyHandler(this);
        ui = new UI(this);
        PImage blueCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-blue.png").toString());
        PImage redCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-orange.png").toString());
        PImage greenCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-green.png").toString());
        cursorImages.put(Flood.single(), blueCursor);
        cursorImages.put(BuildHut.single(), redCursor);
        cursorImages.put(GrowTree.single(), greenCursor);
        cursorImg = blueCursor;
        treeImage = loadImage(Paths.get(System.getProperty("user.dir"), "images", "tree.png").toString());
        camera = new Camera(MAP_PX_WIDTH, MAP_PX_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT, this);
        if (gameStarted) {
            initializeAll();
        }
    }

    @Override
    public void settings() {
        size((int) (SCREEN_WIDTH), (int) (SCREEN_HEIGHT));
        //fullScreen();
    }

    private void initializeAll() {
        initializeTown();
        initializeBoard();
        board.initialize();
        town.initialize();
        initializePlayer();
    }

    public void startGame() {
        initializeAll();
        gameStarted = true;
    }

    public boolean shiftHeld() {
        return keysPressed.get(SHIFT) != null && keysPressed.get(SHIFT);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keyboard.handleRelease(event);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        keyboard.handlePress(event);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getButton() == RIGHT) {
            click.handleRight();
        }
    }

    @Override
    public void draw() {
        cursor(cursorImg, 0, 0);
        if (mousePressed && mouseButton == LEFT) {
            click.handleLeft();
        }
        background(255);
        g.noStroke();
        translate(camera.getX(), camera.getY());
        if (gameStarted) {
            board.draw();
            town.draw();
        }
        ui.draw();
    }

    /**
     * Initialize the board
     */
    private void initializeBoard() {
        board = Board.create(this);
    }

    /**
     * Initialize the town
     */
    private void initializeTown() {
        town = Town.create(this);
    }

    /**
     * Initialize the town
     */
    private void initializePlayer() {
        player = new Player();
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
