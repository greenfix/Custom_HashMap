package ru.innopolis;

public class Uzel implements Cloneable {

    private Object key;
    private Object value;
    private Uzel next;

    public Uzel() {
    }

    public Uzel(Object key, Object value, Uzel next) {
        this.key = key;
        this.value = value;
        this.next = next;
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

    public void setValue(Object value) {
        this.value = value;
    }

    public void setNext(Uzel next) {
        this.next = next;
    }

}
