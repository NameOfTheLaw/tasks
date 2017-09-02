package com.github.leo_scream.tasks.collections;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection<V> {

    void forEach(Consumer<V> consumer);

    int size();

    void add(final V val);

    void remove(final V val);

    void clear();

    boolean contains(final V val);

    void removeIf(final Predicate<V> p);

    default void addAll(final Collection<V> c) {
        c.forEach(this::add);
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean containsAll(final Collection<V> c) {
        boolean[] contains = {true};
        c.forEach(v -> contains[0] = this.contains(v));
        return contains[0];
    }

    default void removeAll(final Collection<V> c) {
        c.forEach(this::remove);
    }

    default void retainIf(final Predicate<V> p) {
        removeIf(p.negate());
    }
}
