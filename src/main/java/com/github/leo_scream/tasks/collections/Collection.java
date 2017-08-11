package com.github.leo_scream.tasks.collections;

import java.util.function.Predicate;

public interface Collection<V> extends Iterable<V> {

    int size();

    void add(final V val);

    void remove(final V val);

    void clear();

    boolean contains(final V val);

    void removeIf(final Predicate<V> p);

    default void addAll(final Collection<V> c) {
        throw new UnsupportedOperationException();
    }

    default boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    default boolean containsAll(final Collection<V> c) {
        throw new UnsupportedOperationException();
    }

    default void removeAll(final Collection<V> c) {
        throw new UnsupportedOperationException();
    }

    default void retainIf(final Predicate<V> p) {
        throw new UnsupportedOperationException();
    }
}
