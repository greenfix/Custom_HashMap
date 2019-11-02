package ru.innopolis;

import java.util.Map;
import java.util.Objects;

public class Uzel {

    private int hash;
    private Object key;
    private Object value;
    private Uzel next;

    public Uzel() {
    }

    public Uzel(int hash, Object key, Object value, Uzel next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public int getHash() {
        return hash;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Uzel getNext() {
        return next;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setNext(Uzel next) {
        this.next = next;
    }

}
