package dictionaries;

/**
 *
 * @author rishabh
 */

class ListNode<K extends Comparable<K>, V> {

    K key;
    V value;
    ListNode<K, V> next;

    public ListNode(K k, V v) {
        this.key = k;
        this.value = v;
        this.next = null;
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

    public ListNode<K, V> getNext() {
        return next;
    }

    public void setNext(ListNode<K, V> next) {
        this.next = next;
    }
}


public class ListDictionary<K extends Comparable<K>, V> implements DictionaryInterface<K, V> {

    private ListNode<K, V> head;
    private int count;

    //constructor
    public ListDictionary() {
        this.head = new ListNode<K, V>(null, null);
        this.count = 0;
    }
    
    @Override
    public void insert(K key, V value) {
        int flag = 0;
        ListNode<K, V> current = new ListNode<K, V>(key, value);
        ListNode<K, V> h1 = head;
        while (h1.next != null) {
            flag = 0;
            h1 = h1.next;
            if (h1.key.equals(key)) {
                h1.value = value;
                flag = 1;
                break;
            }

        }
        if (flag == 0) {
            count++;
            h1.setNext(current);
        }
    }
    
    @Override
    public void remove(K key) {
        ListNode<K, V> h2 = head;
        ListNode<K, V> h1 = head;
        boolean flag = false;
        while (h1.next != null) {
            h2 = h1;
            h1 = h1.next;
            if (h1.key.equals(key)) {
                flag = true;
                count--;
                break;
            }
        }
        if (flag == true) {
            h2.next = h2.next.next;
        }
    }
    
    @Override
    public V getValue(K str) {
        ListNode<K, V> h1 = head;
        try {
            while (h1.next != null) {
                h1 = h1.next;
                if (h1.key.equals(str)) {
                    return h1.value;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    @Override
    public K[] getKeys() {
        ListNode<K, V> h1 = head.next;
        @SuppressWarnings("unchecked")
        K arr[] = (K[]) new String[count];
        int i = 0;
        try {
            while (h1.next != null) {
                arr[i++] = h1.getKey();
                h1 = h1.next;
            }
            arr[i] = h1.getKey();
        } catch (Exception e) {
        }
        return arr;
    }
}