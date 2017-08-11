package com.github.leo_scream.tasks.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

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
            public void subList(int from, int to) {

            }

            @Override
            public int getCapacity() {
                return 0;
            }

            @Override
            public ArrayList<V> trimToSize() {
                return null;
            }

            @Override
            public ArrayList<V> sort(Comparator<V> comparator) {
                return null;
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
