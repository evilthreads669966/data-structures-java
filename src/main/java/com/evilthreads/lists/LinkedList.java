package com.evilthreads.lists;

import com.evilthreads.Node;
import com.evilthreads.SortingType;
import com.evilthreads.iterators.LinkedIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

final public class LinkedList<T extends Comparable<T>> implements List<T>, Comparable<LinkedList<T>> {
    @Nullable
    private Node<T> head;
    @Nullable
    private Node<T> tail;
    private int size;

    @SafeVarargs
    public LinkedList(T... values){
        this.head = null;
        this.tail = null;
        this.size = 0;

        for(final T value : values){
            add(value);
        }
    }

    @NotNull
    public static <T extends Comparable<T>> LinkedList<T> fromIterable(@NotNull final Iterable<T> values){
        final LinkedList<T> list = new LinkedList<T>();

        list.addAll((Collection<? extends T>) values);

        return list;
    }

    public void push(@NotNull final T value){
        size++;
        final Node<T> node = new Node<T>(value);

        if(isEmpty()){
            head = node;
            tail = head;
            return;
        }

        node.next = head;
        head = node;
    }

    @NotNull
    public LinkedList<T> copy(){
        final LinkedList<T> list = new LinkedList<>();
        list.addAll(this);
        return list;
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

    public boolean isEmpty(){
        return head == null;
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }

    @Nullable
    public T firstOrNull(){
        try{
            return first();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    @NotNull
    public T first() throws NoSuchElementException{
        return peek();
    }

    @Nullable
    public T lastOrNull(){
        try{
            return last();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    @NotNull
    public T last() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        return tail.value;
    }

    @NotNull
    public T last(@NotNull final Predicate<T> predicate) throws NoSuchElementException{
        Node<T> curr = head;
        T value = null;

        while(curr != null){
            if(predicate.call(curr.value)){
                value = curr.value;
            }

            curr = curr.next;
        }

        if(value == null){
            throw new NoSuchElementException();
        }else{
            return value;
        }
    }

    @Override
    public boolean contains(Object o) {
        Node<T> curr = head;

        while(curr != null){
            if(curr.value.equals(o)){
                return true;
            }

            curr = curr.next;
        }

        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        if(isEmpty() || c.isEmpty()){
            return false;
        }

        for(final Object value : c){
            if(!contains(value)){
                return false;
            }
        }

        return true;
    }

    @NotNull
    public T get(final int index) throws IndexOutOfBoundsException{
        return getNode(index).value;
    }

    @Nullable
    public T getOrNull(final int index){
        try{
            return get(index);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @NotNull
    public T getOrElse(@NotNull final T defaultValue, final int index){
        try{
            return get(index);
        }catch(IndexOutOfBoundsException e){
            return defaultValue;
        }
    }

    @Override
    public T set(int index, T value) {
        getNode(index).value = value;
        return value;
    }

    @Override
    public void add(int index, T element) throws IndexOutOfBoundsException {
        if(isEmpty() || index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        if(index == 0){
            push(element);
        }

        final Node<T> curr = getNode(index - 1);
        final Node<T> node = new Node<T>(element);
        node.next = curr.next;
        curr.next = node;
        size++;
    }

    @Override
    public boolean add(T t) {
        size++;
        final Node<T> node = new Node<T>(t);

        if(isEmpty()){
            head = node;
            tail = head;

            return true;
        }

        tail.next = node;
        tail = tail.next;

        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        for(final T value : c){
            add(value);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) throws IndexOutOfBoundsException {
        if(isEmpty() || index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }

        if(c.isEmpty()){
            return false;
        }

        Node<T> curr = null;

        if(index > 0){
            curr = getNode(index - 1);
        }


        for(final T value : c){
            if(index == 0){
                push(value);

                curr = head;
                index++;
            }else{
                final Node<T> node = new Node<T>(value);
                node.next = curr.next;
                curr.next = node;
                size++;

                curr = curr.next;
            }


        }

        return true;
    }

    public void minus(@NotNull final Collection<T> values){
        removeAll(values);
    }

    public void plus(@NotNull final Collection<T> values){
        addAll(values);
    }

    @Override
    public T remove(int index) {
        if(isEmpty() || index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        if(index == 0){
            return pop();
        }

        Node<T> curr = head.next;
        Node<T> prev = head;
        int idx = 1;

        while(curr != null){
            if(idx == index){
                final T value = curr.value;
                prev.next = curr.next;
                size--;

                return value;
            }

            idx++;
            prev = curr;
            curr = curr.next;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(Object o) {
        try {
            if(isEmpty()){
                return false;
            }

            if(head.value.equals(o)){
                head = head.next;
                size--;

                return true;
            }

            Node<T> curr = head.next;
            Node<T> prev = head;

            while(curr != null){
                if(curr.value.equals(o)){
                    prev.next = curr.next;
                    size--;

                    if(prev.next == null){
                        tail = prev;
                    }

                    return true;
                }

                curr = curr.next;
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        boolean removed = false;

        if(isEmpty()){
            return removed;
        }

        Node<T> curr = head;
        Node<T> prev = null;

        while(curr != null){
            if(c.contains(curr.value)){
                curr = curr.next;
                if(prev == null){
                    head = curr;
                }else{
                    prev.next = curr;
                }

                removed = true;
                size--;

                if(curr == null){
                    tail = prev;
                }

                continue;
            }

            prev = curr;
            curr = curr.next;
        }

        return removed;
    }

    @Override
    public boolean removeIf(@NotNull java.util.function.Predicate<? super T> filter) {
        if(isEmpty()){
            return false;
        }

        boolean removed = false;
        Node<T> curr = head;
        Node<T> prev = null;

        while(curr != null){
            if(filter.test(curr.value)){
                curr = curr.next;
                if(prev == null){
                    head = curr;
                }else{
                    prev.next = curr;
                }

                removed = true;
                size--;

                if(curr == null){
                    tail = prev;
                }

                continue;
            }

            prev = curr;
            curr = curr.next;
        }

        return removed;
    }

    @Override
    public int indexOf(Object o) {
        if(isEmpty()){
            return -1;
        }

        Node<T> curr = head;
        int idx = 0;

        while(curr != null){
            if(curr.value.equals(o)){
                return idx;
            }

            idx++;
            curr = curr.next;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if(isEmpty()){
            return -1;
        }

        Node<T> curr = head;
        int idx = 0;
        int position = -1;

        while(curr != null){
            if(curr.value.equals(o)){
                position = idx;
            }

            idx++;
            curr = curr.next;
        }

        return position;
    }

    @Nullable
    public T minOrNull(){
        try{
            return min();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    @NotNull
    public T min() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        T min = head.value;

        if(head.next == null){
            return min;
        }

        Node<T> curr = head.next;


        while(curr != null){
            if(min.compareTo(curr.value) == 1){
                min = curr.value;
            }

            curr = curr.next;
        }

        return min;
    }

    @Nullable
    public T maxOrNull(){
        try{
            return max();
        }catch(NoSuchElementException e){
            return null;
        }
    }

    @NotNull
    public T max() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        T max = head.value;

        if(head.next == null){
            return max;
        }

        Node<T> curr = head.next;

        while(curr != null){
            if(max.compareTo(curr.value) == -1){
                max = curr.value;
            }

            curr = curr.next;
        }

        return max;
    }

    @Override
    public @NotNull List<T> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    @NotNull
    public List<T> slice(final int start, final int end) throws IndexOutOfBoundsException{
        if(start > end){
            throw new IndexOutOfBoundsException();
        }

        Node<T> curr = getNode(start);
        final LinkedList<T> list = new LinkedList<T>();
        int idx = start;

        while(curr != null && idx <= end){
            list.add(curr.value);

            curr = curr.next;
            idx++;
        }

        return list;
    }

    @NotNull
    public List<T> reversed(){
        final LinkedList<T> list = new LinkedList<T>();
        Node<T> curr = head;

        while(curr != null){
            list.push(curr.value);

            curr = curr.next;
        }

        return list;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        boolean removed = false;

        if(isEmpty() || c.isEmpty()){
            return removed;
        }

        Node<T> curr = head;
        Node<T> prev = null;

        while(curr != null){
            if(!c.contains(curr.value)){
                curr = curr.next;
                if(prev == null){
                    head = curr;
                }else{
                    prev.next = curr;
                }

                removed = true;
                size--;

                continue;
            }

            prev = curr;
            curr = curr.next;
        }

        return removed;
    }

    public <S> S fold(@NotNull final S initial, final Accumulation<S,T> operation){
        if(isEmpty()){
            return initial;
        }

        Node<T> curr = head;
        S accumulator = initial;

        while(curr != null){
            accumulator = operation.call(accumulator, curr.value);

            curr = curr.next;
        }

        return accumulator;
    }

    @NotNull
    public List<List<T>> chunked(int n){
        final List<List<T>> chunks = new ArrayList<List<T>>();

        if(isEmpty() || n <= 0){
            return chunks;
        }

        Node<T> curr = head;

        final int quotient = size / n;
        final int remainder = size % n;

        if(quotient == 0 || (quotient == 1 && remainder == 0)){
            chunks.add(this);
            return chunks;
        }

        final int groups;

        if(remainder > 0){
            groups = quotient + 1;
        }else{
            groups = quotient;
        }

        for(int i = 0; i <= groups - 1; i++){
            final LinkedList<T> chunk = new LinkedList<T>();
            int count = 0;

            while(curr != null && count < quotient){
                chunk.add(curr.value);

                curr = curr.next;
                count++;
            }

            chunks.add(chunk);
        }
        return chunks;
    }

    @NotNull
    public <S extends Comparable<S>> List<List<S>> chunked(int n, @NotNull final Transform<T,S> transform){
        final List<List<S>> chunks = new ArrayList<List<S>>();

        if(isEmpty() || n <= 0){
            return chunks;
        }

        Node<T> curr = head;
        final int quotient = size / n;
        final int remainder = size % n;

        if(quotient == 0 || (quotient == 1 && remainder == 0)){
            chunks.add(this.map((value) -> transform.call(value)));
            return chunks;
        }

        final int groups;

        if(remainder > 0){
            groups = quotient + 1;
        }else{
            groups = quotient;
        }

        for(int i = 0; i <= groups - 1; i++){
            final LinkedList<S> chunk = new LinkedList<S>();
            int count = 0;

            while(curr != null && count < quotient){
                chunk.add(transform.call(curr.value));

                curr = curr.next;
                count++;
            }

            chunks.add(chunk);
        }
        return chunks;
    }

    @NotNull
    public <S> List<S> flatMap(@NotNull final Transform<T,Iterable<S>> transform){
        final List<S> list = new ArrayList<S>();

        Node<T> curr = head;

        while(curr != null){
            final Iterable<S> values = transform.call(curr.value);
            list.addAll((Collection<? extends S>) values);

            curr = curr.next;
        }

        return list;
    }

    public <S> List<S> map(@NotNull final Transform<T,S> transform){
        Node<T> curr = head;
        final List<S> list = new ArrayList<S>();

        while(curr != null){
            list.add(transform.call(curr.value));

            curr = curr.next;
        }

        return list;
    }

    public List<T> mapIndexed(final int index, @NotNull final Transform<T,T> transform) throws IndexOutOfBoundsException{
        if(isEmpty() || index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        LinkedList<T> list = new LinkedList<T>();
        Node<T> curr = getNode(index);

        while(curr != null){
            curr.value = transform.call(curr.value);
            list.add(curr.value);

            curr = curr.next;
        }

        return list;
    }

    public boolean any(@NotNull final Predicate<T> predicate){
        Node<T> curr = head;
        while(curr != null){
            if(predicate.call(curr.value)){
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    @NotNull
    public <S> List<Pair<T,S>> zip(@NotNull final List<S> list){
        final List<Pair<T,S>> pairs = new ArrayList<Pair<T,S>>();
        if(isEmpty() || list.isEmpty()){
            return pairs;
        }

        final List shortest;

        if(size < list.size()){
            shortest = this;
        }else{
            shortest = list;
        }

        for(int i = 0; i < shortest.size(); i++){
            final Pair<T,S> pair = new Pair<T,S>(get(i), list.get(i));
            pairs.add(pair);
        }

        return pairs;
    }

    @NotNull
    public List<T> filter(@NotNull final Predicate<T> predicate){
        if(isEmpty()){
            return this;
        }

        final List<T> list = new ArrayList<T>();
        Node<T> curr = head;

        while(curr != null) {
            if (!predicate.call(curr.value)) {
                list.add(curr.value);
            }

            curr = curr.next;
        }

        return list;
    }

    @NotNull
    public String joinToString(@NotNull final String separator, @NotNull final String prefix, @NotNull final String postfix, @NotNull final Transform<T,String> transform){
        final StringBuilder sb = new StringBuilder(prefix);
        Node<T> curr = head;

        while(curr != null){
            final String string = transform.call(curr.value);

            if(curr.next == null){
                sb.append(string);
                break;
            }else{
                sb.append(string + separator);
            }

            curr = curr.next;
        }

        sb.append(postfix);

        return sb.toString();
    }

    @NotNull
    public String joinToString(@NotNull final String prefix, @NotNull final String postfix, @NotNull final Transform<T,String> transform){
        return joinToString(" ", prefix, postfix, transform);
    }

    @NotNull
    public String joinToString(@NotNull final String separator, @NotNull final Transform<T,String> transform){
        return joinToString(separator, "[", "]", transform);
    }

    @NotNull
    public String joinToString(@NotNull final Transform<T,String> transform){
        return joinToString(" ", "[", "]", transform);
    }

    @NotNull
    public List<T> drop(final int n) throws IllegalArgumentException{
        if(n < 0){
            throw new IllegalArgumentException();
        }

        if(n == 0){
            return this;
        }

        final List<T> list = new ArrayList<T>();

        if(isEmpty() || n >= size){
            return list;
        }

        Node<T> curr = head;
        int idx = 0;

        while(curr != null && idx < size - n){
            list.add(curr.value);

            idx++;
            curr = curr.next;
        }

        return list;
    }

    @NotNull
    public List<T> dropWhile(@NotNull final Predicate<T> predicate){
        final List<T> list = new LinkedList<T>();

        if(isEmpty()){
            return list;
        }

        Node<T> curr = head;

        while(curr != null && predicate.call(curr.value)){
            curr = curr.next;
        }

        while(curr != null){
            list.add(curr.value);

            curr = curr.next;
        }

        return list;
    }

    @NotNull
    public List<T> take(final int n) throws IllegalArgumentException{
        if(n < 0){
            throw new IllegalArgumentException();
        }

        if(n >= size || isEmpty()){
            return this;
        }

        final List<T> list = new ArrayList<T>();

        if(n == 0){
            return list;
        }

        Node<T> curr = head;
        int idx = 0;

        while(curr != null && idx < n){
            list.add(curr.value);

            idx++;
            curr = curr.next;
        }

        return list;
    }

    @NotNull
    public List<T> takeWhile(@NotNull final Predicate<T> predicate){
        final List<T> list = new LinkedList<T>();
        Node<T> curr = head;

        while(curr != null){
            if(predicate.call(curr.value)){
                list.add(curr.value);
            }else{
                return list;
            }

            curr = curr.next;
        }

        return list;
    }

    @NotNull
    @Override
    public Object[] toArray() {
        final Object[] array = new Object[size];
        int idx = 0;

        for(final T value : this){
            array[idx] = value;
            idx++;
        }

        return array;
    }

    @Override
    public @NotNull <T1> T1[] toArray(@NotNull T1[] a) throws ClassCastException{
        Node<T> curr = head;
        final int length = a.length;
        int idx = 0;

        while(curr != null && idx < length){
            a[idx] = (T1) curr.value;

            idx++;
            curr = curr.next;
        }

        return a;
    }

    @NotNull
    public Set<T> toSet(){
        final Set<T> set = new HashSet();
        Node<T> curr = head;

        while(curr != null){
            set.add(curr.value);

            curr = curr.next;
        }

        return set;
    }

    @NotNull
    public Set<T> toSortedSet(){
        final SortedSet<T> set = new TreeSet<T>();
        Node<T> curr = head;

        while(curr != null){
            set.add(curr.value);

            curr = curr.next;
        }

        return set;
    }

    public int countOccurrences(@NotNull final T value){
        int count = 0;

        if(isEmpty()){
            return count;
        }

        Node<T> curr = head;

        while(curr != null){
            if(curr.value.equals(value)){
                count++;
            }

            curr = curr.next;
        }

        return count;
    }

    public int countOccurrences(@NotNull final Predicate<T> predicate){
        int count = 0;

        if(isEmpty()){
            return count;
        }

        Node<T> curr = head;

        while(curr != null){
            if(predicate.call(curr.value)){
                count++;
            }

            curr = curr.next;
        }

        return count;
    }

    public void sort(@NotNull final SortingType sortingType){
        selectionSort(sortingType);
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
                if(curr.compareTo(startingNode) == lessOrGreater){
                    swapValues(startingNode, curr);
                }

                curr = curr.next;
            }

            startingNode = startingNode.next;
            curr = startingNode.next;
        }
    }

    public void sort(){
        selectionSort(SortingType.ASCENDING);
    }

    public void selectionSort(){
        selectionSort(SortingType.ASCENDING);
    }

    public void bubbleSort(){
        bubbleSort(SortingType.ASCENDING);
    }

    public void bubbleSort(@NotNull final SortingType sortingType){
        if(isEmpty() || size == 1){
            return;
        }

        final int greaterOrLess;

        if(sortingType == SortingType.ASCENDING){
            greaterOrLess = 1;
        }else{
            greaterOrLess = -1;
        }

        for(int i = 0; i < size - 1; i++){
            Node<T> curr = head;
            int idx = 0;

            while(idx < size - 1 - i){
                if(curr.value.compareTo(curr.next.value) == greaterOrLess){
                    swapValues(curr, curr.next);
                }

                curr = curr.next;
                idx++;
            }
        }
    }

    private void swapValues(@NotNull final Node<T> left, @NotNull final Node<T> right){
        final T temp = left.value;
        left.value = right.value;
        right.value = temp;
    }

    @NotNull
    private Node<T> getNode(final int index) throws IndexOutOfBoundsException{
        if(isEmpty() || index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        Node<T> curr = head;
        int idx = 0;

        while(curr != null){
            if(idx == index){
                return curr;
            }

            idx++;
            curr = curr.next;
        }

        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(!(obj instanceof LinkedList)){
            return false;
        }
        final LinkedList<T> other = (LinkedList<T>) obj;
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
    public String toString() {
        return joinToString(",", (value) -> value.toString());
    }

    @Override
    public int compareTo(@NotNull LinkedList<T> o) {
        if(size > o.size){
            return 1;
        }
        if(size < o.size){
            return -1;
        }
        return 0;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new LinkedIterator<T>(head);
    }

    @Override
    public @NotNull ListIterator<T> listIterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull ListIterator<T> listIterator(int index) {
        return null;
    }

    static final public class Pair<T, S>{
        @NotNull
        private T first;
        @NotNull
        private S second;

        public Pair(@NotNull final T first, @NotNull final S second){
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public void setFirst(@NotNull final T first) {
            this.first = first;
        }

        public S getSecond() {
            return second;
        }

        public void setSecond(@NotNull final S second) {
            this.second = second;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){
                return true;
            }
            if(obj == null){
                return false;
            }
            if(!(obj instanceof Pair)){
                return false;
            }

            final Pair other = (Pair) obj;

            if(other.first.equals(first) && other.second.equals(second)){
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    @FunctionalInterface
    public interface Predicate<T>{
        boolean call(@NotNull final T value);
    }

    @FunctionalInterface
    public interface Accumulation<T,S>{
        @NotNull
        T call(@NotNull T acc, @NotNull S value);
    }

    @FunctionalInterface
    public interface Transform<T,S>{
        @NotNull
        S call(@NotNull final T value);
    }
}