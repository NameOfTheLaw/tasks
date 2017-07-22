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
        Objects.requireNonNull(predicate);
        final ReachIterable<T> self = this;
        return new ReachIterable<T>() {
            boolean matched = false;
            boolean hasNext = true;

            @Override
            public boolean forNext(Consumer<T> consumer) {
                while (!matched && hasNext) {
                    hasNext = self.forNext(
                            element -> {
                                matched = predicate.test(element);
                                if (matched) consumer.accept(element);
                            }
                    );
                }
                matched = false;
                return hasNext;
            }
        };
    }

    default <R> ReachIterable<R> map(final Function<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return consumer -> this.forNext(
                element -> consumer.accept(mapper.apply(element))
        );
    }

    default <R> ReachIterable<R> flatMap(final Function<T, List<R>> mapper) {
        Objects.requireNonNull(mapper);
        final ReachIterable<T> outer = this;
        return new ReachIterable<R>() {
            ReachIterable<R> inner = ReachIterable.from(Collections.emptyList());
            boolean outerHasNext = false;
            boolean innerHasNext = false;

            @Override
            public boolean forNext(Consumer<R> consumer) {
                if (!innerHasNext) {
                    outerHasNext = outer.forNext(element -> inner = ReachIterable.from(mapper.apply(element)));
                }
                innerHasNext = inner.forNext(consumer);
                return  innerHasNext || outerHasNext;
            }
        };
    }

    default boolean anyMatch(final Predicate<T> predicate) {
        return firstMatch(predicate).isPresent();
    }

    default boolean allMatch(final Predicate<T> predicate) {
        return !anyMatch(predicate.negate());
    }

    default boolean noneMatch(final Predicate<T> predicate) {
        return !anyMatch(predicate);
    }

    default Optional<T> firstMatch(final Predicate<T> predicate) {
        class Holder { private T value; }
        final Holder needle = new Holder();
        final Consumer<T> testAndExtract = element -> { if (predicate.test(element)) needle.value = element; };
        boolean hasNext = true;
        while (Objects.isNull(needle.value) && hasNext) {
            hasNext = this.forNext(testAndExtract);
        }
        return Optional.ofNullable(needle.value);
    }

    static <T> ReachIterable<T> from(final List<T> list) {
        final Iterator<T> iterator = list.iterator();
        return consumer -> {
            if (iterator.hasNext()) consumer.accept(iterator.next());
            return iterator.hasNext();
        };
    }
}
