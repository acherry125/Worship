package game.Player;

import game.Board.ATile;
import game.GodSim;
import game.Player.powers.IPower;
import processing.core.PVector;

public class Player {
    PowerManager pm;
    GodSim g;
    public Player(GodSim g) {
        this.g = g;
        pm = new PowerManager(g);
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
