package com.evilthreads.iterators;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {
    @NotNull
    private final T[] array;
    private int index;

    public ArrayIterator (@NotNull final T[] array) {
        this.array = array;
        this.index = -1;
    }

    @Override
    public boolean hasNext() {
        return array[index + 1] != null;
    }

    @Override
    public T next() {
        return array[++index];
    }
}
