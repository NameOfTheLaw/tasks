package com.github.leo_scream.tasks.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

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

    static <V> LinkedList<V> of (final V... items) {
        LinkedList<V> list = new LinkedList<V>() {

            @Override
            public Iterator<V> iterator() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public void remove(V val) {

            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(V val) {
                return false;
            }

            @Override
            public void removeIf(Predicate<V> p) {

            }

            @Override
            public void add(int i, V val) {

            }

            @Override
            public void set(int i, V val) {

            }

            @Override
            public V get(int i) {
                return null;
            }

            @Override
            public void remove(int i) {

            }

            @Override
            public void subList(int from, int to) {

            }

            @Override
            public V first() {
                return null;
            }

            @Override
            public V last() {
                return null;
            }
        };

        Arrays.stream(items).forEach(list::add);
        return list;
    }
}
