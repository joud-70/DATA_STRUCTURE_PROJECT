package ridesharingsystemproject;

public class BST<K extends Comparable<K>, T> implements IBST<K, T> {
    private BSTNode<K, T> root, current;
    private int count;

    public BST() {
        root = current = null;
        count = 0;
    }

    public boolean empty() { return root == null; }

    public int size() { return count; }

    // Same idea as the class findkey: p walks down, q remembers the last node visited.
    public boolean findKey(K key) {
        if (empty()) return false;
        BSTNode<K, T> p = root, q = root;
        while (p != null) {
            q = p;
            int res = key.compareTo(p.key);
            if (res == 0) { current = p; return true; }
            else if (res < 0) p = p.left;
            else p = p.right;
        }
        current = q;
        return false;
    }

    public T retrieve() { return current.data; }

    
    
    public boolean insert(K key, T data) {
    if (root == null) {
        current = root = new BSTNode<K, T>(key, data);
        count++;
        return true;
    }

    BSTNode<K, T> p = root;
    BSTNode<K, T> q = null;
    while (p != null) {
        int res = key.compareTo(p.key);
        if (res == 0) {
            return false; // key already exists, current does not change
        } else {
            q = p;
            if (res < 0)
                p = p.left;
            else
                p = p.right;
        }
    }

    // p is null here, q is the parent of the new node
    p = new BSTNode<K, T>(key, data);
    if (key.compareTo(q.key) < 0)
        q.left = p;
    else
        q.right = p;
    current = p;
    count++;
    return true;
}

    
    
   public boolean removeKey(K key) {
    BSTNode<K, T> p = root;
    BSTNode<K, T> q = null; // parent of p
    boolean found = false;

    // search for the key
    while (p != null && !found) {
        int res = key.compareTo(p.key);
        if (res < 0) {
            q = p;
            p = p.left;
        } else if (res > 0) {
            q = p;
            p = p.right;
        } else
            found = true;
    }

    if (found) {
        // case 3: two children
        if (p.left != null && p.right != null) {
            // search for the min in the right subtree
            BSTNode<K, T> min = p.right;
            q = p;
            while (min.left != null) {
                q = min;
                min = min.left;
            }
            p.key = min.key;
            p.data = min.data;
            deleteNode(min, q);
        } else // case 1 and 2
            deleteNode(p, q);

        count--;
        current = root;
        return true;
    }
    return false; // not found
}

private void deleteNode(BSTNode<K, T> n, BSTNode<K, T> parent) {
    // parent is the parent of n. Deletes node n (case 1 and 2)
    BSTNode<K, T> child;
    if (n.left != null)
        child = n.left;
    else
        child = n.right;

    if (parent == null) { // no parent, root must change
        root = child;
    } else {
        if (parent.left == n)
            parent.left = child;
        else
            parent.right = child;
    }
}

    

    public LinkedList<T> inOrder() {
        LinkedList<T> result = new LinkedList<T>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(BSTNode<K, T> p, LinkedList<T> result) {
        if (p == null) return;
        inOrder(p.left, result);
        result.insert(p.data);
        inOrder(p.right, result);
    }

    public LinkedList<T> rangeSearch(K low, K high) {
        LinkedList<T> result = new LinkedList<T>();
        rangeSearch(root, low, high, result);
        return result;
    }

    private void rangeSearch(BSTNode<K, T> t, K low, K high, LinkedList<T> result) {
    if (t == null) return;

    if (t.key.compareTo(low) < 0)          // key < low: everything on the left is too small
        rangeSearch(t.right, low, high, result);
    else if (t.key.compareTo(high) > 0)    // key > high: everything on the right is too big
        rangeSearch(t.left, low, high, result);
    else {                                 // low <= key <= high
        rangeSearch(t.left, low, high, result);
        result.insert(t.data);
        rangeSearch(t.right, low, high, result);
    }
}

    public int height() { return height(root); }

    
    
    /*
    private int height(BSTNode<K, T> p) {
    if (p == null) return 0;
    int l = height(p.left);
    int r = height(p.right);
    if (l > r)
        return 1 + l;
    else
        return 1 + r;
}
    */
    
    
    
    
    private int height(BSTNode<K, T> p) {
        if (p == null) return 0;
        return 1 + Math.max(height(p.left), height(p.right));
    }

    public int nbKeyComp(K key) {
        int n = 0;
        BSTNode<K, T> p = root;
        while (p != null) {
            n++;
            int res = key.compareTo(p.key);
            if (res == 0) return n;
            p = (res < 0) ? p.left : p.right;
        }
        return n;
    }
}
