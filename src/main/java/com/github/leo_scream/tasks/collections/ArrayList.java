package com.github.leo_scream.tasks.collections;

import java.util.Comparator;

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
}
