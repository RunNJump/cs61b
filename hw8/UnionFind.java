import java.util.Arrays;

/** A partition of a set of contiguous integers that allows (a) finding whether
 *  two integers are in the same partition set and (b) replacing two partitions
 *  with their union.  At any given time, for a structure partitioning
 *  the integers 1-N, each partition is represented by a unique member of that
 *  partition, called its representative.
 *  @author Hong Seok Jang
 */
public class UnionFind {

    /**
     * Array that collection of parents(parent[i] = parent of i).
     */
    private static int[] parent;
    /**
     * rank of i.
     */
    private static int[] rank;

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /** Return the representative of the partition currently containing V.
     *  Assumes V is contained in one of the partitions.  */
    public int find(int v) {
        if (parent[v] == v) {
            return v;
        }
        return parent[v] = find(parent[v]);
    }

    /** Return true iff U and V are in the same partition. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single partition, returning its representative. */
    public int union(int u, int v) {
        u = find(u);
        v = find(v);
        if (u == v) {
            return u;
        }

        if (rank[u] > rank[v]) {
            parent[v] = u;
        } else if (rank[u] < rank[v]) {
            parent[u] = v;
        } else {
            parent[v] = u;
            rank[u]++;
        }
        return u;
    }

    /**
     * Change UnionFind to string.
     * @return string,
     */
    public String toString() {
        return "<UnionFind\nparent " + Arrays.toString(parent)
                + "\nrank " + Arrays.toString(rank) + "\n>";
    }

    /**
     * test.
     * @param args unused.
     */
    public static void main(String[] args) {
        UnionFind uf = new UnionFind(5);
        System.out.println(uf);

        uf.union(1,2);
        System.out.println("union 1 2");
        System.out.println(uf);

        uf.union(1,2);
        System.out.println("union 1 2");
        System.out.println(uf);

        uf.union(1,0);
        System.out.println("union 1 0");
        System.out.println(uf);

        uf.find(3);
        System.out.println("find 3");
        System.out.println(uf);
    }
}


