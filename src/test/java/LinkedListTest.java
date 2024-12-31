import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {
    private final LinkedList<Integer> list = new LinkedList<>();

    @BeforeEach
    void setUp() {
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        list.push(5);
    }

    @AfterEach
    void tearDown() {
        list.clear();
    }

    @Test
    void testClearAndIsEmpty() {
        assertFalse(list.isEmpty());

        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void testPush() {
        list.push(6);
        assertEquals(6, list.size());
        assertEquals(6, list.peek());
    }

    @Test
    void testPop() {
        final int value = list.pop();
        assertEquals(5, value);

        list.clear();
        assertThrows(NoSuchElementException.class, () -> list.pop());
    }

    @Test
    void testSize() {
        assertEquals(5, list.size());

        list.push(1);
        assertEquals(6, list.size());

        list.add(2, 2);
        assertEquals(7, list.size());

        list.add(2);
        assertEquals(8, list.size());

        list.remove(2);
        assertEquals(7, list.size());

        list.clear();
        list.push(1);
        list.push(2);
        list.remove(Integer.valueOf(1));
        assertEquals(2, list.last());
    }

    @Test
    void testPeek() {
        final int value = list.peek();
        assertEquals(5, value);

        list.clear();
        assertThrows(NoSuchElementException.class, () -> list.peek());
    }

    @Test
    void testRemove() {
        assertTrue(list.remove(Integer.valueOf(3)));
        assertFalse(list.remove(Integer.valueOf(6)));
    }

    @Test
    void testRemoveAll() {
        final List<Integer> values = new ArrayList<>();
        values.add(2);
        values.add(4);

        assertTrue(list.removeAll(list.toSet()));
        assertTrue(list.isEmpty());
        assertFalse(list.contains(2));
        assertFalse(list.contains(4));
    }

    @Test
    void testRemoveIf() {
        list.removeIf((value) -> value % 2 == 0);
        assertEquals(3, list.size());
        for (int value : list) {
            assertFalse(value % 2 == 0);
        }
    }

    @Test
    void removebyIndex() {
        final int value = list.remove(2);
        assertEquals(3, value);
        assertEquals(4, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));

        list.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void testMinus(){
        final List<Integer> values = Arrays.asList(2,4);
        list.minus(values);
        assertEquals(3, list.size());
        assertFalse(list.containsAll(values));
    }

    @Test
    void testPlus(){
        final List<Integer> values = Arrays.asList(6,7,8,9,10);
        list.plus(values);
        assertEquals(10, list.size());
        assertEquals(10, list.last());
        assertTrue(list.containsAll(values));
    }

    @Test
    void testAdd() {
        list.add(0);
        assertEquals(0, list.lastOrNull());
        assertEquals(6, list.size());
    }

    @Test
    void testAddWithIndex() {
        list.add(2, 10);

        assertTrue(list.contains(10));
        assertEquals(2, list.indexOf(10));
    }

    @Test
    void testAddAll() {
        final List<Integer> values = new ArrayList<>();
        values.add(10);
        values.add(20);

        list.addAll(0, values);
        assertEquals(20, list.get(1));
        assertTrue(list.containsAll(values));
    }

    @Test
    void testAddAllWithIndex(){
        final List<Integer> values = new ArrayList<>();
        values.add(10);
        values.add(20);
        list.addAll(0, values);
        assertEquals(10, list.first());
        assertEquals(7, list.size());
        assertTrue(list.containsAll(values));

        values.clear();
        values.add(30);
        values.add(40);
        list.addAll(2, values);
        assertEquals(9, list.size());
        assertEquals(30, list.get(2));
        assertTrue(list.containsAll(values));

        assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(-1, values));
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(list.size(), values));
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.clear();
            list.addAll(0, values);
        });
    }

    @Test
    void testGet() {
        assertEquals(3, list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
    }

    @Test
    void getOrNull(){
        assertNotNull(list.getOrNull(0));

        list.clear();
        assertNull(list.getOrNull(0));
    }

    @Test
    void getOrElse(){
        int value = list.getOrElse(10, 10);
        assertEquals(10, value);

        value = list.getOrElse(10, 2);
        assertEquals(3, value);
    }

    @Test
    void testSet() {
        list.set(2, 10);
        assertEquals(10, list.get(2));

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(list.size(), 20));
    }

    @Test
    void testIndexOf() {
        assertEquals(2, list.indexOf(3));
        assertEquals(-1, list.indexOf(6));
    }

    @Test
    void testLastIndexOf() {
        list.add(2, 1);
        assertEquals(4, list.lastIndexOf(2));
        assertEquals(-1, list.lastIndexOf(6));
    }

    @Test
    void testFirst() {
        assertEquals(5, list.first());

        list.clear();
        assertThrows(NoSuchElementException.class, () -> list.first());
    }

    @Test
    void testFirstOrNull() {
        assertEquals(5, list.firstOrNull());

        list.clear();
        ;
        assertNull(list.firstOrNull());
    }

    @Test
    void testLast() {
        assertEquals(1, list.last());

        list.clear();
        assertThrows(NoSuchElementException.class, () -> list.last());
    }

    @Test
    void testLastOrNull() {
        assertEquals(1, list.lastOrNull());

        list.clear();
        assertNull(list.lastOrNull());
    }

    @Test
    void testContains() {
        assertTrue(list.contains(3));
        assertFalse(list.contains(6));
    }

    @Test
    void testContainsAll() {
        final List<Integer> values = new ArrayList<>();
        values.add(2);
        values.add(4);
        assertTrue(list.containsAll(values));

        values.add(6);
        assertFalse(list.containsAll(values));
    }

    @Test
    void testMin() {
        assertEquals(1, list.min());

        list.clear();
        assertThrows(NoSuchElementException.class, () -> list.min());
    }

    @Test
    void testMinOrNull() {
        assertEquals(1, list.minOrNull());

        list.clear();
        assertNull(list.minOrNull());
    }

    @Test
    void testMax() {
        assertEquals(5, list.max());

        list.clear();
        assertThrows(NoSuchElementException.class, () -> list.max());
    }

    @Test
    void testMaxOrNull() {
        assertEquals(5, list.maxOrNull());

        list.clear();
        assertNull(list.maxOrNull());
    }

    @Test
    void testRetainAll(){
        final List<Integer> values = new ArrayList<>();
        values.add(5);
        values.add(4);
        values.add(3);

        assertTrue(list.retainAll(values));
        assertTrue(list.containsAll(values));
        assertEquals(values.size(), list.size());
    }

    @Test
    void testCompareTo() {
        final LinkedList<Integer> values = new LinkedList<>();
        values.push(1);
        assertEquals(1, list.compareTo(values));

        values.addAll(list.toSet());
        assertEquals(-1, list.compareTo(values));
    }

    @Test
    void testZip() {
        final List<String> values = new ArrayList<String>();
        values.add("a");
        values.add("b");
        values.add("c");
        final List<LinkedList.Pair<Integer, String>> pairs = list.zip(values);

        assertEquals(3, pairs.size());
        assertEquals(new LinkedList.Pair(5, "a"), pairs.get(0));
    }

    @Test
    void testFilter() {
        final List<Integer> values = list.filter(value -> value % 2 == 0);
        assertEquals(3, values.size());
        for (int value : values) {
            assertFalse(value % 2 == 0);
        }
    }

    @Test
    void testFold() {
        final int total = list.fold(0, (acc, value) -> acc + value);
        assertEquals(15, total);
    }

    @Test
    void testAny(){
        assertTrue(list.any((value) -> value % 2 == 0));
        assertFalse(list.any((value) -> value > 6));
    }

    @Test
    void testFlatMap(){
        LinkedList<Box<Integer>> linkedList = new LinkedList<>();
        linkedList.add(new Box(Arrays.asList(1,2,3)));
        linkedList.add(new Box(Arrays.asList(4,5,6)));

        List<Integer> values = linkedList.flatMap((box) -> box.values);

        assertEquals(6, values.size());
        assertTrue(values.containsAll(Arrays.asList(1,2,3,4,5,6)));
    }

    @Test
    void testMap(){
        final List<Integer> values = list.map((value) -> value + 1);
        int value = 6;

        for(final int v : values){
            assertEquals(value, v);
            value--;
        }
    }

    @Test
    void testDrop() {
        final List<Integer> values = list.drop(2);
        assertEquals(3, values.size());
        assertEquals(3, values.get(values.size() - 1));

        assertThrows(IllegalArgumentException.class, () -> list.drop(-1));

        assertIterableEquals(list, list.drop(0));
        assertTrue(list.drop(list.size()).isEmpty());
    }

    @Test
    void testDropWhile(){
        final List<Integer> values = list.dropWhile(value -> value > 3);
        assertEquals(3, values.size());
    }

    @Test
    void testTake() {
        final List<Integer> values = list.take(2);
        assertEquals(2, values.size());
        assertEquals(5, values.get(0));
        assertEquals(4, values.get(values.size() - 1));

        assertThrows(IllegalArgumentException.class, () -> list.take(-1));
        assertIterableEquals(list, list.take(list.size()));
    }

    @Test
    void testTakeWhile(){
        final List<Integer> values = list.takeWhile((value) -> value > 2);
        assertEquals(3, values.size());

        int value = 5;

        for(final int v : values){
            assertEquals(value, v);
            value--;
        }
    }

    @Test
    void testSlice() {
        final List<Integer> values = list.slice(1, 3);
        assertEquals(3, values.size());
        assertEquals(4, values.get(0));
        assertEquals(2, values.get(values.size() - 1));

        assertThrows(IndexOutOfBoundsException.class, () -> list.slice(-1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.slice(0, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.slice(list.size(), 2));

        list.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> list.slice(0, 2));
    }

    @Test
    void testChunked() {
        List<List<Integer>> chunks = list.chunked(2);
        assertEquals(3, chunks.size());
        assertEquals(2, chunks.get(0).size());
        assertEquals(1, chunks.get(chunks.size() - 1).size());

        chunks = list.chunked(-1);
        assertEquals(0, chunks.size());

        chunks = list.chunked(list.size() + 1);
        assertEquals(list.size(), chunks.get(0).size());
    }

    @Test
    void testHigherOrderChunked(){
        List<List<String>> chunks = list.chunked(5, (value) -> value.toString());
        assertEquals(1, chunks.size());

        chunks = list.chunked(2, (value) -> value.toString());
        assertEquals(3, chunks.size());
        assertEquals(2, chunks.get(0).size());
        assertEquals(1, chunks.get(chunks.size() - 1).size());
    }

    @Test
    void testLinkedListConstructor() {
        final LinkedList<Integer> linkedList = new LinkedList<>(1, 2, 3);
        assertEquals(3, linkedList.size());
        assertEquals(3, linkedList.last());
        assertEquals(1, linkedList.first());

        final LinkedList<Integer> emptyList = new LinkedList<>();
        assertEquals(0, emptyList.size());
        assertThrows(NoSuchElementException.class, () -> emptyList.pop());
        assertThrows(NoSuchElementException.class, () -> emptyList.last());
    }

    @Test
    void testFromIterable(){
        final List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);

        final LinkedList<Integer> newList = LinkedList.fromIterable(values);
        assertEquals(3, newList.size());
        assertIterableEquals(values, newList);
    }

    @Test
    void testIterator(){
        final Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testCountOccurrences(){
        list.add(5);
        assertEquals(2, list.countOccurrences(5));
        assertEquals(0, list.countOccurrences(6));
    }

    @Test
    void testHigherOrderCountOccurrences(){
        int count = list.countOccurrences((value) -> value % 2 == 0);
        assertEquals(2, count);
    }

    @Test
    void testToSet(){
        final int size = list.size();
        list.addAll(list.copy());
        final Set<Integer> set = list.toSet();
        assertEquals(size, set.size());
    }

    @Test
    void testToSortedSet(){
        list.clear();
        list.add(5);
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(4);

        final Set<Integer> set = list.toSortedSet();
        assertEquals(5, set.size());

        int value = 1;

        for(int item : set){
            assertEquals(value, item);
            value++;
        }
    }

    @Test
    void toArray(){
        Object[] array = list.toArray();
        assertEquals(list.size(), array.length);
        assertTrue(((Integer)array[0]) instanceof Integer);
    }

    @Test
    void toArrayWithParameter(){
        final Object[] array = new Object[list.size()];
        final Object[] values = list.toArray(array);

        for(int i = 0; i < list.size(); i++){
            System.out.println(values[i]);
        }
    }

    @Test
    void testSelectionSort() {
        list.push(10);
        list.push(0);
        list.add(20);
        list.add(-1);
        list.selectionSort(LinkedList.SortingType.ASCENDING);
        assertEquals(-1, list.peek());
        assertEquals(20, list.last());
    }

    @Test
    void testBubbleSort(){
        LinkedList<Integer> values = new LinkedList<>(3,2,4,5,1);
        values.bubbleSort(LinkedList.SortingType.ASCENDING);
        assertEquals(5, values.size());

        int value = 1;

        for(int v : values) {
            assertEquals(value, v);
            value++;
        }

        values = new LinkedList<>(3,2,4,5,1);
        values.bubbleSort(LinkedList.SortingType.DESCENDING);
        value = 5;
        for(int v : values) {
            assertEquals(value, v);
            value--;
        }
    }

    @Test
    void testReversed(){
        final List<Integer> values = list.reversed();
        int value = 1;

        for(final int v : values) {
            assertEquals(value, v);

            value++;
        }
    }

    @Test
    void testHigherOrderLast(){
        int value = list.last((it) -> it % 2 == 0);
        assertEquals(2, value);
        assertThrows(NoSuchElementException.class, () -> list.last((it) -> it > 6));
    }

    @Test
    void testMapIndexed(){
        final List<Integer> values = list.mapIndexed(2, (value) -> value + 1);

        int value = 4;

        for(final int v : values){
            assertEquals(value, v);
            value--;
        }

        assertThrows(IndexOutOfBoundsException.class, () -> list.mapIndexed(-1, (v) -> v));
        assertThrows(IndexOutOfBoundsException.class, () -> list.mapIndexed(list.size(), (v) -> v));
        list.clear();
        assertThrows(IndexOutOfBoundsException.class, () -> list.mapIndexed(0, (v) -> v));
    }

    @Test
    void copy(){
        final List<Integer> values = list.copy();
        assertEquals(list, values);
    }

    @Test
    void testToString(){
        String string = "[5,4,3,2,1]";
        assertEquals(string, list.toString());
    }

    @Test
    void testJoinToString(){
        String string = list.joinToString((value) -> value.toString());
        assertEquals("[5 4 3 2 1]", string);

        string = list.joinToString("{", "}", (value) -> value.toString());
        assertEquals("{5 4 3 2 1}", string);

        string = list.joinToString(",", (value) -> value.toString());
        assertEquals("[5,4,3,2,1]", string);
    }
}

class Box<T> implements Comparable<Box<T>>{
    final List<T> values;

    public Box(final List<T> values) {
        this.values = values;
    }

    @Override
    public int compareTo(@NotNull Box<T> o){
        return 0;
    }
}