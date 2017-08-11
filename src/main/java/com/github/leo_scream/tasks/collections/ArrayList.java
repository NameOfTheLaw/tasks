package com.github.leo_scream.tasks.collections;

import java.util.Comparator;

public interface ArrayList<V> extends List<V> {

    int getCapacity();

    ArrayList<V> trimToSize();

    ArrayList<V> sort(final Comparator<V> comparator);

    int binarySearch(final V val);

    default ArrayList<V> sort() {
        throw new UnsupportedOperationException();
    }

    default ArrayList<V> reverseSort(final Comparator<V> comparator) {
        throw new UnsupportedOperationException();
    }

    default ArrayList<V> reverseSort() {
        throw new UnsupportedOperationException();
    }

}
