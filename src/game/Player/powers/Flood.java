package game.Player.powers;

import game.Board.ATile;
import game.GodSim;
import processing.core.PVector;

public class Flood implements IPower {
    @Override
    public void use(ATile clickedTile, GodSim g) {
        g.getBoard().floodTile(clickedTile);
    }
}
