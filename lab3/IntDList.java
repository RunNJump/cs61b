import sun.util.resources.cldr.fo.CurrencyNames_fo;

public class IntDList {

    protected DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /** Returns the first item in the IntDList. */
    public int getFront() {
        return _front._val;
    }

    /** Returns the last item in the IntDList. */
    public int getBack() {
        return _back._val;
    }

    /** Return value #I in this list, where item 0 is the first, 1 is the
     *  second, ...., -1 is the last, -2 the second to last.... */
    public int get(int i) {
        DNode p = _front;
        if (i < 0) {
            p = _back;
            while (i != -1 && p._prev != null) {
                p = p._prev;
                i++;
            }
        }
        else if (i > 0) {
            while (i != 0 && p._next != null) {
                p = p._next;
                i--;
            }
        }
        if (p == null)
            return -1;
        return p._val;  // Your code here
    }

    /** The length of this list. */
    public int size() {
        DNode p = _front;
        int siz = 0;
        while (p != null) {
            siz++;
            p = p._next; // Your code here
        }
        return siz;
    }

    /** Adds D to the front of the IntDList. */
    public void insertFront(int d) {
        DNode insert = new DNode(d);
        DNode p = _front;
        insert._next = p;
        if (p != null)
            p._prev = insert;
        _front = insert;
        if (_back == null)
            _back = insert; // Your code here
    }

    /** Adds D to the back of the IntDList. */
    public void insertBack(int d) {
        DNode insert = new DNode(d);
        DNode p = _back;
        insert._prev = _back;
        if (p != null)
            p._next = insert;
        _back = insert;
        if (_front == null)
            _front = insert; // Your code here
    }

    /** Removes the last item in the IntDList and returns it.
     * This is an extra challenge problem. */
    public int deleteBack() {
        return 0;   // Your code here

    }

    /** Returns a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc. 
     * This is an extra challenge problem. */
    public String toString() {
        return null;   // Your code here
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    protected static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        private DNode(int val) {
            this(null, val, null);
        }

        private DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }
}
