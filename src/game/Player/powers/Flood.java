package game.Player.powers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;

public class Flood implements IPower {
    private static final Flood ourInstance = new Flood();

    public static Flood single() {
        return ourInstance;
    }

    private Flood() {}

    @Override
    public void use(ATile clickedTile, GodSim g) {
        Board.single().floodTile(clickedTile);
    }
}
