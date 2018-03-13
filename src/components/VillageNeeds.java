package components;

import java.util.HashMap;

public class VillageNeeds {
  private HashMap<String, Object> villageNeeds;

  public VillageNeeds() {
    villageNeeds = new HashMap();
  }

  public Object get(String key) {
    return villageNeeds.get(key);
  }

  public void put(String key, Object val) {
    villageNeeds.put(key, val);
  }
}
