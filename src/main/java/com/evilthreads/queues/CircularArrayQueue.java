package com.evilthreads.queues;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CircularArrayQueue<T extends Comparable<T>> {
    private T[] array;
    private int front;
    private int rear;
    private int size;

    public CircularArrayQueue(final int initialCapacity) {
        array = (T[]) new Comparable[initialCapacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    public boolean isEmpty(){
        return front == rear;
    }

    public void enqueue(@NotNull final T value){
        if(isFull()){
            resize();
        }

        array[rear++] = value;
        rear = rear % array.length;
        size++;
    }

    @Nullable
    public T dequeue(){
        final T value = array[front++];
        front = front % array.length;
        size--;

        return value;
    }

    @Nullable
    public T peek(){
        return array[front];
    }

    public void clear(){
        array = (T[]) new Comparable[array.length];
        front = 0;
        rear = 0;
        size = 0;
    }

    public int size(){
        return size;
    }

    private void resize(){
        final T[] arr = (T[]) new Comparable[array.length * 2];

        if(front > rear){
            for(int i = front; i < array.length; i++){
                arr[i] = array[i];
            }
            for(int i = 0; i < rear; i++){
                arr[i] = array[i];
            }
        }else{
            for(int i = front; i < rear; i++){
                arr[i] = array[i];
            }
        }

        front = 0;
        rear = size;
        array = arr;
    }

    private boolean isFull(){
        return size == array.length;
    }

    public boolean contains(@NotNull final T value){
        if(isEmpty()){
            return false;
        }

        if(front > rear){
            for(int i = front; i < array.length; i++){
                if(array[i].equals(value)){
                    return true;
                }
            }

            for(int i = 0; i < rear; i++){
                if(array[i].equals(value)){
                    return true;
                }
            }
        }else{
            for(int i = front; i < rear; i++){
                if(array[i].equals(value)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsAll(@NotNull final Collection<T> values){
        if(isEmpty()){
            return false;
        }

        for(final T value : values){
            if(!contains(value)){
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if(isEmpty()){
            return sb.toString();
        }

        sb.append('[');

        if(front > rear){
            for(int i = front; i < array.length; i++){
                sb.append(array[i] + " ");
            }
            for(int i = 0; i < rear; i++){
                if(i == rear - 1){
                    sb.append(array[i]);
                }else{
                    sb.append(array[i] + " ");
                }
            }
        }else{
            for(int i = front; i < rear; i++){
                if(i == rear - 1){
                    sb.append(array[i]);
                }else{
                    sb.append(array[i] + " ");
                }
            }
        }

        sb.append(']');

        return sb.toString();
    }
}
