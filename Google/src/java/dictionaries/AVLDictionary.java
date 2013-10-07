package dictionaries;

/**
 *
 * @author rishabh
 */
class AVLNode<K extends Comparable<K>, V> {

    K key;
    V value;
    int height;
    AVLNode<K, V> left;
    AVLNode<K, V> right;
    AVLNode<K, V> parent;

    public AVLNode() {
        key = null;
        value = null;
        left = null;
        right = null;
        this.height = 0;
    }

    public AVLNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 0;
    }
    
    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }
    
    public AVLNode<K, V> getParent() {
        return parent;
    }

    public void setParent(AVLNode<K, V> parent) {
        this.parent = parent;
    }
    
    public AVLNode<K, V> getLeft() {
        return left;
    }
    
    public void setLeft(AVLNode<K, V> left) {
        this.left = left;
    }
    
    public AVLNode<K, V> getRight() {
        return right;
    }
    
    public void setRight(AVLNode<K, V> right) {
        this.right = right;
    }
}

public class AVLDictionary<K extends Comparable<K>, V> implements DictionaryInterface<K, V> {

    private AVLNode<K, V> root;
    int count, i;
    boolean isLeftChild;

    public AVLDictionary() {
        this.root = new AVLNode<K, V>();
        this.count = 0;
        this.isLeftChild = true;
    }
    
    @Override
    public void insert(K key, V value) {
        this.root = insert(key, value, root);
        //	System.out.println("Modified Root = "+root.getKey());
    }

    @SuppressWarnings("unchecked")
    private AVLNode<K, V> insert(K key, V value, AVLNode<K, V> node) {
        //System.out.println("From : "+node.getKey());
        if (node.key == null) {
            //	System.out.println("First Time");
            node = new AVLNode<K, V>(key, value);
            count++;
        } else if (key.compareTo((K) node.getKey()) < 0) {
            //	System.out.println("I am a left child!");
            if (node.left != null) {
                node.left = insert(key, value, node.left);
            } else {
                node.left = new AVLNode<K, V>(key, value);
                node.left.parent = node;
                count++;
            }
            if (height(node.left) - height(node.right) == 2) {
                if (key.compareTo(node.left.getKey()) < 0) {
                    node = rotateWithLeftChild(node);
                } else {
                    node = doubleWithLeftChild(node);
                }
            }
        } else if (key.compareTo(node.getKey()) > 0) {
            //	System.out.println("I am a right child!");
            if (node.right != null) {
                node.right = insert(key, value, node.right);
            } else {
                node.right = new AVLNode<K, V>(key, value);
                node.right.parent = node;
                count++;
            }
            if (height(node.right) - height(node.left) == 2) {
                if (key.compareTo(node.right.getKey()) > 0) {
                    node = rotateWithRightChild(node);
                } else {
                    node = doubleWithRightChild(node);
                }
            }
        } else
			;  // Duplicate; do nothing
        node.height = max(height(node.left), height(node.right)) + 1;
        return node;
    }
    
    //@Override
    @SuppressWarnings("unchecked")
    public void remove(K k) {
        AVLNode<K, V> P = remove1(k);
        System.out.println(P.getKey());
        do {
            if (isLeftChild) {
                System.out.println("left remove ");
                if (height(P.left) - height(P.right) == 2) {
                    if (k.compareTo(P.left.getKey()) < 0) {
                        P = rotateWithLeftChild(P);
                    } else {
                        P = doubleWithLeftChild(P);
                    }
                }
            } else {
                System.out.println("right remove ");
                if (height(P.right) - height(P.left) == 2) {
                    if (k.compareTo(P.right.getKey()) > 0) {
                        P = rotateWithRightChild(P);
                    } else {
                        P = doubleWithRightChild(P);
                    }
                }

            }
            if (P != root) {
                P = P.parent;
            }
        } while (P != root);

    }

