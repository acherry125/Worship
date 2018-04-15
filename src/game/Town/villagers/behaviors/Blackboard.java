package game.Town.villagers.behaviors;

import java.util.HashMap;

public class Blackboard {
    private static Blackboard ourInstance = new Blackboard();

    public static Blackboard single() {
        return ourInstance;
    }

    private Blackboard() {
        lookup = new HashMap<String, Object>();
    }

    HashMap<String, Object> lookup;

    public Object get(String key) {
        return lookup.get(key);
    }

    public void put(String key, Object val) {
        lookup.put(key, val);
    }
}
