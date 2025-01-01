package com.evilthreads.iterators;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ArrayStackIterator<T> implements Iterator<T> {
    @NotNull
    private final T[] array;
    private int top;

    public ArrayStackIterator(@NotNull final T[] array, final int top) {
        this.array = array;
        this.top = top;
    }

    @Override
    public boolean hasNext() {
        return top != -1;
    }

    @Override
    public T next() {
        return array[top--];
    }
}
