import com.evilthreads.SortingType;
import com.evilthreads.queues.CircularArrayQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CircularArrayQueueTest {
    private final CircularArrayQueue<Integer> queue = new CircularArrayQueue<>(6);

    @BeforeEach
    void setUp() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
    }

    @AfterEach
    void tearDown() {
        queue.clear();
    }

    @Test
    void testIsEmpty(){
        assertFalse(queue.isEmpty());
        queue.clear();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testClear(){
        assertFalse(queue.isEmpty());
        queue.clear();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testEnqueue(){
        assertEquals(5, queue.size());
        queue.enqueue(6);
        assertEquals(6, queue.size());
    }

    @Test
    void testDequeue(){
        assertEquals(5, queue.size());
        assertEquals(1, queue.dequeue());

        for(int i = 0; i < 4; i++){
            queue.dequeue();
        }

        assertNull(queue.dequeue());
    }

    @Test
    void testPeek(){
        assertEquals(1, queue.peek());

        queue.clear();
        assertNull(queue.peek());
    }

    @Test
    void testSize(){
        assertEquals(5, queue.size());
        queue.enqueue(6);
        assertEquals(6, queue.size());
        queue.dequeue();
        assertEquals(5, queue.size());
        queue.clear();
        assertEquals(0, queue.size());
    }

    @Test
    void testResize(){
        queue.enqueue(6);
        assertEquals(6, queue.size());
        queue.enqueue(7);
        assertEquals(7, queue.size());
    }

    @Test
    void testContains(){
        assertTrue(queue.contains(3));
        assertFalse(queue.contains(6));
    }

    @Test
    void testContainsAll(){
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(3);
        values.add(5);
        assertTrue(queue.containsAll(values));
        values.add(6);
        assertFalse(queue.containsAll(values));
    }

    @Test
    void testToString(){
        final String string = "[1 2 3 4 5]";
        assertEquals(string, queue.toString());
    }

    @Test
    void testIterator(){
        final Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        for(int i = 0; i < 4; i++){
            iterator.next();
        }

        assertFalse(iterator.hasNext());
    }

    @Test
    void testEquals(){
        final CircularArrayQueue<Integer> other = new CircularArrayQueue<>(20);
        other.enqueue(1);
        other.enqueue(2);
        other.enqueue(3);
        other.enqueue(4);
        other.enqueue(5);
        assertEquals(other, queue);
    }

    @Test
    void testSelectionSort(){
        queue.dequeue(); //remove 1
        queue.dequeue(); //remove 2
        queue.dequeue(); // remove 3
        //queue = [4,5]
        queue.enqueue(6);//queue = [6,4,5]
        queue.enqueue(3);//queue = [3,6,4,5]
        queue.enqueue(9);//queue = [9,3,6,4,5]

        queue.selectionSort(SortingType.ASCENDING);

        final CircularArrayQueue<Integer> other = new CircularArrayQueue<>(20);
        other.enqueue(3);
        other.enqueue(4);
        other.enqueue(5);
        other.enqueue(6);
        other.enqueue(9);

        assertEquals(other, queue);

    }
}
