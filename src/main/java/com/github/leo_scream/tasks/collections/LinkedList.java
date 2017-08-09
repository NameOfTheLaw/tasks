package com.github.leo_scream.tasks.collections;

public interface LinkedList<V> extends List<V> {

    V first();

    V last();

    default void removeFirst() {
        throw new UnsupportedOperationException();
    }

    default void removeLast() {
        throw new UnsupportedOperationException();
    }

    default void push(final V val) {
        throw new UnsupportedOperationException();
    }

    default V pop() {
        throw new UnsupportedOperationException();
    }

    default LinkedList<V> reverse() {
        throw new UnsupportedOperationException();
    }
}
