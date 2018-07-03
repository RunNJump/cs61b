import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6);
        IntList C = IntList.list(1, 2, 3, 4, 5, 6);
        assertEquals(IntList.dcatenate(A, B), C);
        assertEquals(IntList.dcatenate(null, B), B);
    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {
        IntList A = IntList.list(4, 5, 6);
        IntList B = IntList.list(5, 6);
        IntList C = IntList.list(4, 5, 6);
        assertEquals(IntList.subTail(A, -1), null);
        assertEquals(IntList.subTail(A, 0), A);
        assertEquals(IntList.subTail(A, 1), B);
        assertEquals(IntList.subTail(A, 3), null);
        assertEquals(IntList.subTail(null, 1), null);
        IntList.subTail(A, 1);
        assertEquals(A, C);
    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {
        IntList A = IntList.list(4, 5, 6, 7);
        IntList B = IntList.list(5, 6);
        IntList C = IntList.list(4, 5, 6, 7);
        assertEquals(IntList.sublist(A, -1, 2), null);
        assertEquals(IntList.sublist(A, 1, -2), null);
        assertEquals(IntList.sublist(null, 1, 2), null);
        assertEquals(IntList.sublist(A, 1, 2), B);
        IntList.sublist(A, 1, 2);
        assertEquals(A, C);
    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() {
        IntList A = IntList.list(4, 5, 6, 7);
        IntList B = IntList.list(5, 6);
        assertEquals(IntList.dsublist(A, -1, 2), null);
        assertEquals(IntList.dsublist(A, 1, -2), null);
        assertEquals(IntList.dsublist(null, 1, 2), null);
        assertEquals(IntList.dsublist(A, 1, 2), B);
        assertEquals(IntList.dsublist(A, 0, 4), A);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
