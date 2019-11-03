package ru.innopolis;

public class Cosh {

    private static final float FACTOR = 2.0f;

    private float loadFactor;
    private int capacity;
    private int size = 0;
    private Uzel[] kase;

    /**
     *
     */
    public Cosh() {
        capacity = 16;
        loadFactor = 0.75f;
        kase = new Uzel[capacity];
    }

    /**
     * @param capacity
     */
    public Cosh(int capacity) {
        this.capacity = capacity;
        loadFactor = 0.75f;
        kase = new Uzel[capacity];
    }

    /**
     * @param capacity   capacity
     * @param LoadFactor LoadFactor
     */
    public Cosh(int capacity, float LoadFactor) {
        this.capacity = capacity;
        this.loadFactor = LoadFactor;
        kase = new Uzel[capacity];
    }

    /**
     * @param K key
     * @param V value
     * @return value
     * if the key exists then the value is overwritten
     */
    public Object put(Object K, Object V) {
        if ((1f * size / capacity) >= loadFactor) {
            expansionKase();
        }

        return putService(K, V, kase);
    }

    /**
     * @param K key
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(Object K) {
        int basket = getIndexBasket(K);
        Uzel uzel = kase[basket];
        if (uzel == null) {

            return false;
        }
        while (true) {
            if (isNeedUzel(K, uzel)) {
                return true;
            }
            uzel = uzel.getNext();
            if (uzel == null) {
                return false;
            }
        }
    }

    /**
     * @param K key
     * @return value
     */
    public Object get(Object K) {
        Uzel uzel = kase[getIndexBasket(K)];
        if (uzel == null) {

            return null;
        }
        while (true) {
            if (isNeedUzel(K, uzel)) {
                break;
            }
            uzel = uzel.getNext();
            if (uzel == null) {
                return null;
            }
        }

        return uzel.getValue();
    }

    /**
     * @param K key
     * @return value
     */
    public Object remove(Object K) {
        int basket = getIndexBasket(K);
        Uzel uzel = kase[basket];
        if (uzel == null) {

            return null;
        }
        int level = 0;
        Uzel prevUzel = uzel;
        while (true) {
            if (isNeedUzel(K, uzel)) {
                break;
            }
            level++;
            prevUzel = uzel;
            uzel = uzel.getNext();
            if (uzel == null) {

                return null;
            }
        }
        Object oldValue = uzel.getValue();
        if (level == 0) {
            kase[basket] = uzel.getNext();
        } else {
            prevUzel.setNext(uzel.getNext());
        }
        size--;

        return oldValue;
    }

    /**
     * @return
     */
    public int size() {

        return size;
    }

    /**
     *
     * @param K key
     * @param V value
     * @param nodes nodes
     * @return Object
     */
    private Object putService(Object K, Object V, Uzel[] nodes) {
        int basket = getIndexBasket(K);
        Uzel uzel = nodes[basket];
        if (uzel == null) {
            // first in basket - simple add
            nodes[basket] = new Uzel(K, V, null);
            size++;
        } else {
            while (true) {
                if (isNeedUzel(K, uzel)) {
                    // rewrite
                    Object oldValue = uzel.getValue();
                    uzel.setValue(V);
                    return oldValue;
                }
                if (uzel.getNext() == null) {
                    // last in basket - add to end
                    uzel.setNext(new Uzel(K, V, null));
                    size++;
                    break;
                }
                uzel = uzel.getNext();
            }
        }

        return null;
    }

    /**
     * @param K key
     * @return index of basket
     */
    private int getIndexBasket(Object K) {
        if (K == null) {
            return 0;
        }
        int h = K.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        h = h ^ (h >>> 7) ^ (h >>> 4);

        return (h & (capacity - 1));
    }

    /**
     *
     */
    private void expansionKase() {
        capacity = (int) (capacity * FACTOR);
        Uzel[] expansion = new Uzel[capacity];
        Uzel uzel;
        size = 0;
        for (int i = 0; i < kase.length; i++) {
            uzel = kase[i];
            if (uzel != null) {
                do {
                    putService(uzel.getKey(), uzel.getValue(), expansion);
                    uzel = uzel.getNext();
                } while (uzel != null);
            }
        }
        kase = expansion;
    }

    /**
     * @param K key
     * @param uzel uzel
     * @return true if needle
     */
    private boolean isNeedUzel(Object K, Uzel uzel) {
        Object keyUzel = uzel.getKey();
        return ((keyUzel == null && K == null) ||
                (K != null && K.equals(keyUzel)) ||
                (keyUzel != null && keyUzel.equals(K)));
    }

}
