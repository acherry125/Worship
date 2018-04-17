package game.Player.powers;

import game.Board.ATile;
import game.GodSim;

public interface IPower {
    /* Use the power */
    void use(ATile clickedTile, GodSim g);
}
