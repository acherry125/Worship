package components;
import static processing.core.PApplet.println;


public class Camera {
    private static final float SHIFT_HORIZ = 20;
    private static final float SHIFT_VERT = 20;
    private float x;
    private float y;
    private float mapWidth;
    private float mapHeight;
    private float screenWidth;
    private float screenHeight;
    public Camera(float mapWidth, float mapHeight, float screenWidth, float screenHeight) {
        x = (-mapWidth / 2) + (screenWidth / 2);
        y = (-mapHeight / 2) + (screenHeight / 2);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    public void moveLeft() {
        if (x < 0) {
            x += SHIFT_HORIZ;
        }
    }
    public void moveRight() {
        if (x > - (mapWidth - screenWidth)) {
            x -= SHIFT_HORIZ;
        }
    }
    public void moveUp() {
        if (y < 0) {
            y += SHIFT_HORIZ;
        }
    }
    public void moveDown() {
        if (y > - (mapHeight - screenHeight)) {
            y -= SHIFT_HORIZ;
        }
    }
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }
}