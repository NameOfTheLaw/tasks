package com.github.leo_scream.tasks;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Specification: src/test/kotlin/com/github/leo_scream/tasks/RangeSpec.kt
 */
public interface Range {

    int lowerBound();

    int upperBound();

    default boolean isBefore(final Range other) {
        return upperBound() < other.lowerBound();
    }

    default boolean isAfter(final Range other) {
        return other.upperBound() < lowerBound();
    }

    default boolean isConcurrent(final Range other) {
        return contains(other.lowerBound()) || contains(other.upperBound());
    }

    default boolean contains(final int value) {
        return lowerBound() <= value && value <= upperBound();
    }

    default List<Integer> asList() {
        return IntStream.rangeClosed(lowerBound(), upperBound())
                        .boxed()
                        .collect(toList());
    }

    default Iterator<Integer> asIterator() {
        return new Iterator<Integer>() {
            private int current = lowerBound();

            @Override
            public boolean hasNext() { return current <= upperBound(); }

            @Override
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                return current++;
            }
        };
    }

    static Range bounds(final int lower, final int upper) {
        if (lower > upper)
            throw new IllegalArgumentException("Lower bound of range must be less-than or equal to upper bound");
        return new Range() {
            @Override
            public int lowerBound() {
                return lower;
            }

            @Override
            public int upperBound() {
                return upper;
            }
        };
    }
}
