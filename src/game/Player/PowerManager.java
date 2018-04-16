package game.Player;

import game.Board.ATile;
import game.Board.Board;
import game.GodSim;
import game.Player.powers.BuildHut;
import game.Player.powers.Flood;
import game.Player.powers.GrowFood;
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
    private int timeLastUsedPower = -5000;
    private int lastUsedPowerInterval = 5000;

    public PowerManager(GodSim g) {
        this.g = g;
        this.board = Board.single();
        powers = new IPower[]{Flood.single(), BuildHut.single(), GrowTree.single(), GrowFood.single()};
        for (int i = 0; i < powers.length; i++) {
            powerIndices.put(powers[i], i);
        }
        equipPower(powers[0]);
        this.town = Town.single();
    }

    public void usePower(ATile tileSelected) {
        activePower.use(tileSelected, g);
        godPowerUsed();
    }

    private void godPowerUsed() {
        town.resetGodPowerTimer();
        town.powerWasUsedRecently();
        if (g.millis() - timeLastUsedPower > lastUsedPowerInterval) {
            timeLastUsedPower = g.millis();
            for (Villager villager : this.town.getVillagers()) {
                if (activePower instanceof BuildHut) {
                    villager.changeBelief(g.map((float) Math.random(), 0, 1, 0.5f, 1f));
                } else {
                    villager.changeBelief(g.map((float) Math.random(), 0, 1, 0.15f, 1f));
                }
            }
        }
    }

    public void equipPower(IPower power) {
        activePower = power;
    }

    public void rotatePower(boolean forward) {
        int currIndex = powerIndices.get(activePower);
        int newIndex;
        if (forward) {
            newIndex = Math.floorMod(++currIndex, powers.length);
        } else {
            newIndex = Math.floorMod(--currIndex, powers.length);
        }
        activePower = powers[newIndex];
        g.setCursor(activePower);
    }
}
