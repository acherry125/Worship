package game.Player;

import game.Board.ATile;
import game.GodSim;
import game.Player.powers.IPower;
import game.Town.Town;
import processing.core.PVector;

public class Player {
    PowerManager pm;
    GodSim g;
    Town t;

    public Player(GodSim g, Town t) {
        this.g = g;
        this.t = t;
        pm = new PowerManager(g, t);
    }

    public void act(PVector loc, boolean leftClick) {
        ATile clickedTile = g.getBoard().getTile(g.getMouse());
        if (leftClick) {
            pm.usePower(clickedTile);
        } else {
            pm.rotatePower();
        }
    }

    public void equipPower(IPower power) {
        pm.equipPower(power);
    }
}
