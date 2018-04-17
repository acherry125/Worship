package game;

import game.Board.Board;
import game.Handlers.KeyHandler;
import game.Player.Player;
import game.Player.powers.BuildHut;
import game.Player.powers.Flood;
import game.Player.powers.GrowTree;
import game.Player.powers.GrowFood;
import game.Player.powers.IPower;
import game.Town.Town;
import game.Handlers.MouseHandler;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.nio.file.Paths;
import java.util.HashMap;

public class GodSim extends PApplet {

    public static final float SCREEN_WIDTH = 1000;
    public static final float SCREEN_HEIGHT = 700;

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

    private MouseHandler mouse;
    private KeyHandler keyboard;

    private final HashMap<Integer, Boolean> keysPressed = new HashMap<Integer, Boolean>();

    /** Images **/
    private PImage cursorImg;
    private final HashMap<IPower, PImage> cursorImages = new HashMap<IPower, PImage>();
    public PImage treeImg;
    public PImage bushImg;
    public PImage rocksImg;
    public PImage waterImg;
    public PImage vHeadImg;
    public PImage gathererB;
    public PImage foodB;
    public PImage waterB;
    public PImage stoneB;
    public PImage woodB;
    public PImage builderB;
    public PImage defaultHut;
    public PImage berryHut;
    public PImage waterHut;
    public PImage stoneHut;
    public PImage woodHut;
    public PImage buildHut;

    // mainly for debugging
    public boolean gameStarted = true;


    /* GETTERS */
    /**
     * Get the mouse's position on the board
     *
     * @return PVector with the mouse's position on the board
     */
    public PVector getMouse() {
        return new PVector(mouseX - camera.getX(), mouseY - camera.getY());
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

    /* SETTERS */
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
        frameRate(61);
        mouse = new MouseHandler(this);
        keyboard = new KeyHandler(this);
        ui = new UI(this);
        /* Cursor images */
        PImage blueCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-blue.png").toString());
        PImage orangeCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-orange.png").toString());
        PImage greenCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-green.png").toString());
        PImage redCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-red.png").toString());
        cursorImages.put(Flood.single(), blueCursor);
        cursorImages.put(BuildHut.single(), orangeCursor);
        cursorImages.put(GrowFood.single(), redCursor);
        cursorImages.put(GrowTree.single(), greenCursor);
        cursorImg = blueCursor;

        /* Resource Tile Images */
        treeImg = loadImage(Paths.get(System.getProperty("user.dir"), "images", "tree.png").toString());
        bushImg = loadImage(Paths.get(System.getProperty("user.dir"), "images", "bush.png").toString());
        rocksImg = loadImage(Paths.get(System.getProperty("user.dir"), "images", "rocks.png").toString());
        waterImg = loadImage(Paths.get(System.getProperty("user.dir"), "images", "water.png").toString());

        /* Hut images */
        defaultHut = loadImage(Paths.get(System.getProperty("user.dir"), "images", "default-hut.png").toString());
        berryHut = loadImage(Paths.get(System.getProperty("user.dir"), "images", "berry-hut.png").toString());
        waterHut = loadImage(Paths.get(System.getProperty("user.dir"), "images", "water-hut.png").toString());
        stoneHut = loadImage(Paths.get(System.getProperty("user.dir"), "images", "stone-hut.png").toString());
        woodHut = loadImage(Paths.get(System.getProperty("user.dir"), "images", "wood-hut.png").toString());
        buildHut = loadImage(Paths.get(System.getProperty("user.dir"), "images", "build-hut.png").toString());

        /* Villager images */
        vHeadImg = loadImage(Paths.get(System.getProperty("user.dir"), "images", "v-head.png").toString());
        gathererB = loadImage(Paths.get(System.getProperty("user.dir"), "images", "gathererB.png").toString());
        foodB = loadImage(Paths.get(System.getProperty("user.dir"), "images", "foodB.png").toString());
        waterB = loadImage(Paths.get(System.getProperty("user.dir"), "images", "waterB.png").toString());
        stoneB = loadImage(Paths.get(System.getProperty("user.dir"), "images", "stoneB.png").toString());
        woodB = loadImage(Paths.get(System.getProperty("user.dir"), "images", "woodB.png").toString());
        builderB = loadImage(Paths.get(System.getProperty("user.dir"), "images", "builderB.png").toString());

        camera = new Camera(MAP_PX_WIDTH, MAP_PX_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT, this);

        // skip the main menu
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
        Calendar.create(this);
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
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == LEFT) {
            mouse.handleLeft();
        }
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        if (event.getCount() == 1) {
            mouse.handleScrollDown();
        } else {
            mouse.handleScrollUp();
        }
    }

    @Override
    public void draw() {
        cursor(cursorImg, 5, 5);
        camera.execute();
        if (mousePressed && mouseButton == RIGHT) {
            mouse.handleRight();
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
