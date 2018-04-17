package game.Player;

import game.Board.ATile;
import game.Board.Board;
import game.Calendar;
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
class PowerManager {
    private final GodSim g;
    private final Board board;
    private final Town town;

    private final IPower[] powers;
    private IPower activePower;
    private final HashMap<IPower, Integer> powerIndices = new HashMap<IPower, Integer>();
    private int timeLastUsedPower = -5000;
    private final int lastUsedPowerInterval = 5000;

    public PowerManager(GodSim g) {
        this.g = g;
        this.board = Board.single();
        powers = new IPower[]{BuildHut.single(), Flood.single(), GrowTree.single(), GrowFood.single()};
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
        if (Calendar.single().millis() - timeLastUsedPower > lastUsedPowerInterval) {
            timeLastUsedPower = Calendar.single().millis();
            for (Villager villager : this.town.getVillagers()) {
                if (activePower instanceof BuildHut) {
                    villager.changeBelief(g.map((float) Math.random(), 0, 1, 0.25f, .5f));
                } else {
                    villager.changeBelief(g.map((float) Math.random(), 0, 1, 0.15f, .4f));
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
