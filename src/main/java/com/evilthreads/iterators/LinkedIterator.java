package com.evilthreads.iterators;

import com.evilthreads.Node;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class LinkedIterator<T extends Comparable<T>> implements Iterator<T> {
    @Nullable
    private Node<T> current;

    public LinkedIterator(final @Nullable Node<T> node) {
        current = node;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        final T value = current.value;
        current = current.next;
        return value;
    }
}