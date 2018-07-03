import java.util.Arrays;
import java.util.Comparator;

/** Minimal spanning tree utility.
 *  @author Hong Seok Jang
 */
public class MST {

    /** Given an undirected, weighted, connected graph whose vertices are
     *  numbered 1 to V, and an array E of edges, returns a list of edges
     *  in E that form a minimal spanning tree of the input graph.
     *  Each edge in E is a three-element int array of the form (u, v, w),
     *  where 0 < u < v <= V are vertex numbers, and 0 <= w is the weight
     *  of the edge. The result is an array containing edges from E.
     *  Neither E nor the arrays in it may be modified.  There may be
     *  multiple edges between vertices.  The objects in the returned array
     *  are a subset of those in E (they do not include copies of the
     *  original edges, just the original edges themselves.) */
    public static int[][] mst(int V, int[][] E) {
        int[][] copy = new int[E.length][3];
        int length = E.length;
        for (int i = 0; i < length; i++) {
            System.arraycopy(E[i], 0, copy[i], 0, 3);
        }

        Arrays.sort(copy,EDGE_WEIGHT_COMPARATOR);

        UnionFind uf = new UnionFind(V);
        int[][] result = new int[V - 1][];
        for (int i = 0; i < V; i++) {
            result[i] = new int[3];
        }
        int e = 0;
        while (e < V - 1) {
            int x = uf.find(copy[e][0]);
            int y = uf.find(copy[e][1]);
            if (x == y) {
                break;
            }
            result[e++] = copy[e];
            uf.union(x, y);
        }

        return result;

    }


    /** An ordering of edges by weight. */
    private static final Comparator<int[]> EDGE_WEIGHT_COMPARATOR =
        new Comparator<int[]>() {
            @Override
            public int compare(int[] e0, int[] e1) {
                return e0[2] - e1[2];
            }
        };

}
