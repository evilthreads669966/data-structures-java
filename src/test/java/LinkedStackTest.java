import com.evilthreads.SortingType;
import com.evilthreads.stacks.LinkedStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedStackTest {
    private final LinkedStack<Integer> stack = new LinkedStack<>();

    @BeforeEach
    public void setUp() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
    }

    @AfterEach
    public void tearDown() {
        stack.clear();
    }

    @Test
    void clear(){
        assertFalse(stack.isEmpty());
        assertEquals(5, stack.size());
        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void testIsEmpty(){
        assertFalse(stack.isEmpty());
        stack.clear();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPush(){
        stack.push(6);
        assertEquals(6, stack.pop());
    }

    @Test
    void testPop(){
        assertEquals(5, stack.pop());

        stack.clear();
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void testPeek(){
        assertEquals(5, stack.peek());

        stack.clear();
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void testSize(){
        assertEquals(5, stack.size());
        stack.pop();
        assertEquals(4, stack.size());
        stack.push(6);
        assertEquals(5, stack.size());
        stack.clear();
        assertEquals(0, stack.size());
    }

    @Test
    void testSelectionSort(){
        stack.clear();
        stack.push(2);
        stack.push(5);
        stack.push(1);
        stack.push(3);
        stack.push(4);

        stack.selectionSort(SortingType.ASCENDING);

        for(int i = 5; i > 0; i--){
            assertEquals(i, stack.pop());
        }
    }

    @Test
    void testToString(){
        final String string = "[5 4 3 2 1]";
        assertEquals(string, stack.toString());
    }

    @Test
    void testIterator(){
        final Iterator<Integer> iterator = stack.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());

        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testContains(){
        assertTrue(stack.contains(5));
        assertFalse(stack.contains(6));
    }

    @Test
    void testContainsAll(){
        List<Integer> values = Arrays.asList(1,3,5);
        assertTrue(stack.containsAll(values));
        values = Arrays.asList(1,2,3,6);
        assertFalse(stack.containsAll(values));
    }
}
