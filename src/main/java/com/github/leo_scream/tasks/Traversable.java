package com.github.leo_scream.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Specification: src/test/kotlin/com/github/leo_scream/tasks/TraversableSpec.kt
 */
public interface Traversable<T> {
    void forEach(final Consumer<T> consumer);

    default Traversable<T> filter(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return consumer -> this.forEach(
                element -> { if (predicate.test(element)) consumer.accept(element); }
        );
    }

    default <R> Traversable<R> map(final Function<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return consumer -> this.forEach(
                element -> consumer.accept(mapper.apply(element))
        );
    }

    default <R> Traversable<R> flatMap(final Function<T, List<R>> mapper) {
        Objects.requireNonNull(mapper);
        return consumer -> this.forEach(
                element -> mapper.apply(element).forEach(consumer)
        );
    }

    default List<T> toList() {
        final List<T> list = new ArrayList<>();
        forEach(list::add);
        return list;
    }

    static <T> Traversable<T> from(final List<T> list) {
        return list::forEach;
    }
}
