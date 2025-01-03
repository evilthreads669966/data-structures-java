package com.evilthreads.iterators;

import java.util.Iterator;

public class CircularArrayIterator<T> implements Iterator<T> {
    private final T[] array;
    private int index;
    private int rear;
    private int front;

    public CircularArrayIterator (final T[] array, final int front, final int rear) {
        this.index = front;
        this.rear = rear;
        this.front = front;
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return index != rear;
    }

    @Override
    public T next() {
        final T value = array[index++];

        if(front > rear && index == array.length){
            index = 0;
        }

        return value;
    }
}
