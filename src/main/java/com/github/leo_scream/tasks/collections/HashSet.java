package com.github.leo_scream.tasks.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public interface HashSet<V> extends Set<V> {

    int getCapacity();

    void trimToSize();

    static <V> HashSet<V> of(final V... items) {
        final HashSet<V> set = new HashSet<V>() {
            @Override
            public int getCapacity() {
                return 0;
            }

            @Override
            public void trimToSize() {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public void add(V val) {

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
            public Iterator<V> iterator() {
                return null;
            }
        };

        Arrays.stream(items).forEach(set::add);
        return set;
    }
}
