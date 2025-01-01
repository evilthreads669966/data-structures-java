package com.evilthreads.stacks;

import com.evilthreads.Node;
import com.evilthreads.SortingType;
import com.evilthreads.iterators.LinkedIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<T extends Comparable<T>> implements Iterable<T>{
    private Node<T> head;
    private int size;

    public LinkedStack() {
        this.head = null;
        this.size = 0;
    }

    public void push(@NotNull final T value){
        Node<T> node = new Node<>(value);

        node.next = head;
        head = node;
        size++;
    }

    @NotNull
    public T pop() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        final T value = head.value;
        head = head.next;
        size--;

        return value;
    }

    @NotNull
    public T peek() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        return head.value;
    }

    public int size(){
        return size;
    }

    public void clear(){
        head = null;
        size = 0;
    }

    public boolean contains(final T value){
        if(isEmpty()){
            return false;
        }

        Node<T> curr = head;

        while(curr != null){
            if(curr.value.equals(value)){
                return true;
            }

            curr = curr.next;
        }

        return false;
    }

    public boolean containsAll(final Iterable<T> values){
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

    public boolean isEmpty(){
        return head == null;
    }

    public void selectionSort(@NotNull final SortingType sortingType){
        if(isEmpty()){
            return;
        }

        final int lessOrGreater;

        if(sortingType == SortingType.ASCENDING){
            lessOrGreater = -1;
        }else{
            lessOrGreater = 1;
        }

        Node<T> startingNode = head;
        Node<T> curr = head.next;

        while(curr != null){
            while(curr != null){
                if(startingNode.value.compareTo(curr.value) == lessOrGreater){
                    swapValues(startingNode, curr);
                }

                curr = curr.next;
            }

            startingNode = startingNode.next;
            curr = startingNode.next;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");

        if(isEmpty()){
            return sb.toString();
        }

        sb.append("[");

        Node<T> curr = head;

        while(curr != null){
            if(curr.next == null){
                sb.append(curr.value);
            }else{
                sb.append(curr.value).append(" ");
            }
            curr = curr.next;
        }

        sb.append("]");

        return sb.toString();
    }

    private void swapValues(Node<T> left, Node<T> right){
        final T temp = left.value;
        left.value = right.value;
        right.value = temp;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return new LinkedIterator<T>(head);
    }
}
