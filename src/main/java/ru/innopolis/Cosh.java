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
    private int getIndexBasket(Object K) {

        return (K == null) ? 0 : (Math.abs(K.hashCode() % capacity) + 1);
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
     * @return  value
     * if the key exists then the value is overwritten
     */
    public Object put(Object K, Object V) {

        if ((1f * size / capacity) >= LOAD_FACTOR) {
            expansionKase();
        }

        int hash = getIndexBasket(K);
        if (!(kase[hash] == null)) {
            Uzel oldUzel = kase[hash];
            Object oldKey = oldUzel.getKey();
            if (oldKey == null || oldKey.equals(K)) {
                // rewrite
                Object oldValue = oldUzel.getValue();
                oldUzel.setValue(V);
                return oldValue;
            } else {
                // collision
                while (!(oldUzel.getNext() == null)) {
                    oldUzel = oldUzel.getNext();
                }
                Uzel uzel = new Uzel(hash, K, V, null);
                oldUzel.setNext(uzel);
                size++;
                return null;
            }
        } else {
            // add
            Uzel uzel = new Uzel(hash, K, V, null);
            kase[hash] = uzel;
            size++;
            return null;
        }
    }

    /**
     * @param K key
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(Object K) {

        if (K == null) {
            return !(kase[0] == null);
        }

        int hash = getIndexBasket(K);
        Uzel uzel = kase[hash];
        if (kase[hash] == null) {

            return false;
        }

        while (!K.equals(uzel.getKey())) {
            uzel = uzel.getNext();
            if (uzel == null || uzel.getNext() == null) {
                break;
            }
        }

        return K.equals(uzel.getKey());
    }

    /**
     * @param K key
     * @return  value
     */
    public Object get(Object K) {

        int hash = getIndexBasket(K);
        Uzel uzel = kase[hash];
        if (kase[hash] == null) {

            return null;
        }
        while (K != null && !K.equals(uzel.getKey())) {
            uzel = uzel.getNext();
            if (uzel == null || uzel.getNext() == null) {
                break;
            }
        }

        if (uzel == null) {
            return null;
        } else {
            if (K == null || K.equals(uzel.getKey())) {
                return uzel.getValue();
            } else {
                return null;
            }
        }
    }

    /**
     * @param K key
     * @return  value
     */
    public Object remove(Object K) {

        int hash = getIndexBasket(K);
        Uzel uzel = kase[hash];
        if (kase[hash] == null) {

            return null;
        }

        int level = 0;
        Uzel prevUzel = uzel;
        Object oldValue = null;
        if (K != null) {
            while (!K.equals(uzel.getKey())) {
                level++;
                prevUzel = uzel;
                uzel = uzel.getNext();
                if (uzel.getNext() == null) {
                    break;
                }
            }
            oldValue = uzel.getValue();
            if (!K.equals(uzel.getKey())) {
                return null;
            }
        }

        if (level == 0) {
            if (uzel.getNext() == null) {
                kase[hash] = null;
                uzel = null;
            } else {
                kase[hash] = uzel.getNext();
                uzel = null;
            }
        } else {
            if (uzel.getNext() == null) {
                prevUzel.setNext(null);
                uzel = null;
            } else {
                prevUzel.setNext(uzel.getNext());
                uzel = uzel.getNext();
            }
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

}
