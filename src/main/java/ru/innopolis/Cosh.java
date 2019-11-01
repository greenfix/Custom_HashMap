package ru.innopolis;

public class Cosh {

    private static final float EXP = 0.75f;
    private static final float FACTOR = 2.0f;

    private int kaseLen = 15;
    private Uzel[] kase = new Uzel[kaseLen];
    private int size = 0;

    /**
     *
     */
    public Cosh() {

    }

    /**
     * @param K
     * @param V
     */
    public Cosh(Object K, Object V) {
        this.put(K, V);
    }

    /**
     * @param K
     * @return Hashcode
     */
    private int hash(Object K) {

        return Math.abs(K.hashCode() % kaseLen);
    }

    /**
     *
     */
    private void expansionKase() {
        kaseLen = (int) (kaseLen * FACTOR);
        Uzel[] expansion = new Uzel[kaseLen];
        for (int i = 0; i < kase.length; i++) {
            expansion[i] = kase[i];
        }
        kase = expansion;
    }

    /**
     * @param K
     * @param V
     * @return Object value
     * if the key or value is null, an exception is thrown
     * if the key exists then the value is overwritten
     * @throws IllegalArgumentException
     */
    public Object put(Object K, Object V) {

        if ((1f * size / kaseLen) >= EXP) {
            expansionKase();
        }

        if (K == null || V == null) {
            throw new IllegalArgumentException();
        }

        int hash = hash(K);
        if (!(kase[hash] == null)) {
            Uzel oldUzel = kase[hash];
            Object oldKey = oldUzel.getKey();
            if (oldKey.equals(K)) {
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
     * @param K
     * @return true if this map contains a mapping for the specified key
     * if the key is null then an exception is thrown
     * @throws IllegalArgumentException
     */
    public boolean containsKey(Object K) {

        return !(get(K) == null);
    }

    /**
     * @param K
     * @return Object value
     * if the key does not exist then null
     * if the key is null then an exception is thrown
     * @throws IllegalArgumentException
     */
    public Object get(Object K) {
        if (K == null) {
            throw new IllegalArgumentException();
        }
        int hash = hash(K);
        Uzel uzel = kase[hash];
        if (kase[hash] == null) {

            return null;
        }
        while (!K.equals(uzel.getKey())) {
            uzel = uzel.getNext();
            if (uzel.getNext() == null) {
                break;
            }
        }

        if (uzel == null) {
            return null;
        } else {
            if (K.equals(uzel.getKey())) {
                return uzel.getValue();
            } else {
                return null;
            }
        }
    }

    /**
     * @param K
     * @return true if the node is deleted, false if not
     * @throws IllegalArgumentException
     */
    public Object remove(Object K) {
        if (K == null) {
            throw new IllegalArgumentException();
        }
        int hash = hash(K);
        Uzel uzel = kase[hash];
        if (kase[hash] == null) {

            return null;
        }

        // TODO: 31.10.2019 - если узел совсем не найден
        int level = 0;
        Uzel prevUzel = uzel;
        Object oldValue;
        while (!K.equals(uzel.getKey())) {
            level++;
            prevUzel = uzel;
            uzel = uzel.getNext();
            if (uzel.getNext() == null) {
                break;
            }
        }
        oldValue = uzel.getValue();
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
