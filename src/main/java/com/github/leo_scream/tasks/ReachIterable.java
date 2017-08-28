package com.github.leo_scream.tasks;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Denis Verkhoturov, mod.satyr@gmail.com
 */
@FunctionalInterface
public interface ReachIterable<T> {
    boolean forNext(Consumer<T> consumer);

    default ReachIterable<T> filter(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);

        return consumer -> {
            final boolean[] box = new boolean[1];
            forNext(element -> {
                if (predicate.test(element)) {
                    consumer.accept(element);
                    box[0] = true;
                }
            });
            return box[0];
        };
    }

    default <R> ReachIterable<R> map(final Function<T, R> mapper) {
        Objects.requireNonNull(mapper);

        return consumer ->
                forNext(element -> consumer.accept(mapper.apply(element)));
    }

    default <R> ReachIterable<R> flatMap(final Function<T, List<R>> mapper) {
        Objects.requireNonNull(mapper);

        final ReachIterable<R>[] iterable = new ReachIterable[1];
        iterable[0] = ReachIterable.from(Collections.emptyList());
        final Consumer<T> updateIterable = element -> iterable[0] = ReachIterable.from(mapper.apply(element));

        return consumer -> {
            boolean isNextLocal = iterable[0].forNext(consumer);
            boolean isNextGlobal = true;

            while (!isNextLocal && isNextGlobal) {
                isNextGlobal = forNext(updateIterable);
                isNextLocal = iterable[0].forNext(consumer);
            }

            return isNextLocal;
        };
    }

    default boolean anyMatch(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);

        final boolean[] box = new boolean[2];
        box[1] = true;

        while (!box[0] && box[1]) {
            box[1] = forNext(element -> {
                if (predicate.test(element)) {
                    box[0] = true;
                }
            });
        }

        return box[0];
    }

    default boolean allMatch(final Predicate<T> predicate) {
        return !anyMatch(predicate.negate());
    }

    default boolean noneMatch(final Predicate<T> predicate) {
        return allMatch(predicate.negate());
    }

    default Optional<T> firstMatch(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);

        final Object[] value = new Object[1];
        final boolean[] box = new boolean[2];
        box[1] = true;

        while (!box[0] && box[1]) {
            box[1] = forNext(element -> {
                if (predicate.test(element)) {
                    value[0] = element;
                    box[0] = true;
                }
            });
        }

        return Optional.ofNullable((T) value[0]);
    }

    static <T> ReachIterable<T> from(final List<T> list) {
        final Iterator<T> iterator = list.iterator();
        return consumer -> {
            if (iterator.hasNext()) {
                consumer.accept(iterator.next());
                return true;
            }
            return false;
        };
    }
}
