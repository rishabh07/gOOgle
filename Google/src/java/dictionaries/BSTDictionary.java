package dictionaries;

/**
 *
 * @author rishabh
 */

class BinaryNode<K extends Comparable<K>, V> {

    private K key;
    private V value;
    private BinaryNode<K, V> parent;
    private BinaryNode<K, V> left;
    private BinaryNode<K, V> right;

    public BinaryNode(K k, V v) {
        this.key = k;
        this.value = v;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public BinaryNode() {
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public BinaryNode<K, V> getParent() {
        return parent;
    }

    public void setParent(BinaryNode<K, V> parent) {
        this.parent = parent;
    }

    public BinaryNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<K, V> left) {
        this.left = left;
    }

    public BinaryNode<K, V> getRight() {
        return right;
    }

    public void setRight(BinaryNode<K, V> right) {
        this.right = right;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

public class BSTDictionary<K extends Comparable<K>, V> implements DictionaryInterface<K, V> {

    private BinaryNode<K, V> root;
    private int count;

    public BSTDictionary() {
        this.root = new BinaryNode<K, V>();
        this.count = 0;
    }

    
    @Override
    public void insert(K key, V value) {
        BinaryNode<K, V> newNode;
        if (this.root.getKey() == null) {
            //	System.out.println("First time!");
            newNode = new BinaryNode<K, V>(key, value);
            this.root = newNode;
            this.count++;
        } else {
            //	System.out.println("Growing!");
            BinaryNode<K, V> parent = this.search(this.root, key);
            //	System.out.println("Parent = "+parent.getValue());
            if (parent.getKey().equals(key)) {
                //		System.out.println("Repeat!");
                parent.setValue(value);
            } else {
                newNode = new BinaryNode<K, V>(key, value);
                newNode.setParent(parent);
                this.count++;
                if (key.compareTo(parent.getKey()) < 0) {
                    parent.setLeft(newNode);
                    //			System.out.println("I am a left child!");
                } else {
                    parent.setRight(newNode);
                    //			System.out.println("I am a right child!");
                }
            }
        }
    }

    public void remove(K str) {
        //String key1=(String) str;
        //int key = key1.charAt(0)-96;
        BinaryNode<K, V> current = root;
        BinaryNode<K, V> parent = root;
        boolean isLeftChild = true;
        while (str.compareTo(current.getKey()) != 0) { 			// search for node
            //System.out.println("current "+current.getValue());
            parent = current;
            if (str.compareTo(current.getKey()) < 0) { // go left?
                isLeftChild = true;
                current = current.getLeft();
                //System.out.println("current ll"+current.getValue());
            } else {// or go right?
                isLeftChild = false;
                current = current.getRight();
                //System.out.println("current rr"+current.getValue());
            }
            if (current == null) // end of the line,
            {
                System.out.println("didn't find");
            }
            //break; // didn't find it
        } // end while
        // found node to delete
        // continues...
        // delete() continued...
        // if no children, simply delete it

        if (current.getLeft() == null && current.getRight() == null) {
            this.count--;
            if (current == root) // if root,
            {
                root = null; // tree is empty
            } else if (isLeftChild) {
                parent.setLeft(null); // disconnect
            } else // from parent
            {
                parent.setRight(null);
            }
        } // continues...
        // delete() continued...
        // if no right child, replace with left subtree
        else if (current.getRight() == null) {
            this.count--;
            if (current == root) {
                root = current.getLeft();
            } else if (isLeftChild) // left child of parent
            {
                parent.setLeft(current.getLeft());
            } else // right child of parent
            {
                parent.setRight(current.getLeft());
            }
        } // if no left child, replace with right subtree
        else if (current.getLeft() == null) {
            count--;
            if (current == root) {
                root = current.getRight();
            } else if (isLeftChild) // left child of parent
            {
                parent.setLeft(current.getRight());
            } else // right child of parent
            {
                parent.setRight(current.getRight());
            }
        } // delete() continued
        else {// two children, so replace with inorder successor
            // get successor of node to delete (current)
            this.count--;
            BinaryNode<K, V> succes = successor(current);
            // connect parent of current to successor instead
            if (current == root) {
                root = succes;
            } else if (isLeftChild) {
                parent.setLeft(succes);//= ;
            } else {
                parent.setRight(succes);// = ;
            }			// connect successor to current's left child
            succes.setLeft(current.getLeft());
            succes.setRight(current.getRight());
        } // end else two children
        // (successor cannot have a left child)
        //return true;
    } // end delete()
    
    @Override
    public K[] getKeys() {
        K arr[] = (K[]) new String[count];
        this.counter = 0;
        return inOrder(this.root, arr);
    }
    
    @Override
    public V getValue(K str) {
        BinaryNode<K, V> current = root;
        try {
            while (current.getKey() != null) {
                if (str.compareTo(current.getKey()) <= 0) {
                    if (current.getKey().compareTo(str) == 0) {
                        return current.getValue();
                    }
                    current = current.getLeft();
                } else {
                    if (current.getKey().compareTo(str) == 0) {
                        return current.getValue();
                    }
                    current = current.getRight();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    private int counter;

    public K[] inOrder(BinaryNode<K, V> root, K[] arr1) {
        try {
            if (root.getLeft() != null) {
                this.inOrder(root.getLeft(), arr1);
            }
            arr1[counter++] = root.getKey();
            if (root.getRight() != null) {
                this.inOrder(root.getRight(), arr1);
            }
        } catch (Exception e) {
        }
        return arr1;
    }
    
    private BinaryNode<K, V> search(BinaryNode<K, V> curr, K key) {
        while (curr.getLeft() != null || curr.getRight() != null) {
            if (key.compareTo((K) curr.getKey()) <= 0) {
                if (curr.getLeft() != null) {
                    curr = curr.getLeft();
                } else {
                    return curr;
                }
            } else if (key.compareTo((K) curr.getKey()) > 0) {
                if (curr.getRight() != null) {
                    curr = curr.getRight();
                } else {
                    return curr;
                }
            }
            search(curr, key);
        }
        return curr;
    }

    public void disp() {
        this.inOrder(root);
    }

    public void inOrder(BinaryNode<K, V> root) {
        if (root != null) {
            this.inOrder(root.getLeft());
            System.out.println("Traversing = " + root.getValue());
            this.inOrder(root.getRight());
        }
    }

    
    public BinaryNode<K, V> successor(BinaryNode<K, V> n) {
        if (n.getRight() != null) {
            BinaryNode<K, V> cur = n.getRight();
            while (cur.getLeft() != null) {
                cur = cur.getLeft();
            }
            cur.getParent().setLeft(null);
            return cur;
        } else {
            BinaryNode<K, V> cur = n;
            while (cur.getParent() != null && cur.getParent().getRight().getKey() == cur.getKey()) {
                cur = cur.getParent();
            }
            return cur.getParent();
        }
    }
}
