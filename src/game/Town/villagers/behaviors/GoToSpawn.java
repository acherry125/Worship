package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownNeeds;
import game.Town.villagers.Villager;

import static processing.core.PApplet.println;

/**
 * Does the behavior for the villager given to go to the spawn.
 */
public class GoToSpawn extends GoToTile {

    public GoToSpawn(Villager villager, TownNeeds townNeeds, Board board) {
        super(villager, townNeeds, board);
    }

    @Override
    public int execute() {
        println(villager.getTarget());
        super.goToTile(board.getSpawnTile());
        this.villager.move();
        return 1;
    }
}
