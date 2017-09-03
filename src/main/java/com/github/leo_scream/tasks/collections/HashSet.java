package com.github.leo_scream.tasks.collections;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface HashSet<V> extends Set<V> {

    double getLoadFactor();

    static <V> HashSet<V> of(final V... items) {
        final HashSet<V> set = new HashSet<V>() {

            @Override
            public void forEach(Consumer<V> consumer) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public double getLoadFactor() {
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
        };

        Arrays.stream(items).forEach(set::add);
        return set;
    }
}
