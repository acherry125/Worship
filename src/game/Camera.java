package game;

public class Camera {
    private static final float SHIFT_HORIZ = 15;
    private static final float SHIFT_VERT = 15;
    private float x;
    private float y;
    private final float mapWidth;
    private final float mapHeight;
    private final float screenWidth;
    private final float screenHeight;
    private final GodSim g;

    public Camera(float mapWidth, float mapHeight, float screenWidth, float screenHeight, GodSim g) {
        x = (-mapWidth / 2) + (screenWidth / 2);
        y = (-mapHeight / 2) + (screenHeight / 2);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.g = g;
    }

    /*** GETTERSs ***/
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    public void execute() {
        int LEFT_WASD = 65;
        int UP_WASD = 87;
        int RIGHT_WASD = 68;
        int DOWN_WASD = 83;

        if (g.getKeyPressed(g.LEFT) || g.getKeyPressed(LEFT_WASD)) {
            moveLeft();
        }
        if (g.getKeyPressed(g.UP) || g.getKeyPressed(UP_WASD)) {
            moveUp();
        }
        if (g.getKeyPressed(g.DOWN) || g.getKeyPressed(DOWN_WASD)) {
            moveDown();
        }
        if (g.getKeyPressed(g.RIGHT) || g.getKeyPressed(RIGHT_WASD)) {
            moveRight();
        }
    }
    private float getShiftHoriz() {
        if (g.getKeyPressed(g.SHIFT)) {
            return 2*SHIFT_HORIZ;
        }
        return SHIFT_HORIZ;
    }
    private float getShiftVert() {
        if (g.getKeyPressed(g.SHIFT)) {
            return 2*SHIFT_VERT;
        }
        return SHIFT_VERT;
    }

    private void moveLeft() {
        if (x + getShiftHoriz() < 0) {
            x += getShiftHoriz();
        } else {
            x = 0;
        }
    }

    private void moveRight() {
        if (x - getShiftHoriz() > -(mapWidth - screenWidth)) {
            x -= getShiftHoriz();
        } else {
            x = -(mapWidth - screenWidth);
        }
    }

    private void moveUp() {
        if (y + getShiftVert() < 0) {
            y += getShiftVert();
        } else {
            y = 0;
        }
    }

    private void moveDown() {
        if (y - getShiftVert() > -(mapHeight - screenHeight)) {
            y -= getShiftVert();
        } else {
            y = -(mapHeight - screenHeight);
        }
    }

}