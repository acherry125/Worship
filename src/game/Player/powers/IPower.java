package game.Player.powers;

import game.Board.ATile;
import game.GodSim;
import processing.core.PVector;

public interface IPower {
    /* Use the power */
    public void use(ATile clickedTile, GodSim g);
}
