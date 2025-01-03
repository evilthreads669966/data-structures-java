import com.evilthreads.queues.LinkedQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedQueueTest {
    private final LinkedQueue<Integer> queue = new LinkedQueue<>();

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
    void isEmpty(){
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
        assertEquals(6, queue.last());
    }

    @Test
    void testDequeue(){
        assertEquals(5, queue.size());
        assertEquals(1,queue.dequeue());
        assertEquals(4,queue.size());

        queue.clear();
        assertNull(queue.dequeue());
    }

    @Test
    void testPeek(){
        assertEquals(1,queue.peek());
        queue.clear();
        assertNull(queue.peek());
    }

    @Test
    void testLast(){
        assertEquals(5,queue.last());
    }

    @Test
    void testSize(){
        assertEquals(5,queue.size());
        queue.dequeue();
        assertEquals(4,queue.size());
        queue.enqueue(6);
        assertEquals(5,queue.size());
        queue.clear();
        assertEquals(0,queue.size());
    }

    @Test
    void testIterator(){
        final Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testToString(){
        final String string = "[1 2 3 4 5]";
        assertEquals(string, queue.toString());
    }

    @Test
    void testEquals(){
        final LinkedQueue<Integer> other = new LinkedQueue<>();
        other.enqueue(1);
        other.enqueue(2);
        other.enqueue(3);
        other.enqueue(4);
        other.enqueue(5);

        assertEquals(other, queue);
    }
}
