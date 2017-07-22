package com.github.leo_scream.tasks;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Denis Verkhoturov, mod.satyr@gmail.com
 */
public interface ReachIterable<T> {
    boolean forNext(Consumer<T> consumer);

    default ReachIterable<T> filter(final Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    default <R> ReachIterable<R> map(final Function<T, R> mapper) {
        throw new UnsupportedOperationException();
    }

    default <R> ReachIterable<R> flatMap(final Function<T, List<R>> mapper) {
        throw new UnsupportedOperationException();
    }

    default boolean anyMatch(final Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    default boolean allMatch(final Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    default boolean noneMatch(final Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    default Optional<T> firstMatch(final Predicate<T> predicate) {
        throw new UnsupportedOperationException();
    }

    static <T> ReachIterable<T> from(final List<T> list) {
        throw new UnsupportedOperationException();
    }
}
