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
    private static final int DEFAULT_INITIAL_SIZE = 16;
    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double maxLoadFactor;

    // You should probably define some more!
    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_SIZE, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_MAX_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        if (initialSize <= 0 || maxLoad <= 0) {
            throw new IllegalArgumentException();
        }
        this.maxLoadFactor = maxLoad;
        this.size = 0;
        this.buckets = createTable(initialSize);
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


    /** Removes all of the mappings from this map */
    @Override
    public void clear() {
        buckets = createTable(16);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if and only if this dictionary contains KEY as the
     * key of some key-value pair.
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException();
        return getNode(key) != null;
    }

    /** Returns the value corresponding to KEY or null if no such value exists. */
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException();
        Node node = getNode(key);
        return node == null ? null : node.value; // Checks if node is null.
        // If node is null, returns null, otherwise returns node.value
    }

    /** Inserts the key-value pair of KEY and VALUE into this dictionary,
     * replacing the previous value associated to KEY, if any.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        if ((double) size / buckets.length > maxLoadFactor) {
            resize(buckets.length*2);
        }
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            bucket = createBucket();
            buckets[index] = bucket;
        }

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.add(new Node(key, value));
        size++;
    }

    transient Set<K> keySet;

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    keys.add(node.key);
                }
            }
        }
        return keys;
    }

    private Node getNode(K key) {
        int index = getIndex(key);
        Collection<Node> bucket = buckets[index];
        if (bucket != null) {
            for(Node node : bucket) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    //***//
    private void resize(int newSize) {
        Collection<Node>[] newBuckets = createTable(newSize);
        for (Collection<Node> bucket: buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    int newIndex = (node.key.hashCode() & 0x7fffffff) % newSize;
                    if (newBuckets[newIndex] == null) {
                        newBuckets[newIndex] = createBucket();
                    }
                    newBuckets[newIndex].add(node);
                }
            }
        }
        buckets = newBuckets;
    }

    private int getIndex(K key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
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
        Node next;
        int index;

        /** */
        @Override
        public boolean hasNext() {
            while (next == null && index < buckets.length) {
                next = (Node) buckets[index++];
            }
            return next != null;
        }

        /** */
        @Override
        public K next() {
            Node e = next;
            Node current = next;
            next = current.next;
            if (e == null)
                throw new NoSuchElementException();
            if (next == null && index < buckets.length) {
                while (index < buckets.length && buckets[index++] == null) {}
            }
            return current.key;
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        public Node next;
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }
    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
