import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a BST based String Set.
 * @author Hong Seok Jang
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    private Node put(String s, Node p) {
        if (p == null) {
            return new Node(s);
        }
        if (s.compareTo(p.s) > 0) {
            p.right = put(s, p.right);
        }
        if (s.compareTo(p.s) < 0) {
            p.left = put(s, p.left);
        }
        return p;
    }
    @Override
    public void put(String s) {
        root = put(s, root);
    }

    private boolean contains(String s, Node p) {
        if (p == null) {
            return false;
        }
        if (s.compareTo(p.s) > 0) {
            return contains(s, p.right);
        }
        if (s.compareTo(p.s) < 0) {
            return contains(s, p.left);
        }
        return true;
    }

    @Override
    public boolean contains(String s) {
        return contains(s, root); // FIXME
    }

    List<String> list = new ArrayList<>();

    public List<String> asList(Node p) {
        if(p != null) {
            asListHelper(root);
        }
        return list;
    }

    public void asListHelper(Node p) {
        if (p == null) {
            return;
        }

        asListHelper(p.left);
        list.add(p.s);
        asListHelper(p.right);
    }

    @Override
    public List<String> asList() {
        return asList(root);
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        public Node(String sp) {
            s = sp;
        }
    }

    /** Root node of the tree. */
    private Node root;
}
