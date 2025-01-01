package com.evilthreads;

import com.evilthreads.lists.LinkedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    @NotNull
    public T value;
    @Nullable
    public Node<T> next;

    public Node(@NotNull T value){
        this.value = value;
        this.next = null;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Node)){
            return false;
        }
        final Node<T> other = (Node<T>) obj;
        if(this.next.equals(other.next) && this.value.equals(other.value)){
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull Node<T> other) {
        return value.compareTo(other.value);
    }
}