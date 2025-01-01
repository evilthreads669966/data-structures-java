package com.evilthreads.stacks;

import com.evilthreads.SortingType;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

public class ArrayStack<T extends Comparable<T>> {
    @NotNull
    private T[] array;
    private int top;
    private int initialCapacity;

    public ArrayStack() {
        array = (T[]) new Comparable[1000];
        top = -1;
        this.initialCapacity = initialCapacity;
    }

    public ArrayStack(int initialCapacity) {
        array = (T[]) new Comparable[initialCapacity];
        top = -1;
        this.initialCapacity = initialCapacity;
    }

    public void push(@NotNull T value){
        if(isFull()){
            resize();
        }

        array[++top] = value;
    }

    @NotNull
    public T pop() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        return array[top--];
    }

    @NotNull
    public T peek() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        return array[top];
    }

    public void clear(){
        array = (T[]) new Comparable[initialCapacity];
        top = -1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public int size(){
        return top + 1;
    }

    private boolean isFull(){
        return top == array.length - 1;
    }

    private void resize(){
        T[] arr = (T[]) new Comparable[array.length + initialCapacity];

        for(int index = 0; index < array.length - 1; index++){
            arr[index] = array[index];
        }

        array = arr;
    }

    public void selectionSort(){
        selectionSort(SortingType.ASCENDING);
    }

    public void selectionSort(@NotNull final SortingType sortingType){
        if(isEmpty())
            return;

        final int lessOrGreater;

        if(sortingType == SortingType.ASCENDING){
            lessOrGreater = 1;
        }else{
            lessOrGreater = -1;
        }

        for(int i = 0; i <= top; i++){
            for(int j = i+1; j <= top; j++){
                if(array[i].compareTo(array[j]) == lessOrGreater){
                    final T temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");

        if(isEmpty()){
            return sb.toString();
        }

        sb.append('[');

        for(int index = 0; index <= top; index++){
            if(index == top){
                sb.append(array[index]);
            }else{
                sb.append(array[index] + " ");
            }
        }

        sb.append(']');

        return sb.toString();
    }
}