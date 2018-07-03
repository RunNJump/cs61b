import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  @author Hong Seok Jang
 */

public class ArraysTest {
    /**
     * Test catenate function from Arrays.java
     */
    @Test
    public void testCatenate() {
        int[] A = {1, 2, 3};
        int[] B = {4, 5, 6};
        int[] C = {1, 2, 3, 4, 5, 6};
        int[] result = Arrays.catenate(A, B);
        int[] resultNull = Arrays.catenate(null, B);
        assertArrayEquals(C, result);
        assertArrayEquals(B, resultNull);
    }
    /**
     * Test remove function from Arrays.java
     */
    @Test
    public void testRemove() {
        int[] A = {1, 2, 3, 4, 5};
        int[] B = {1, 2, 5};
        int[] C = {1, 2, 3};
        int[] result = Arrays.remove(A, 2, 2);
        int[] zeroLen = Arrays.remove(A, 3, 0);
        int[] result2 = Arrays.remove(A, 3, 2);
        assertArrayEquals(B, result);
        assertArrayEquals(A, zeroLen);
        assertArrayEquals(C, result2);
    }

    /**
     * Test naturalRuns function from Arrays.java
     */
    @Test
    public void testNaturalRuns() {
        int[] example =  {1, 3, 7, 5, 4, 6, 9, 10};
        int[][] result = Arrays.naturalRuns(example);
        int[][] dArray = {{1, 3, 7}, {5}, {4, 6, 9, 10}};
        int[] example2 = null;
        int[][] result2 = Arrays.naturalRuns(example2);
        assertArrayEquals(result2, null);
        assertArrayEquals(dArray, result);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
