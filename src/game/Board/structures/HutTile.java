package game.Board.structures;

import game.Board.AStructureTile;
import game.Town.villagers.VILLAGER_ROLES;

public class HutTile extends AStructureTile {
    public HutTile(int indX, int indY, float cell_w, float cell_h) {
        super(indX, indY, cell_w, cell_h);
    }

    @Override
    public void draw() {
        super.draw();
        drawHut();
    }

    public VILLAGER_ROLES supportedRole() {
        switch(getType()) {
            case STONE:
                return VILLAGER_ROLES.STONEGATHERER;
            case WOOD:
                return VILLAGER_ROLES.WOODGATHERER;
            case WATER:
                return VILLAGER_ROLES.WATERGATHERER;
            case FOOD:
                return VILLAGER_ROLES.FOODGATHERER;
            case BUILD:
                return VILLAGER_ROLES.BUILDER;
            default:
                return VILLAGER_ROLES.GATHERER;
        }
    }

    /**
     * Draw a hut on this tile
     */
    private void drawHut() {
        float paddingW = 5;
        float paddingH = 10;
        float pX = (indX * cell_w) + paddingW;
        float pY = (indY * cell_h) + paddingH;
        float pWidth = cell_w - 2*paddingW;
        float pHeight = cell_h - 2*paddingH;

        switch(getType()) {
            case DEFAULT:
                g.image(g.defaultHut, pX, pY, pWidth, pHeight);
                break;
            case STONE:
                g.image(g.stoneHut, pX, pY, pWidth, pHeight);
                break;
            case WOOD:
                g.image(g.woodHut, pX, pY, pWidth, pHeight);
                break;
            case WATER:
                g.image(g.waterHut, pX, pY, pWidth, pHeight);
                break;
            case FOOD:
                g.image(g.berryHut, pX, pY, pWidth, pHeight);
                break;
            case BUILD:
                g.image(g.buildHut, pX, pY, pWidth, pHeight);
                break;
            default: break;
        }
    }
}
