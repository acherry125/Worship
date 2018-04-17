package game.Player.powers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.RESOURCES;

public class GrowTree implements IPower {
    private static final GrowTree ourInstance = new GrowTree();

    public static GrowTree single() {
        return ourInstance;
    }

    private GrowTree() {}

    @Override
    public void use(ATile clickedTile, GodSim g) {
        Board.single().updateTileSlot(clickedTile, RESOURCES.WOOD);
    }
}
