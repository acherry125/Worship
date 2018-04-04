package game;

import game.Board.Board;
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

    public final float CELLS_WIDE = 30;
    public final float CELLS_TALL = 30;

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

    PImage cursorImg;
    HashMap<IPower, PImage> cursorImages = new HashMap<IPower, PImage>();

    @Override
    public void setup() {
        click = new ClickHandler(this);
        initializeBoard();
        initializeTown();
        initializePlayer();
        camera = new Camera(MAP_PX_WIDTH, MAP_PX_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT);
        ui = new UI(this);
        PImage blueCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-blue.png").toString());
        PImage redCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-red.png").toString());
        PImage greenCursor = loadImage(Paths.get(System.getProperty("user.dir"), "images", "cursor-green.png").toString());
        cursorImages.put(Flood.single(), blueCursor);
        cursorImages.put(BuildHut.single(), redCursor);
        cursorImages.put(GrowTree.single(), greenCursor);
        cursorImg = blueCursor;
    }

    @Override
    public void settings() {
        //size((int) (SCREEN_WIDTH), (int) (SCREEN_HEIGHT));
        fullScreen();
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
     * Initialize the town
     */
    private void initializePlayer() {
        player = new Player(this);
    }

    public void setCursor(IPower power) {
        cursorImg = cursorImages.get(power);
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
    public Town getTown() {
        return town;
    }
    public Camera getCamera() {
        return camera;
    }
    public Player getPlayer() {
        return player;
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
    public void mouseClicked(MouseEvent event) {
        if (event.getButton() == RIGHT) {
            click.handleRight();
        }
    }

    @Override
    public void draw() {
        cursor(cursorImg);
        if (mousePressed && mouseButton == LEFT) {
            click.handleLeft();
        }
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
