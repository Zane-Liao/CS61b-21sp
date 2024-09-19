package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
//    private Node next;
    private MyHashMap<K, V>[] st;
    int size = 0;

    /** Removes all of the mappings from this map */
    @Override
    public void clear() {
        buckets = null;
        size = 0;
    }

    /**
     * Returns true if and only if this dictionary contains KEY as the
     * key of some key-value pair.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException();
        return get(key) != null;
    }

    /** Returns the value corresponding to KEY or null if no such value exists. */
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException();
        int i = key.hashCode();
        return st[i].get(key);
    }

    @Override
    public int size() {
        return size;
    }

    /** Inserts the key-value pair of KEY and VALUE into this dictionary,
     * replacing the previous value associated to KEY, if any.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        int i = key.hashCode();
        if (!st[i].containsKey(key)) {
            st[i].put(key, value);
        }
//        key.equals();
    }

    transient Set<K> keySet;

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet;
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
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {

        /** */
        @Override
        public boolean hasNext() {
            return false;
        }

        /***/
        @Override
        public K next() {
            return null;
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    /** Constructors */
    public MyHashMap() { super(); }

    public MyHashMap(int initialSize) {
        initialSize = 16;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        initialSize = 16;
        maxLoad = 0.75;
        double loadFacter = (double) size() / buckets.length;
        if (maxLoad <= loadFacter) {
            initialSize = hashCode() % buckets.length;
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */

    /** OpenAI4o */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];

        for (int i = 0; i < tableSize; i++) {
            table[i] = new HashSet<>();
        }

        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
