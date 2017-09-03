package com.github.leo_scream.tasks.collections;

public interface Set<V> extends Collection<V> {

    default Set<V> union(final Set<V> other) {
        final Set<V> unionSet = HashSet.of();
        unionSet.addAll(this);
        unionSet.addAll(other);

        return unionSet;
    }

    default Set<V> intersection(final Set<V> other) {
        final Set<V> intersectionSet = HashSet.of();
        intersectionSet.addAll(this);
        intersectionSet.retainIf(other::contains);

        return intersectionSet;
    }

    default Set<V> difference(final Set<V> other) {
        final Set<V> part1 = HashSet.of();
        part1.addAll(this);
        part1.removeIf(other::contains);

        final Set<V> part2 = HashSet.of();
        part2.addAll(other);
        part2.removeIf(this::contains);

        return part1.union(part2);
    }
}
