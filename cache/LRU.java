import java.util.*;

class LRU {
    private int size;
    private Map<String, Integer> keyToIdx = new HashMap<>();
    private LinkedList<KeyVal> KeyValLL = new LinkedList<>();
    
    public LRU(int size) {
	this.size = size;
    }

    // returns value or null & updates 'hotness' of key/val 
    public Object get(String key) {
	if (keyToIdx.containsKey(key)) {
	    int idx = keyToIdx.get(key);
	    KeyVal hot = KeyValLL.get(idx);
	    KeyValLL.remove(idx);
	    KeyValLL.offerFirst(hot);
	    return hot.val;
	} else {
	    return null; // wasn't present
	}
    }

    // if oversized, kicks off oldest/nothot key/val
    public void set(String key, Object val) {
	if (keyToIdx.containsKey(key)) {
	    return; // do nothing (we assume that setting an element into LRU doesn't increase it's useness/hotness
	} else {
	    if (keyToIdx.size()+1 < size) {
		KeyValLL.offerLast(new KeyVal(key,val));
		keyToIdx.put(key, KeyValLL.size()-1);
	    } else {
		KeyVal cold = KeyValLL.getLast();
		KeyValLL.offerLast(new KeyVal(key,val));
		keyToIdx.remove(cold.key);
		keyToIdx.put(key, KeyValLL.size()-1);
	    }
	}
    }
    
    private class KeyVal {
	public String key;
	public Object val;
        public KeyVal(String key, Object val) { this.key = key; this.val = val; }
    }

    public static void main(String[] args) {
	LRU lru = new LRU(3);
	lru.set("0", "a");
	lru.set("1", "b");
	lru.set("2", "c");
	System.out.println(lru.get("2"));
	System.out.println(lru.get("1"));
	lru.set("3", "d");
	System.out.println( lru.get("0") ); // should be null
    }
}
