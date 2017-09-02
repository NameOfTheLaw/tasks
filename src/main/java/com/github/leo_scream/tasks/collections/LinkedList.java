package com.github.leo_scream.tasks.collections;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface LinkedList<V> extends List<V> {

    V first();

    V last();

    default void removeFirst() {
        remove(0);
    }

    default void removeLast() {
        remove(size()-1);
    }

    default void push(final V val) {
        add(val);
    }

    default V pop() {
        V last = last();
        removeLast();
        return last;
    }

    default LinkedList<V> reverse() {
        final LinkedList<V> reversedLinkedList = LinkedList.of();

        forEach(v -> reversedLinkedList.add(0, v));

        return reversedLinkedList;
    }

    @Override
    LinkedList<V> subList(final int from, final int to);

    static <V> LinkedList<V> of(final V... items) {
        final LinkedList<V> list = new LinkedList<V>() {

            Node root = new Node();
            Node last = root;
            int size = 0;

            @Override
            public void forEach(Consumer<V> consumer) {
                Node current = root;
                while (current.hasNext()) {
                    current = current.next;
                    consumer.accept(current.value);
                }
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public void clear() {
                root = new Node();
                last = root;
                size = 0;
            }

            @Override
            public boolean contains(V val) {
                Objects.requireNonNull(val);

                return find(val) != null;
            }

            @Override
            public void removeIf(Predicate<V> p) {
                Objects.requireNonNull(p);

                final LinkedList<V> tempLinkedList = LinkedList.of();

                forEach(v -> { if (p.test(v)) tempLinkedList.add(v); });

                removeAll(tempLinkedList);
            }

            @Override
            public void add(int i, V val) {
                Objects.requireNonNull(val);

                if (i != size) checkBorders(i);

                Node node = getNodeBefore(i);
                Node newNode = new Node(val);

                if (node.next != null) {
                    newNode.next = node.next;
                } else {
                    last = newNode;
                }
                node.next = newNode;
                size++;
            }

            @Override
            public void set(int i, V val) {
                Objects.requireNonNull(val);

                checkBorders(i);

                getNode(i).value = val;
            }

            @Override
            public V get(int i) {
                checkBorders(i);

                return getNode(i).value;
            }

            @Override
            public void remove(int i) {
                checkBorders(i);

                removeNext(getNodeBefore(i));
            }

            @Override
            public void remove(V val) {
                Objects.requireNonNull(val);

                Node prev = root;
                Node curr = root.next;
                while (prev.hasNext()) {
                    if (curr.value.equals(val)) {
                        removeNext(prev);
                        return;
                    }
                    prev = curr;
                    curr = curr.next;
                }
            }

            private Node find(V val) {
                Node current = root;
                while (current.hasNext()) {
                    current = current.next;
                    if (current.value.equals(val)) return current;
                }
                return null;
            }

            private void removeNext(Node prev) {
                Node removed = prev.next;
                if (removed.next != null) {
                    prev.next = removed.next;
                } else {
                    prev.next = null;
                    last = prev;
                }
                size--;
            }

            @Override
            public LinkedList<V> subList(int from, int to) {
                checkBorders(from);
                checkBorders(to);

                LinkedList<V> subList = LinkedList.of();
                Node current = root;
                for (int i = 0; i < to; i++) {
                    current = current.next;
                    if (i >= from) subList.add(current.value);
                }
                return subList;
            }

            @Override
            public V first() {
                checkNonEmptyness();

                return root.next.value;
            }

            @Override
            public V last() {
                checkNonEmptyness();

                return last.value;
            }

            private Node getNodeBefore(int i) {
                return getNode(i-1);
            }

            private Node getNode(int i) {
                Node current = root;
                for (int j = 0; j < i+1; j++) {
                    current = current.next;
                }
                return current;
            }

            private void checkNonEmptyness() {
                if (isEmpty()) throw new IndexOutOfBoundsException();
            }

            private void checkBorders(int i) {
                if (i < 0 || i >= size) {
                    throw new IndexOutOfBoundsException();
                }
            }

            class Node {

                Node next;
                V value;

                public Node() {}

                public Node(V value) {
                    this.value = value;
                }

                boolean hasNext() {
                    return next != null;
                }
            }
        };

        Arrays.stream(items).forEach(list::add);
        return list;
    }
}
