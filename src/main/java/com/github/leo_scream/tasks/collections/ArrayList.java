package com.github.leo_scream.tasks.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ArrayList<V> extends List<V> {

    int getCapacity();

    void trimToSize();

    void sort(final Comparator<V> comparator);

    int binarySearch(final V val);

    default void sort() {
        sort((e1, e2) -> ((Comparable<V>) e1).compareTo(e2));
    }

    default void reverseSort(final Comparator<V> comparator) {
        sort(comparator.reversed());
    }

    default void reverseSort() {
        sort((e1, e2) -> ((Comparable<V>) e2).compareTo(e1));
    }

    @Override
    ArrayList<V> subList(final int from, final int to);

    static <V> ArrayList<V> of(final V... items) {
        final ArrayList<V> list = new ArrayList<V>() {

            final double CAPACITY_MULTIPLIER = 1.5;
            final int INITIAL_CAPACITY = 8;

            Object[] data = new Object[INITIAL_CAPACITY];
            int size = 0;

            @Override
            public void forEach(Consumer<V> consumer) {
                for (int i = 0; i < size; i++) {
                    consumer.accept((V) data[i]);
                }
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public void clear() {
                data = new Object[INITIAL_CAPACITY];
                size = 0;
            }

            @Override
            public boolean contains(V val) {
                Objects.requireNonNull(val);

                for (int i = 0; i < size; i++) {
                    if (val.equals(data[i])) return true;
                }
                return false;
            }

            @Override
            public void removeIf(Predicate<V> p) {
                Objects.requireNonNull(p);

                final ArrayList<V> tempArrayList = ArrayList.of();
                forEach(v -> {
                    if (!p.test(v)) tempArrayList.add(v);
                });
                clear();
                addAll(tempArrayList);
            }

            @Override
            public void add(int i, V val) {
                Objects.requireNonNull(val);

                ensureCapacity();
                if (i != size) checkBorders(i);

                System.arraycopy(data, i, data, i+1, size-i);
                data[i] = val;
                size++;
            }

            @Override
            public void set(int i, V val) {
                Objects.requireNonNull(val);

                checkBorders(i);

                data[i] = val;
            }

            @Override
            public V get(int i) {
                checkBorders(i);

                return (V) data[i];
            }

            @Override
            public void remove(int i) {
                checkBorders(i);

                System.arraycopy(data, i+1, data, i, size-i);
                size--;
            }

            @Override
            public void remove(V val) {
                Objects.requireNonNull(val);

                for (int i = 0; i < size; i++) {
                    if (val.equals(data[i])) {
                        remove(i);
                        break;
                    }
                }
            }

            @Override
            public ArrayList<V> subList(int from, int to) {
                checkBorders(from);
                checkBorders(to);

                return ArrayList.of((V[]) Arrays.copyOfRange(data, from, to));
            }

            @Override
            public int getCapacity() {
                return data.length;
            }

            @Override
            public void trimToSize() {
                data = Arrays.copyOf(data, size);
            }

            @Override
            public void sort(Comparator<V> comparator) {
                Objects.requireNonNull(comparator);

                Arrays.sort((V[]) data, 0, size, comparator);
            }

            @Override
            public int binarySearch(V val) {
                return Arrays.binarySearch(data, 0, size, val);
            }

            private void ensureCapacity() {
                if (size == getCapacity()) {
                    data = Arrays.copyOf(data, (int) (getCapacity()*CAPACITY_MULTIPLIER));
                }
            }

            private void checkBorders(int i) {
                if (i < 0 || i >= size) {
                    throw new IndexOutOfBoundsException();
                }
            }
        };

        Arrays.stream(items).forEach(list::add);
        return list;
    }

}
