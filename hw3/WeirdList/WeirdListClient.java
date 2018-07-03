/** Functions to increment and sum the elements of a WeirdList.
 * @author Hong Seok Jang
 */
class WeirdListClient {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        Adder adder = new Adder(n);
        return L.map(adder);
    }

    /** Return the sum of the elements in L. */
    static int sum(WeirdList L) {
        Summer summer = new Summer();
        L.map(summer);
        return summer.result();
    }

    /** Since map function get IntUnaryFunction, I made helper function. */
    public static class Adder implements IntUnaryFunction {
        /** Make int that will apply to L. */
        private int _a;

        /**
         * constructor.
         * @param a int that will apply to L
         */
        public Adder(int a) {
            _a = a;
        }

        /**
         * To apply function recursively, I used apply(x).
         * @param x head of each WeirdList
         * @return _a + x
         */
        public int apply(int x) {
            return _a + x;
        }
    }

    /** Since map function get IntUnaryFunction, I made helper function.*/
    public static class Summer implements IntUnaryFunction {
        /** Make int to return and set 0.*/
        private int total = 0;

        /** Constructor.*/
        public Summer() {
            this.total = 0;
        }

        /**
         * To apply function recursively, I used apply(x).
         * @param x head of each WeirdList
         * @return return the head (I will not use this return)
         */
        public int apply(int x) {
            total += x;
            return x;
        }

        /**
         * Make a function that return result.
         * @return total sum of each value.
         */
        public int result() {
            return total;
        }
    }

    /* As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     */
}
