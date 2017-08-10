package com.github.leo_scream.tasks.collections;

public interface List<C extends List<?, V>, V> extends Collection<List<?, V>, V> {

    C add(final int i, final V val);

    C set(final int i, final V val);

    V get(final int i);

    C remove(final int i);

    C subList(final int from, final int to);

    @Override
    default C add(final V val) {
        throw new UnsupportedOperationException();
    }
}
