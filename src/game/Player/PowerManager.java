package game.Player;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Player.powers.BuildHut;
import game.Player.powers.Flood;
import game.Player.powers.IPower;
import game.Town.Town;

/** Responsible for Managing Powers for the Player, remembering which are active **/
public class PowerManager {
    GodSim g;
    Board board;
    Town town;

    IPower activePower;

    public PowerManager(GodSim g) {
        this.g = g;
        this.board = g.getBoard();
        equipPower(new Flood());
    }

    public void usePower(ATile tileSelected) {
        activePower.use(tileSelected, g);
    }

    public void equipPower(IPower power) {
        activePower = power;
    }
}
