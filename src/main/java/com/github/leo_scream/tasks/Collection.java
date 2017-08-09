package com.github.leo_scream.tasks;

import java.util.function.Predicate;

public interface Collection<V> extends Iterable<V> {

    int size();

    void add(V val);

    void remove(V val);

    void clear();

    boolean contains(V val);

    default boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    default void addAll(Collection<V> c) {
        throw new UnsupportedOperationException();
    }

    default void removeAll(Collection<V> c) {
        throw new UnsupportedOperationException();
    }

    default void removeIf(Predicate<V> p) {
        throw new UnsupportedOperationException();
    }

    default void retainIf(Predicate<V> p) {
        throw new UnsupportedOperationException();
    }

    default boolean containsAll(Collection<V> c) {
        throw new UnsupportedOperationException();
    }
}
