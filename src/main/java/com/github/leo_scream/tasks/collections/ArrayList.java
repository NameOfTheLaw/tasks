package com.github.leo_scream.tasks.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public interface ArrayList<V> extends List<V> {

    int getCapacity();

    void trimToSize();

    void sort(final Comparator<V> comparator);

    int binarySearch(final V val);

    default void sort() {
        throw new UnsupportedOperationException();
    }

    default void reverseSort(final Comparator<V> comparator) {
        throw new UnsupportedOperationException();
    }

    default void reverseSort() {
        throw new UnsupportedOperationException();
    }

    @Override
    ArrayList<V> subList(final int from, final int to);

    static <V> ArrayList<V> of(final V... items) {
        ArrayList<V> list = new ArrayList<V>() {

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
            public ArrayList<V> subList(int from, int to) {
                return null;
            }

            @Override
            public int getCapacity() {
                return 0;
            }

            @Override
            public void trimToSize() {

            }

            @Override
            public void sort(Comparator<V> comparator) {

            }

            @Override
            public int binarySearch(V val) {
                return 0;
            }
        };

        Arrays.stream(items).forEach(list::add);
        return list;
    }

}
