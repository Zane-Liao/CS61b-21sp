package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode root;
    private int size;

    private class BSTNode {
        private K key;
        private V value;
        private int size;
        private BSTNode left;
        private BSTNode right;
        BSTNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }

        BSTNode get(K k) {
            if (k.equals(this.key)) {
                return this;
            }
            int cmp = k.compareTo(this.key);
            if (cmp < 0 && left != null) {
                return left.get(k);
            } else if (cmp > 0 && right != null) {
                return right.get(k);
            }
            return null;
        }
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode x, K key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.value;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(BSTNode x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    /** */
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    @Override
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException();
        root = put(root, key, val);
    }


    private BSTNode put(BSTNode x, K key, V value) {
            if (x == null) {
                return new BSTNode(key, value, 1);
            }
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = put(x.left, key, value);
            else if (cmp > 0) x.right = put(x.right, key, value);
            else x.value = value;
            x.size = 1 + size(x.left) + size(x.right);
            return x;
    }

    public void printInOrder() {
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
