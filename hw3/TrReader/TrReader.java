import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Hong Seok Jang
 */
public class TrReader extends Reader {
    /**
     *  The Reader that I will change.
     */
    private final Reader newReader;
    /**
     *  Strings to change from "from" to "to".
     */
    private final String _from, _to;
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(0) to TO.charAt(0), etc., leaving other characters
     *  unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        newReader = str;
        this._from = from;
        this._to = to;
    }

    /**
     *  Helper function for read.
     *  If char c is not in _from or out of index, return char c
     *  else, return char in _to
     *  @param c char to check whether it is in _from or not
     */
    private char change(char c) {
        if (_from.indexOf(c) == -1) {
            return c;
        } else {
            return _to.charAt(_from.indexOf(c));
        }
    }

    /**
     * Override read(char[], int, int) function in Reader.
     * (credit: I copied from Reader.java)
     * changed part: Change char in cbuf using change function
     * @param cbuf  Destination buffer
     * @param off   Offset at which to start storing characters
     * @param len   Maximum number of characters to read
     * @return The number of characters read
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char[] cbuf, int off, int len) throws IOException {
        int result = newReader.read(cbuf, off, len);
        for (int i = off; i < off + result; i++) {
            cbuf[i] = change(cbuf[i]);
        }
        return result;
    }

    /**
     * Close the reader.
     * @throws IOException If an I/O error occurs
     */
    public void close() throws IOException {
        newReader.close();
    }
}


