package com.evilthreads.queues;

import com.evilthreads.Node;
import com.evilthreads.iterators.LinkedIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class LinkedQueue<T extends Comparable<T>> implements Iterable<T> {
    private Node<T> head, tail;
    private int size;

    public LinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(@NotNull final T value){
        final Node<T> newNode = new Node<>(value);
        if (head == null){
            newNode.next = head;
            head = newNode;
            tail = head;
        }else{
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    @Nullable
    public T dequeue(){
        if(isEmpty()){
            return null;
        }

        final T value = head.value;
        head = head.next;

        size--;

        return value;
    }

    @Nullable
    public T peek(){
        if(isEmpty()){
            return null;
        }

        return head.value;
    }

    @Nullable
    public T last(){
        if(isEmpty()){
            return null;
        }

        return tail.value;
    }

    public int size(){
        return size;
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty(){
        return head == null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

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

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!(obj instanceof LinkedQueue)){
            return false;
        }

        final LinkedQueue<T> other = (LinkedQueue<T>) obj;

        if(size != other.size){
            return false;
        }

        Node<T> curr = head;
        Node<T> otherCurr = other.head;

        while(curr != null){
            if(!curr.value.equals(otherCurr.value)){
                return false;
            }

            curr = curr.next;
            otherCurr = otherCurr.next;
        }

        return true;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return new LinkedIterator<T>(head);
    }
}