    public AVLNode<K, V> remove1(K keyString) {
        AVLNode<K, V> current = root;
        AVLNode<K, V> parent = root;
        //boolean isLeftChild = true;
        while (current.getKey().compareTo(keyString) != 0) {
            parent = current;
            if (keyString.compareTo(current.getKey()) < 0) {
                isLeftChild = true;
                current = current.getLeft();
            } else {
                isLeftChild = false;
                current = current.getRight();
            }
            if (current == null) {
                System.out.println("found");
            }
        }


        if (current.getLeft() == null && current.getRight() == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
            count--;
            parent.height = max(height(parent.left), height(parent.right)) + 1;
            return parent;
        } else if (current.getRight() == null) {
            if (current == root) {
                root = current.getLeft();
            } else if (isLeftChild) {
                parent.setLeft(current.getLeft());
            } else {
                parent.setRight(current.getLeft());
            }
            current.getLeft().parent = parent;
            count--;
            current.height = max(height(current.left), height(current.right)) + 1;
            return current;
        } else if (current.getLeft() == null) {
            if (current == root) {
                root = current.getRight();
            } else if (isLeftChild) {
                parent.setLeft(current.getRight());
            } else {
                parent.setRight(current.getRight());
            }
            current.getRight().parent = parent;
            count--;
            current.height = max(height(current.left), height(current.right)) + 1;
            return current;
        } else {
            AVLNode<K, V> succes = successor(current);
            current.key = succes.key;
            current.value = succes.value;
            if (isLeftChild) {
                parent.setLeft(succes);
            } else {
                parent.setRight(succes);
            }
            succes.setLeft(current.getLeft());
            if (current.getRight() != succes) {
                succes.setRight(current.getRight());
            }
            count--;
            if (succes.parent == current) {
                succes.parent = current.parent;
            }
            succes.parent.height = max(height(succes.parent.left), height(succes.parent.right)) + 1;
            return succes.parent;
        }

    }
    
    @Override
    public K[] getKeys() {
        K arr[] = (K[]) new String[this.count];
        this.i = 0;
        return inOrder(this.root, arr);
    }
    
    @Override
    public V getValue(K str) {
        AVLNode<K, V> current = root;
        try {
            while (current != null) {
                if (str.compareTo((K) current.getKey()) <= 0) {
                    if (current.getKey().compareTo(str) == 0) {
                        //			if(current.parent!=null)
                        //				System.out.println("My Parent = "+current.parent.getKey());
                        //			else
                        //				System.out.println("I am orphan");
                        return (V) current.getValue();
                    }
                    current = current.getLeft();
                } else {
                    if (current.getKey().compareTo(str) == 0) {
                        //			if(current.parent!=null)
                        //				System.out.println("My Parent = "+current.parent.getKey());
                        return (V) current.getValue();
                    }
                    current = current.getRight();
                }

            }
        } catch (Exception e) {
        }
        return null;
    }

    public K[] inOrder(AVLNode<K, V> root2, K[] arr) {
        try {
            if (root2.getLeft() != null) {
                this.inOrder(root2.getLeft(), arr);
            }
            //System.out.println("Traversing = "+root.getValue());
            if (root2.getKey() != null) {
                arr[i++] = (K) root2.getKey();
            }
            //System.out.println(arr1[i].
            if (root2.getRight() != null) {
                this.inOrder(root2.getRight(), arr);
            }
        } catch (Exception e) {
        }
        return arr;
    }

    private static int height(AVLNode<?, ?> node) {
        return node == null ? -1 : node.height;
    }

    private static int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    public AVLNode<K, V> successor(AVLNode<K, V> current) {
        if (current.getRight() != null) {
            AVLNode<K, V> cur = current.getRight();
            while (cur.getLeft() != null) {
                cur = cur.getLeft();
            }
            //cur.parent.setLeft(null);
            return cur;
        } else {
            AVLNode<K, V> cur = current;
            while (cur.getParent() != null
                    && cur.getParent().getRight().getKey() == cur.getKey()) {
                cur = cur.getParent();
            }
            return cur.getParent();
        }
    }

    private static AVLNode rotateWithLeftChild(AVLNode k2) {
        System.out.println("rotateWithLeftChild");
        AVLNode tempParent = k2.parent;
        AVLNode k1 = k2.left;
        k2.left = k1.right;
        if (k2.left != null) {
            k2.left.parent = k2;
        }
        //else
        //	k2.left.parent = null;
        k1.right = k2;
        k2.parent = k1;
        if (tempParent != null) {
            k1.parent = tempParent;
        } else {
            k1.parent = null;
        }
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child. For AVL trees, this is a single
     * rotation for case 4. Update heights, then return new root.
     */
    private static AVLNode rotateWithRightChild(AVLNode k1) {
        System.out.println("rotateWithRightChild");
        AVLNode tempParent = k1.parent;
        AVLNode k2 = k1.right;
        k1.right = k2.left;
        //if(k1.right!=null)
        //	k1.right.parent = k1;
        //else
        //	k1.right.parent = null;
        k2.left = k1;
        k1.parent = k2;
        if (tempParent != null) {
            k2.parent = tempParent;
        } else {
            k2.parent = null;
        }
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child with its right child;
     * then node k3 with new left child. For AVL trees, this is a double
     * rotation for case 2. Update heights, then return new root.
     */
    @SuppressWarnings({"unchecked"})
    private static AVLNode doubleWithLeftChild(AVLNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child with its left child;
     * then node k1 with new right child. For AVL trees, this is a double
     * rotation for case 3. Update heights, then return new root.
     */
    @SuppressWarnings({"unchecked"})
    private static AVLNode doubleWithRightChild(AVLNode k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
}
