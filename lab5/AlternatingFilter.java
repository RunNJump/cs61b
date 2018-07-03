import java.util.Iterator;
import utils.Filter;

/** A kind of Filter that lets through every other VALUE element of
 *  its input sequence, starting with the first.
 *  @author Hong Seok Jang
 */
class AlternatingFilter<Value> extends Filter<Value> {

    /** A filter of values from INPUT that lets through every other
     *  value. */
    AlternatingFilter(Iterator<Value> input) {
        super(input);
    }

    @Override
    protected boolean keep() {
        temp = !temp;
        return temp;
    }
    /** Make a temp boolean function to get every other.
     */
    private boolean temp = false;
}
