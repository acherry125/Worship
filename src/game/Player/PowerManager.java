package game.Player;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Player.powers.BuildHut;
import game.Player.powers.Flood;
import game.Player.powers.GrowTree;
import game.Player.powers.IPower;
import game.Town.Town;
import game.Town.villagers.Villager;

import java.util.HashMap;

/** Responsible for Managing Powers for the Player, remembering which are active **/
public class PowerManager {
    GodSim g;
    Board board;
    Town town;

    IPower[] powers;
    IPower activePower;
    HashMap<IPower, Integer> powerIndices = new HashMap<IPower, Integer>();

    public PowerManager(GodSim g) {
        this.g = g;
        this.board = Board.single();
        powers = new IPower[]{Flood.single(), BuildHut.single(), GrowTree.single()};
        for (int i = 0; i < powers.length; i++) {
            powerIndices.put(powers[i], i);
        }
        equipPower(powers[0]);
        this.town = Town.single();
    }

    public void usePower(ATile tileSelected) {

        activePower.use(tileSelected, g);
        if (activePower instanceof GrowTree) {
            for (Villager villager : this.town.getVillagers()) {
                villager.changeBelief(100f);
            }
        }
    }

    public void equipPower(IPower power) {
        activePower = power;
    }

    public void rotatePower() {
        int currIndex = powerIndices.get(activePower);
        int newIndex = ++currIndex % powers.length;
        activePower = powers[newIndex];
        g.setCursor(activePower);
    }
}
