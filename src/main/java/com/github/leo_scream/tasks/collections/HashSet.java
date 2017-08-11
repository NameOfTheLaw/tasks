package com.github.leo_scream.tasks.collections;

public interface HashSet<V> extends Set<V> {

    int getCapacity();

    void trimToSize();
}
