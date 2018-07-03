import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /* Sample assert statement for comparing integers.*/
        assertEquals(CompoundInterest.numYears(2015), 0);
        assertEquals(CompoundInterest.numYears(2016), 1);
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(CompoundInterest.futureValue(10, -12, 2017), 7.744, tolerance);
        assertEquals(CompoundInterest.futureValue(10, 12, 2017), 12.544, tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(CompoundInterest.futureValueReal(10, 12, 2017, 3), 11.802, tolerance);
        assertEquals(CompoundInterest.futureValueReal(10,- 12, 2017, 3), 7.286, tolerance);

    }

    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(CompoundInterest.totalSavings(5000, 2017, 10), 16550, tolerance);
        assertEquals(CompoundInterest.totalSavings(5000, 2017, -10), 13550.0, tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(CompoundInterest.totalSavingsReal(5000, 2017, 10, 3), 16027.445,tolerance);
        assertEquals(CompoundInterest.totalSavingsReal(5000, 2017, -10, 3), 13175.645,tolerance);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
