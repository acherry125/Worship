package components;
import processing.core.PApplet;
import static processing.core.PApplet.println;

public class Tile {
    private int x;
    private int y;
    private boolean spawner = false;
    private boolean hasTree = false;
    private boolean hasStone = false;
    private float temp;
    private float water;
    private float cell_w;
    private float cell_h;
    private PApplet g;
    public Tile(int x, int y, float cell_w, float cell_h, PApplet game) {
        this.x = x;
        this.y = y;
        this.cell_w = cell_w;
        this.cell_h = cell_h;
        this.g = game;
        initialize();
    }
    private void initialize() {
        float noise1 = (float) g.noise((float) (x*2.5), (float) (y*2.5));
        float noise2 = (float) g.noise((float) (x * 1.5), (float) (y * 1.5));
        float noise3 = (float) g.noise((float) (x * 1.25), (float) (y * 1.25));
        float noise4 = (float) g.noise((float) (x * 1.1), (float) (y * 1.1));
        float minTemp = 120;
        float offset = 230 - minTemp;
        setTemp((noise1 * offset) + minTemp);
        setWater(noise2);
        setResource(noise3);
    }
    private void setTemp(float temp) {
        this.temp = temp;
    }
    private void setWater(float water) {
        this.water = water;
    }
    private void setResource(float res) {
        if (res > 0.6) {
            hasTree = true;
        } else if (res < 0.25) {
            hasStone = true;
        }
    }
    public float getWater() {
        return this.water;
    }
    public float getTemp() {
        return this.temp;
    }
    public void setAsSpawner() {
        this.spawner = true;
    }
    public void drawSquareBase(int red, int green, int blue) {
        g.fill(red, green, blue);
        float xPixel = x * cell_w;
        float yPixel = y * cell_h;
        g.rect(xPixel, yPixel, cell_w, cell_h);
    }
    public void drawHut() {
        g.fill(255, 100, 0);
        float padding = 10;
        float pX = (x * cell_w) + padding;
        float pY = (y * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        float x1 = pX + (pWidth / 2);
        float y1 = pY;
        float x2 = pX;
        float y2 = pY + pHeight;
        float x3 = pX + pWidth;
        float y3 = pY + pHeight;

        g.triangle(x1, y1, x2, y2, x3, y3);
        g.triangle(x1, y1 - 3, x2, y2 - 20, x3, y3 - 20);
    }

    public void drawTree() {
        float padding = 10;
        float pX = (x * cell_w) + padding;
        float pY = (y * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        float x1 = pX + (pWidth / 2);
        float y1 = pY;
        float x2 = pX;
        float y2 = pY + pHeight;
        float x3 = pX + pWidth;
        float y3 = pY + pHeight;

        g.fill(160, 82, 45);
        g.rect(x1 - 4, y1 + 10, 8, pHeight);
        g.fill(0, 130, 0);
        g.triangle(x1, y1, x2, y2, x3, y3);
    }

    public void drawStone() {
        float padding = 10;
        float pX = (x * cell_w) + padding;
        float pY = (y * cell_h) + padding;
        float pWidth = cell_w - 2*padding;
        float pHeight = cell_h - 2*padding;

        g.fill(200,200,200);
        g.ellipseMode(g.CORNER);
        g.ellipse(pX, pY, pWidth, pHeight);
        g.ellipse(pX + 40, pY + 34, pWidth / 3, pHeight / 3);
        g.ellipse(pX + 5, pY + 5, pWidth / 4, pHeight / 3);
    }

    public void draw() {

        if (spawner) {
            int red = 255;
            int green = 140;
            int blue = 0;
            g.fill(red, green, blue);
            drawSquareBase(red, green, blue);
        }
        else if (getWater() > 0.66 && !spawner) {
            int green = 200;
            drawSquareBase(0, green, (int)(getWater() * 255));
        } else {
            int otherColors = 80;
            drawSquareBase(otherColors, (int)getTemp(), otherColors);
            if (hasTree) {
                drawTree();
            } else if (hasStone) {
                drawStone();
            }
        }


        g.textSize(cell_w / 5);
        g.fill(200, 200, 210);
        //g.text(String.format("%d, %d", x, y), xPixel + 5, yPixel + (cell_h / 2));
    }
}
