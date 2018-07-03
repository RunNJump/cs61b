/* NOTE: The file ArrayUtil.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Hong Seok Jang
 */
class Arrays {
    /* C. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        if (A == null) {
            return B;
        }
        int[] C = new int[A.length + B.length];

        for (int i = 0; i < A.length; i++) {
            C[i] = A[i];
        }

        for (int i = 0; i < B.length; i++) {
            C[i + A.length] = B[i];
        }

        return C;
    }

    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        if (len <= 0) {
            return A;
        }

        int realLen = 0;
        if (start + len > A.length) {
            realLen = A.length - start;
        }
        realLen = len;

        int[] B = new int[A.length - realLen];
        for (int i = 0; i < start; i++) {
            B[i] = A[i];
        }

        for (int i = start; i < B.length && i + realLen < A.length; i++) {
            B[i] = A[i + realLen];
        }
        return B;
    }

    /* E. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        if (A == null) {
            return null;
        }
        int border = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i] <= A[i - 1]) {
                border++;
            }
        }

        int keepLen = A.length;
        int[][] B = new int[border + 1][A.length];

        for (int i = 0; i < border; i++) {
            int[] temp = B[i];
            for (int j = 1; j < keepLen; j++) {
                temp[j - 1] = A[j - 1];
                if (A[j] <= A[j - 1]) {
                    B[i] = remove(temp, j, keepLen - j);
                    A = remove(A, 0, j);
                    break;
                }
            }
        }
        int[] temp = B[border];
        int i = 0;
        for (; i < A.length; i++) {
            temp[i] = A[i];
        }
        B[border] = remove(temp, i, A.length);
        return B;
    }
}
