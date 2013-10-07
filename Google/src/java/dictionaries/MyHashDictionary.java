package dictionaries;

/**
 *
 * @author rishabh
 */

class Pair<K extends Comparable<K>, V> {

    K key;
    V value;

    public Pair(K k, V v) {
        this.key = k;
        this.value = v;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

public class MyHashDictionary<K extends Comparable<K>, V> implements DictionaryInterface<K, V> {

    private int size;
    private Pair<K, V>[] dict;
    private int count;

    @SuppressWarnings("unchecked")
    public MyHashDictionary() {
        size = 10370;
        dict = new Pair[this.size];
        for (int i = 0; i < this.size; i++) {
            dict[i] = new Pair<K, V>(null, null);
        }
        count = 0;
    }

    public int hashCode(String str) {
        int code = 0;
        if (str.length() > 4) {
            for (int i = 0; i < 4; i++) {
                code = (code << 2) + str.charAt(i);
            }
        } else {
            for (int i = 0; i < str.length(); i++) {
                code = (code << 2) + str.charAt(i);
            }
        }
        return code;
    }
    
    @Override
    public void insert(K key, V value) {
        Pair<K, V> word = new Pair<K, V>(key, value);
        if (this.getValue(key) == null) {
            count++;
        }
        dict[hashCode((String) key)] = word;
    }

    @Override
    public void remove(K key) {
        if (this.getValue(key) != null) {
            count--;
        }
        dict[hashCode((String) key)].value = null;
        dict[hashCode((String) key)].key = null;
    }

    @Override
    public K[] getKeys() {
        K arr[] = (K[]) new String[count];
        int j = 0;
        for (int i = 0; i < this.size; i++) {
            if (dict[i].key != null) {
                arr[j++] = dict[i].key;
            }
        }
        return arr;
    }

    @Override
    public V getValue(K str) {
        return dict[hashCode((String) str)].value;
    }

}
