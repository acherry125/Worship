package game.Player.powers;

import game.Board.ATile;
import game.GodSim;
import processing.core.PVector;

public class BuildHut implements IPower {
    private static BuildHut ourInstance = new BuildHut();

    public static BuildHut single() {
        return ourInstance;
    }

    private BuildHut() {}

    @Override
    public void use(ATile clickedTile, GodSim g) {
        g.getBoard().buildHutOnTile(clickedTile);
    }
}
