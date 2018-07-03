import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author Hong Seok Jang
 */
public class ListsTest {
    @Test
    public void testNaturalRuns() {
        IntList example = IntList.list(1, 3, 7, 5, 4, 6, 9, 10, 10, 11);
        IntList2 result = Lists.naturalRuns(example);
        int[][] dArray = {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        IntList2 expect = IntList2.list(dArray);
        assertEquals(expect, result);
        IntList exampleNull = null;
        IntList2 result2 = Lists.naturalRuns(exampleNull);
        assertEquals(result2, null);
    }

    /** It might initially seem daunting to try to set up
    * Intlist2 expected.
    *
    * There is an easy way to get the IntList2 that you want in just
    * few lines of code! Make note of the IntList2.list method that
    * takes as input a 2D array.*/

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
