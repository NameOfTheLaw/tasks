package com.github.leo_scream.tasks.collections;

public interface LinkedList<V> extends List<V> {

    V first();

    V last();

    default LinkedList<V> removeFirst() {
        throw new UnsupportedOperationException();
    }

    default LinkedList<V> removeLast() {
        throw new UnsupportedOperationException();
    }

    default LinkedList<V> push(final V val) {
        throw new UnsupportedOperationException();
    }

    default V pop() {
        throw new UnsupportedOperationException();
    }

    default LinkedList<V> reverse() {
        throw new UnsupportedOperationException();
    }
}
