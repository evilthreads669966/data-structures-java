import com.evilthreads.SortingType;
import com.evilthreads.stacks.ArrayStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayStackTest {
    private final ArrayStack<Integer> stack = new ArrayStack<>(10);

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
    void testClear(){
        stack.clear();
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
        assertTrue(stack.pop() instanceof Integer);
        stack.clear();
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    void testPeek(){
        assertEquals(5, stack.peek());
        assertTrue(stack.pop() instanceof Integer);
        stack.clear();
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    @Test
    void testSize(){
        assertEquals(5, stack.size());
        stack.pop();
        assertEquals(4, stack.size());
        stack.push(5);
        assertEquals(5, stack.size());
        stack.clear();
        assertEquals(0, stack.size());
    }

    @Test
    void testResize(){
        for(int i = 0; i < 10; i++){
            stack.push(i);
        }
        assertEquals(15, stack.size());
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
        final String string = "[1 2 3 4 5]";
        assertEquals(string, stack.toString());
    }

    @Test
    void testIterator(){
        final Iterator<Integer> iterator = stack.iterator();
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        for(int i = 0; i < 4; i++){
            iterator.next();
        }

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
