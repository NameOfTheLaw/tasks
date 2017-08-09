package com.github.leo_scream.tasks;

public interface List<V> extends Collection<V> {

    void add(int i, V val);

    void set(int i, V val);

    V get(int i);

    void remove(int i);

    List<V> subList(int from, int to);

    @Override
    default void add(V val) {
        throw new UnsupportedOperationException();
    }
}
