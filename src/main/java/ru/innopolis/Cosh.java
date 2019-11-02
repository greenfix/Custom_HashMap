package ru.innopolis;

public class Cosh {

    private static final float LOAD_FACTOR = 0.75f;
    private static final float FACTOR = 2.0f;

    private int capacity = 16;
    private int size = 0;
    private Uzel[] kase = new Uzel[capacity];

    /**
     *
     */
    public Cosh() {

    }

    /**
     * @param K key
     * @param V value
     */
    public Cosh(Object K, Object V) {
        this.put(K, V);
    }

    /**
     * @param K key
     * @return index of basket
     */
    public int getIndexBasket(Object K) {

        return (K == null) ? 0 : ((Math.abs(K.hashCode()) % capacity));
    }

    /**
     *
     */
    private void expansionKase() {
        capacity = (int) (capacity * FACTOR);
        Uzel[] expansion = new Uzel[capacity];
        for (int i = 0; i < kase.length; i++) {
            expansion[i] = kase[i];
        }
        kase = expansion;
    }

    /**
     * @param K key
     * @param V value
     * @return value
     * if the key exists then the value is overwritten
     */
    public Object put(Object K, Object V) {
        if ((1f * size / capacity) >= LOAD_FACTOR) {
            expansionKase();
        }
        int hash = getIndexBasket(K);
        Uzel uzel = kase[hash];
        if (uzel == null) {
            // first in basket - simple add
            kase[hash] = new Uzel(hash, K, V, null);
            size++;
        } else {
            while (true) {
                if (ifNeedUzel(K, uzel)) {
                    // rewrite
                    Object oldValue = uzel.getValue();
                    uzel.setValue(V);
                    return oldValue;
                }
                if (uzel.getNext() == null) {
                    // last in basket - add to end
                    uzel.setNext(new Uzel(hash, K, V, null));
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
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(Object K) {
        int hash = getIndexBasket(K);
        Uzel uzel = kase[hash];
        if (uzel == null) {

            return false;
        }
        while (true) {
            if (ifNeedUzel(K, uzel)) {
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
            if (ifNeedUzel(K, uzel)) {
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
        int hash = getIndexBasket(K);
        Uzel uzel = kase[hash];
        if (uzel == null) {

            return null;
        }
        int level = 0;
        Uzel prevUzel = uzel;
        while (true) {
            if (ifNeedUzel(K, uzel)) {
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
            kase[hash] = uzel.getNext();
        } else {
            prevUzel.setNext(uzel.getNext());
        }

        size--;

        return oldValue;
    }

    /**
     * @param K key
     * @param uzel uzel
     * @return true if needle
     */
    public boolean ifNeedUzel(Object K, Uzel uzel) {
        Object keyUzel = uzel.getKey();
        return ((keyUzel == null && K == null) ||
                (K != null && K.equals(keyUzel)) ||
                (keyUzel != null && keyUzel.equals(K)));
    }

    /**
     * @return
     */
    public int size() {

        return size;
    }

}
