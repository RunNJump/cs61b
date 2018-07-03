import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  @author Hong Seok Jang
 */

public class MatrixUtilsTest {

    /**
     * Test AccumulateVertical in MatrixUtils.java
     */
    @Test
    public void testAccumulateVertical() {
        double[][] A = {{1000000, 1000000, 1000000, 1000000}, {1000000, 75990, 30003, 1000000}, {1000000, 30002, 103046, 1000000}, {1000000, 29515, 38273, 1000000}, {1000000, 73403, 35399, 1000000}, {1000000, 1000000, 1000000, 1000000}};
        double[][] B = {{1000000, 1000000, 1000000, 1000000}, {2000000, 1075990, 1030003, 2000000}, {2075990, 1060005, 1133049, 2030003}, {2060005, 1089520, 1098278, 2133049}, {2089520, 1162923, 1124919, 2098278}, {2162923, 2124919, 2124919, 2124919}};
        double[][] result = MatrixUtils.accumulateVertical(A);
        assertArrayEquals(result, B);
        double[][] C = null;
        double[][] resultNull = MatrixUtils.accumulateVertical(C);
        assertArrayEquals(resultNull, null);
    }

    /**
     * Test accumulate in MatrixUtils.java
     */
    @Test
    public void testAccumulate() {
        double[][] A = {{1000000, 1000000, 1000000, 1000000}, {1000000, 75990, 30003, 1000000}, {1000000, 30002, 103046, 1000000}, {1000000, 29515, 38273, 1000000}, {1000000, 73403, 35399, 1000000}, {1000000, 1000000, 1000000, 1000000}};
        double[][] B = {{1000000, 1000000, 1000000, 1000000}, {2000000, 1075990, 1030003, 2000000}, {2075990, 1060005, 1133049, 2030003}, {2060005, 1089520, 1098278, 2133049}, {2089520, 1162923, 1124919, 2098278}, {2162923, 2124919, 2124919, 2124919}};
        double[][] result = MatrixUtils.accumulate(A, MatrixUtils.Orientation.VERTICAL);
        assertArrayEquals(result, B);
        double[][] C = null;
        double[][] resultNull = MatrixUtils.accumulate(C, MatrixUtils.Orientation.VERTICAL);
        assertArrayEquals(resultNull, null);
        double[][] D = {{1000000, 1000000, 1000000, 1000000, 1000000, 1000000}, {1000000, 30003, 103046, 38273, 35399, 1000000}, {1000000, 75990, 30002, 29515, 73403, 1000000}, {1000000, 1000000, 1000000, 1000000 , 1000000, 1000000}};
        double[][] result2 = MatrixUtils.accumulate(D, MatrixUtils.Orientation.HORIZONTAL);
        assertArrayEquals(result2, B);
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
