package game.Player;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Player.powers.IPower;
import game.Town.Town;
import game.Town.villagers.Villager;
import processing.core.PVector;

public class Player {
    PowerManager pm;
    GodSim g;
    Town t;

    public Player() {
        this.t = Town.single();
        this.g = t.getGodSim();
        pm = new PowerManager(g);
    }

    public void act(PVector loc, boolean leftClick) {
        ATile clickedTile = Board.single().getTile(g.getMouse());
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
