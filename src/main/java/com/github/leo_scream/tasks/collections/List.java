package com.github.leo_scream.tasks.collections;

public interface List<V> extends Collection<V> {

    void add(final int i, final V val);

    void set(final int i, final V val);

    V get(final int i);

    void remove(final int i);

    List<V> subList(final int from, final int to);

    @Override
    default void add(final V val) {
        add(size(), val);
    }
}
