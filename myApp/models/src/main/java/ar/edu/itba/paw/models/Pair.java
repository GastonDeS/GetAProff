package ar.edu.itba.paw.models;

public class Pair<K, T> {
    private final K value1;
    private final T value2;

    public Pair(K value1, T value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public K getValue1() {
        return value1;
    }

    public T getValue2() {
        return value2;
    }
}
