package game.Town;

import java.util.HashMap;
import java.util.Random;

public class TownResources {
    private HashMap<RESOURCES, Integer> townNeeds;
    private Random rand = new Random();

    /**
     * Positive numbers means there is a need for that resource.
     * Negative needs means there is excess.
     */
    public TownResources() {
        townNeeds = new HashMap();
        createNeed(RESOURCES.WOOD, 0);
        createNeed(RESOURCES.WATER, 0);
        createNeed(RESOURCES.STONE, 0);
        createNeed(RESOURCES.FOOD, 0);
    }

    public Integer get(RESOURCES key) {
        return townNeeds.get(key);
    }

    /**
     * Increases the need of the given string by the value given.  If the need does not exist,
     * create it with a stating value of the value given.
     *
     * @param need the key of the need.
     */
    public void createNeed(RESOURCES need, int value) {
        int currentNeed;

        if (townNeeds.containsKey(need)) {
            currentNeed = townNeeds.get(need);
        } else {
            currentNeed = 0;
        }

        currentNeed += value;

        townNeeds.put(need, currentNeed);

    }

    public void reduceNeed(RESOURCES need, int num) {
        townNeeds.put(need, townNeeds.get(need) - num);
    }

    public void reduceNeed(RESOURCES need) {
        townNeeds.put(need, townNeeds.get(need) - 1);
    }

    public void raiseNeed(RESOURCES need, int num) {
        townNeeds.put(need, townNeeds.get(need) + num);
    }

    public void raiseNeed(RESOURCES need) {
        townNeeds.put(need, townNeeds.get(need) + 1);
    }


    /**
     * Gets the key/highest need of the village.
     *
     * @return the resource that the village needs most.
     */
    public RESOURCES nextResourceToCollect() {

        RESOURCES[] all = townNeeds.keySet().toArray(new RESOURCES[townNeeds.keySet().size()]);

        /*RESOURCES highestNeed = RESOURCES.WOOD;

        int highestNeedValue = -Integer.MIN_VALUE;

        for (RESOURCES need : townResources.keySet()) {
            if (townResources.get(need) > highestNeedValue) {
                highestNeed = need;
                highestNeedValue = townResources.get(need);
            }
        }*/

        return all[rand.nextInt(all.length)];
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (RESOURCES r : this.townNeeds.keySet()) {
            result.append(r + ": " + this.townNeeds.get(r) + "\n");
        }

        return result.toString();
    }
}