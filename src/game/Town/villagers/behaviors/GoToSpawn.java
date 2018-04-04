package game.Town.villagers.behaviors;

import game.Board.Board;
import game.Town.TownResources;
import game.Town.villagers.Villager;

import static processing.core.PApplet.println;

/**
 * Does the behavior for the villager given to go to the spawn.
 */
public class GoToSpawn extends GoToTile {

    public GoToSpawn(Villager villager, TownResources townResources, Board board) {
        super(villager, townResources, board);
        super.goToTile(board.getSpawnTile());
    }

    @Override
    public int execute() {
        this.villager.move();
        return 1;
    }
}
