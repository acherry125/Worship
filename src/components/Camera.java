package components;

public class Camera {
    static final float SHIFT_HORIZ = 20;
    static final float SHIFT_VERT = 20;
    float x;
    float y;
    float mapWidth;
    float mapHeight;
    float screenWidth;
    float screenHeight;
    public Camera(float mapWidth, float mapHeight, float screenWidth, float screenHeight) {
        x = 0;
        y = 0;
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