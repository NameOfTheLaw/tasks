package com.github.leo_scream.tasks.collections;

public interface Set<V> extends Collection<Set<V>, V> {

    default Set<V> union(final Set<V> other) {
        throw new UnsupportedOperationException();
    }

    default Set<V> intersection(final Set<V> other) {
        throw new UnsupportedOperationException();
    }

    default Set<V> difference(final Set<V> other) {
        throw new UnsupportedOperationException();
    }
}
