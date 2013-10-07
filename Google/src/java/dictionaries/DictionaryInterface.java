package dictionaries;

/**
 *
 * @author rishabh
 */

/**
 * This class is an interface for the Dictionary Data Structures used in
 * Mini Google project
 *
 */
public interface DictionaryInterface<K extends Comparable<K>, V> {
   
    /**
     * This method inserts a key value pair into the Dictionary Data Structure
     *
     * @params K, V
     * @return NULL
     */
    public void insert(K key, V value);
    
    /**
     * This method takes a key and removes a particular key,value pair from the
     * Dictionary Structure
     *
     * @param obj
     */
    public void remove(K key);    
    
    /**
     * This method takes a key value as input and retrieves the corresponding
     * value of it
     *
     * @param str
     * @return V
     */
    public V getValue(K str);

    /**
     * This method returns all the keys as an array.
     *
     * @return
     */
    public K[] getKeys();
}
