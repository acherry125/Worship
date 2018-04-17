package game.Player;

import game.Board.AStructureTile;
import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Player.powers.IPower;
import game.Town.Town;
import processing.core.PVector;

public class Player {
    private final PowerManager pm;
    private final GodSim g;
    private final Town t;

    public Player() {
        this.t = Town.single();
        this.g = t.getGodSim();
        pm = new PowerManager(g);
    }

    public void actLeft(PVector loc) {
        ATile clickedTile = Board.single().getTile(g.getMouse());
        if (clickedTile.hasStructure()) {
            AStructureTile clickedStructure = (AStructureTile) clickedTile;
            clickedStructure.toggleType();
        }
    }

    public void actRight(PVector loc) {
        ATile clickedTile = Board.single().getTile(g.getMouse());
        pm.usePower(clickedTile);
    }
    public void rotatePower(boolean forward) {
        pm.rotatePower(forward);
    }

    public void equipPower(IPower power) {
        pm.equipPower(power);
    }
}
