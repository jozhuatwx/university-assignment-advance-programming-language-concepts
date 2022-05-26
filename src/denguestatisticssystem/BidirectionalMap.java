package denguestatisticssystem;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class BidirectionalMap<K, V> {
  // properties
  private HashMap<K, V> map = new HashMap<>();
  private HashMap<V, K> reversedMap = new HashMap<>();

  // add values to map and reversed map
  public void put(K key, V value) {
    map.put(key, value);
    reversedMap.put(value, key);
  }

  // get value based on key
  public V getValue(K key) {
    return map.get(key);
  }

  // get key based on value
  public K getKey(V value) {
    return reversedMap.get(value);
  }

  // get map size
  public int size() {
    return map.size();
  }

  // get entry set
  public Set<Entry<K, V>> entrySet() {
    return map.entrySet();
  }
}
