package com.github.leo_scream.tasks.collections;

import java.util.function.Predicate;

public interface Collection<C extends Collection<?, V>, V> extends Iterable<V> {

    int size();

    C add(final V val);

    C remove(final V val);

    C clear();

    boolean contains(final V val);

    C removeIf(final Predicate<V> p);

    default C addAll(final Collection<?, V> c) {
        throw new UnsupportedOperationException();
    }

    default boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    default boolean containsAll(final Collection<?, V> c) {
        throw new UnsupportedOperationException();
    }

    default C removeAll(final Collection<?, V> c) {
        throw new UnsupportedOperationException();
    }

    default C retainIf(final Predicate<V> p) {
        throw new UnsupportedOperationException();
    }
}
