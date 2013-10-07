package dictionaries;

/**
 *
 * @author rishabh
 */
import java.util.Enumeration;
import java.util.Hashtable;

public class HashDictionary<K extends Comparable<K>, V> implements DictionaryInterface<K, V> {

    private Hashtable<K, V> table;

    public HashDictionary() {
        table = new Hashtable<K, V>();
    }
    
    @Override
    public void insert(K key, V value) {
        table.put(key, value);
    }

    @Override
    public void remove(K key) {
        table.remove(key);
    }
    
    @Override
    public K[] getKeys() {
        K str;
        int i = 0;
        K arr[] = (K[]) new String[table.size()];
        Enumeration<K> elements;
        elements = table.keys();
        while (elements.hasMoreElements()) {
            str = elements.nextElement();
            arr[i++] = str;
        }
        return arr;
    }

    @Override
    public V getValue(K str) {
        return table.get(str);
    }
    
}
