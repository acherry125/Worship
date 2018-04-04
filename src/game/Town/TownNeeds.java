package game.Town;

import java.util.HashMap;

public class TownNeeds {
    private HashMap<RESOURCES, Integer> villageNeeds;

    /**
     * Positive numbers means there is a need for that resource.
     * Negative needs means there is excess.
     */
    public TownNeeds() {
        villageNeeds = new HashMap();
        createNeed(RESOURCES.WOOD, 5);
        createNeed(RESOURCES.WATER, -10);
        createNeed(RESOURCES.STONE, -10);
        createNeed(RESOURCES.FOOD, -20);
    }

    public Integer get(RESOURCES key) {
        return villageNeeds.get(key);
    }

    /**
     * Increases the need of the given string by the value given.  If the need does not exist,
     * create it with a stating value of the value given.
     *
     * @param need the key of the need.
     */
    public void createNeed(RESOURCES need, int value) {
        int currentNeed;

        if (villageNeeds.containsKey(need)) {
            currentNeed = villageNeeds.get(need);
        } else {
            currentNeed = 0;
        }

        currentNeed += value;

        villageNeeds.put(need, currentNeed);

    }

    public void reduceNeed(RESOURCES need, int num) {
        villageNeeds.put(need, villageNeeds.get(need) - num);
    }

    public void reduceNeed(RESOURCES need) {
        villageNeeds.put(need, villageNeeds.get(need) - 1);
    }

    public void raiseNeed(RESOURCES need, int num) {
        villageNeeds.put(need, villageNeeds.get(need) + num);
    }

    public void raiseNeed(RESOURCES need) {
        villageNeeds.put(need, villageNeeds.get(need) + 1);
    }


    /**
     * Gets the key/highest need of the village.
     *
     * @return the resource that the village needs most.
     */
    public RESOURCES getHighestNeed() {
        RESOURCES highestNeed = RESOURCES.WOOD;

        int highestNeedValue = -Integer.MIN_VALUE;

        for (RESOURCES need : villageNeeds.keySet()) {
            if (villageNeeds.get(need) > highestNeedValue) {
                highestNeed = need;
                highestNeedValue = villageNeeds.get(need);
            }
        }

        return highestNeed;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (RESOURCES r : this.villageNeeds.keySet()) {
            result.append(r + ": " + this.villageNeeds.get(r) + "\n");
        }

        return result.toString();
    }
}
