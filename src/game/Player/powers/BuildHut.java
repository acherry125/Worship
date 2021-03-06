package game.Player.powers;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;

public class BuildHut implements IPower {
    private static final BuildHut ourInstance = new BuildHut();

    public static BuildHut single() {
        return ourInstance;
    }

    private BuildHut() {}

    @Override
    public void use(ATile clickedTile, GodSim g) {
        Board.single().buildHutOnTile(clickedTile);
    }
}
