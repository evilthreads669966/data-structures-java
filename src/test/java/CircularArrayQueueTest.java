import com.evilthreads.queues.CircularArrayQueue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

}
