package game.Player.powers;

import game.Board.ATile;
import game.GodSim;
import processing.core.PVector;

public class Flood implements IPower {
    private static Flood ourInstance = new Flood();

    public static Flood single() {
        return ourInstance;
    }

    private Flood() {}

    @Override
    public void use(ATile clickedTile, GodSim g) {
        g.getBoard().floodTile(clickedTile);
    }
}
