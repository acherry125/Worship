package game.Town;

import java.util.HashMap;

public class TownNeeds {
    private HashMap<RESOURCES, Integer> townNeeds;

    /**
     * Positive numbers means there is a need for that resource.
     * Negative needs means there is excess.
     */
    public TownNeeds() {
        townNeeds = new HashMap();
        createNeed(RESOURCES.WOOD, 5);
        createNeed(RESOURCES.WATER, -10);
        createNeed(RESOURCES.STONE, -10);
        createNeed(RESOURCES.FOOD, -20);
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
    public RESOURCES getHighestNeed() {
        RESOURCES highestNeed = RESOURCES.WOOD;

        int highestNeedValue = -Integer.MIN_VALUE;

        for (RESOURCES need : townNeeds.keySet()) {
            if (townNeeds.get(need) > highestNeedValue) {
                highestNeed = need;
                highestNeedValue = townNeeds.get(need);
            }
        }

        return highestNeed;
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
