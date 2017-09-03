package com.github.leo_scream.tasks.collections;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface HashSet<V> extends Set<V> {

    double getLoadFactor();

    static <V> HashSet<V> of(final V... items) {
        final HashSet<V> set = new HashSet<V>() {

            final int INITIAL_CAPACITY = 8;

            final double LOAD_FACTOR = 0.75;

            final double CAPACITY_MULTIPLIER = 1.5;
            Bucket[] data = new Bucket[INITIAL_CAPACITY];
            int size = 0;

            @Override
            public void forEach(Consumer<V> consumer) {
                for (int i = 0; i < getCapacity(); i++)
                    if (data[i] != null) data[i].forEach(consumer);
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public double getLoadFactor() {
                return (double) size() / getCapacity();
            }

            @Override
            public void add(V val) {
                Objects.requireNonNull(val);

                ensureLoadFactor();

                getBucket(hash(val)).put(val);
            }

            @Override
            public void remove(V val) {
                Objects.requireNonNull(val);

                for (int i = 0; i < getCapacity(); i++)
                    if (data[i] != null && data[i].remove(val)) return;
            }

            @Override
            public void clear() {
                data = new Bucket[INITIAL_CAPACITY];
                size = 0;
            }

            @Override
            public boolean contains(V val) {
                Objects.requireNonNull(val);

                for (int i = 0; i < getCapacity(); i++)
                    if (data[i] != null && data[i].contains(val)) return true;

                return false;
            }

            @Override
            public void removeIf(Predicate<V> p) {
                Objects.requireNonNull(p);

                for (int i = 0; i < getCapacity(); i++)
                    if (data[i] != null) data[i].removeIf(p);
            }

            private int getCapacity() {
                return data.length;
            }

            private int hash(V val) {
                return hash(val, getCapacity());
            }

            private int hash(V val, int capacity) {
                return val.hashCode() % capacity;
            }

            private void ensureLoadFactor() {
                if (size() / getCapacity() > LOAD_FACTOR) {
                    final int newCapacity = (int) (getCapacity() * CAPACITY_MULTIPLIER);
                    final Bucket[] newData = new Bucket[newCapacity];

                    forEach(v -> getBucket(hash(v, newCapacity), newData).put(v));

                    data = newData;
                }
            }

            private Bucket getBucket(int index) {
                return getBucket(index, data);
            }

            private Bucket getBucket(int index, Bucket[] data) {
                if (data[index] == null) data[index] = new Bucket();
                return data[index];
            }

            class Bucket {


                List<V> list = ArrayList.of();

                public void put(V val) {
                    if (!list.contains(val)) {
                        list.add(val);
                        size++;
                    }
                }

                public void forEach(Consumer<V> consumer) {
                    list.forEach(consumer);
                }

                public boolean remove(V val) {
                    int sizeBeforeRemoving = list.size();
                    list.remove(val);
                    if (sizeBeforeRemoving != list.size()) {
                        size--;
                        return true;
                    }
                    return false;
                }

                public boolean contains(V val) {
                    return list.contains(val);
                }

                public void removeIf(Predicate<V> p) {
                    int sizeBeforeRemoving = list.size();
                    list.removeIf(p);
                    size -= sizeBeforeRemoving - list.size();
                }
            }
        };

        Arrays.stream(items).forEach(set::add);
        return set;
    }
}
