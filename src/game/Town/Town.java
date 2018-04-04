package game.Town;

import java.util.ArrayList;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Town.villagers.behaviors.explorer.Explorer;
import game.Town.villagers.VILLAGER_ROLES;
import game.Town.villagers.Villager;

import static processing.core.PApplet.println;

public class Town {
    GodSim g;
    Board board;
    ArrayList<Villager> villagers = new ArrayList<Villager>();
    TownNeeds townNeeds;

    public Town(GodSim g) {
        this.g = g;
        this.board = g.getBoard();
        townNeeds = new TownNeeds();
        initialize();
    }

    /**
     * Spawns a villager on the map at the spawn point
     *
     * @return the spawned Villager
     */
    public Villager spawn() {
        ATile spawnTile = board.getSpawnTile();
        Villager villager = new Villager(this, spawnTile.getXPx() + 100, spawnTile.getYPx() - 100,
                VILLAGER_ROLES.BUILDER, g);
        villagers.add(villager);

        Villager villager2 = new Villager(this, spawnTile.getXPx() -230, spawnTile.getYPx() - 230,
                VILLAGER_ROLES.EXPLORER, g);
        villagers.add(villager2);

        // TODO: can make village needs itself increment, but at what rate? putting here for now.

        return villager;
    }

    public TownNeeds getTownNeeds() {
        return townNeeds;
    }

    public boolean canSupportHut() {
        return townNeeds.get(RESOURCES.WOOD) <= 0;
    }

    public void draw() {

        for (Villager villager : villagers) {
            villager.initializeBTree();
            villager.act();
            villager.draw();
        }
    }

    /**
     * Creates a new villager in the village.
     */
    private void initialize() {
        spawn();
    }
}
