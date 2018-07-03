package qirkat;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Formatter;
import java.util.Observer;
import static qirkat.PieceColor.*;
import static qirkat.Move.*;

/** A Qirkat board.   The squares are labeled by column (a char value between
 *  'a' and 'e') and row (a char value between '1' and '5'.
 *
 *  For some purposes, it is useful to refer to squares using a single
 *  integer, which we call its "linearized index".  This is simply the
 *  number of the square in row-major order (with row 0 being the bottom row)
 *  counting from 0).
 *
 *  Moves on this board are denoted by Moves.
 *  @author Hong Seok Jang
 */
class Board extends Observable {

    /** A new, cleared board at the start of the game. */
    Board() {
        clear();
    }

    /** A copy of B. */
    Board(Board b) {
        internalCopy(b);
    }

    /** Return a constant view of me (allows any access method, but no
     *  method that modifies it). */
    Board constantView() {
        return this.new ConstantBoard();
    }

    /** Clear me to my starting state, with pieces in their initial
     *  positions. */
    void clear() {
        _whoseMove = WHITE;
        _gameOver = false;
        for (int i = 0; i < 10; i++) {
            _boardArray[i] = WHITE;
            _boardArray[_boardArray.length - i - 1] = BLACK;
        }
        for (int i = 10; i < 12; i++) {
            _boardArray[i] = BLACK;
            _boardArray[_boardArray.length - i - 1] = WHITE;
        }
        _boardArray[_boardArray.length / 2] = EMPTY;

        for (int i = 0; i < _boardArray.length; i++) {
            horizontal[i] = 0;
        }
        moveStack.clear();

        setChanged();
        notifyObservers();
    }

    /** Copy B into me. */
    void copy(Board b) {
        internalCopy(b);
    }

    /** Copy B into me. */
    private void internalCopy(Board b) {
        _boardArray = b._boardArray.clone();
        _whoseMove = b.whoseMove();
        _gameOver = b._gameOver;
        horizontal = b.horizontal.clone();
        Move[] stackArray = new Move[b.moveStack.size()];
        for (int i = 0; i < stackArray.length; i++) {
            stackArray[i] = b.moveStack.pop();
        }
        for (int i = stackArray.length - 1; i > -1; i--) {
            moveStack.add(stackArray[i]);
            b.moveStack.add(stackArray[i]);
        }
    }

    /** Set my contents as defined by STR.  STR consists of 25 characters,
     *  each of which is b, w, or -, optionally interspersed with whitespace.
     *  These give the contents of the Board in row-major order, starting
     *  with the bottom row (row 1) and left column (column a). All squares
     *  are initialized to allow horizontal movement in either direction.
     *  NEXTMOVE indicates whose move it is.
     */
    void setPieces(String str, PieceColor nextMove) {
        if (nextMove == EMPTY || nextMove == null) {
            throw new IllegalArgumentException("bad player color");
        }
        str = str.replaceAll("\\s", "");
        if (!str.matches("[bw-]{25}")) {
            throw new IllegalArgumentException("bad board description");
        }
        _whoseMove = nextMove;

        for (int k = 0; k < str.length(); k += 1) {
            switch (str.charAt(k)) {
            case '-':
                set(k, EMPTY);
                break;
            case 'b': case 'B':
                set(k, BLACK);
                break;
            case 'w': case 'W':
                set(k, WHITE);
                break;
            default:
                break;
            }
        }

        for (int i = 0; i < horizontal.length; i++) {
            horizontal[i] = 0;
        }
        moveStack.clear();

        setChanged();
        notifyObservers();
    }

    /** Return true iff the game is over: i.e., if the current player has
     *  no moves. */
    boolean gameOver() {
        return _gameOver;
    }

    /** Return the current contents of square C R, where 'a' <= C <= 'e',
     *  and '1' <= R <= '5'.  */
    PieceColor get(char c, char r) {
        assert validSquare(c, r);
        return get(index(c, r));
    }

    /** Return the current contents of the square at linearized index K. */
    PieceColor get(int k) {
        assert validSquare(k);
        return _boardArray[k];
    }

    /** Set get(C, R) to V, where 'a' <= C <= 'e', and
     *  '1' <= R <= '5'. */
    private void set(char c, char r, PieceColor v) {
        assert validSquare(c, r);
        set(index(c, r), v);
    }

    /** Set get(K) to V, where K is the linearized index of a square. */
    private void set(int k, PieceColor v) {
        assert validSquare(k);
        _boardArray[k] = v;
    }



    /** Return true iff MOV is legal on the current board. */
    boolean legalMove(Move mov) {
        if (mov.isJump()) {
            return checkJump(mov, true);
        }
        return getMoves().contains(mov);
    }


    /** Return a list of all legal moves from the current position. */
    ArrayList<Move> getMoves() {
        ArrayList<Move> result = new ArrayList<>();
        getMoves(result);
        return result;
    }

    /** Add all legal moves from the current position to MOVES. */
    void getMoves(ArrayList<Move> moves) {
        if (jumpPossible()) {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getJumps(moves, k);
            }
        } else {
            for (int k = 0; k <= MAX_INDEX; k += 1) {
                getMoves(moves, k);
            }
        }
    }


    /** Add all legal non-capturing moves from the position
     *  with linearized index K to MOVES. */
    private void getMoves(ArrayList<Move> moves, int k) {
        if (get(k) != EMPTY) {
            if (get(k) == WHITE) {
                if (k < _boardArray.length - 5) {
                    if (get(col(k + 5), row(k + 5)) == EMPTY) {
                        moves.add(move(col(k), row(k), col(k + 5), row(k + 5)));
                    }
                }
                if (k == 0 || k == 6 || k == 12 || k == _boardArray.length - 7
                        || k == 2 || k == 8 || k == 10 || k == 16) {
                    if (get(col(k + 6), row(k + 6)) == EMPTY) {
                        moves.add(move(col(k), row(k), col(k + 6), row(k + 6)));
                    }
                }
                if (k % 2 == 0 && k % 10 != 0 && k < _boardArray.length - 5) {
                    if (get(col(k + 4), row(k + 4)) == EMPTY) {
                        moves.add(move(col(k), row(k), col(k + 4), row(k + 4)));
                    }
                }
                if (k < _boardArray.length - 5) {
                    if ((k + 1) % 5 != 0 && horizontal[k] != -1) {
                        if (get(col(k + 1), row(k + 1)) == EMPTY) {
                            moves.add(move(col(k), row(k), col(k + 1), row(k + 1)));
                        }
                    }
                    if (k % 5 != 0 && horizontal[k] != 1) {
                        if (get(col(k - 1), row(k - 1)) == EMPTY) {
                            moves.add(move(col(k), row(k), col(k - 1), row(k - 1)));
                        }
                    }
                }
            } else {
                if (k > 4) {
                    if (get(col(k - 5), row(k - 5)) == EMPTY) {
                        moves.add(move(col(k), row(k), col(k - 5), row(k - 5)));
                    }
                }
                if (k == 6 || k == 12 || k == _boardArray.length - 7
                        || k == _boardArray.length - 1 || k == 8
                        || k == 14 || k == 16 || k == _boardArray.length - 3) {
                    if (get(col(k - 6), row(k - 6)) == EMPTY) {
                        moves.add(move(col(k), row(k), col(k - 6), row(k - 6)));
                    }
                }
                if (k % 2 == 0 && k > 4 && k < _boardArray.length - 1
                        && k != _boardArray.length - 11) {
                    if (get(col(k - 4), row(k - 4)) == EMPTY) {
                        moves.add(move(col(k), row(k), col(k - 4), row(k - 4)));
                    }
                }
                if (k > 4) {
                    if ((k + 1) % 5 != 0 && horizontal[k] != -1) {
                        if (get(col(k + 1), row(k + 1)) == EMPTY) {
                            moves.add(move(col(k), row(k), col(k + 1), row(k + 1)));
                        }
                    }
                    if (k % 5 != 0 && horizontal[k] != 1) {
                        if (get(col(k - 1), row(k - 1)) == EMPTY) {
                            moves.add(move(col(k), row(k), col(k - 1), row(k - 1)));
                        }
                    }
                }
            }
        }
    }

    /** Add all legal captures from the position with linearized index K
     *  to MOVES. */
    private void getJumps(ArrayList<Move> moves, int k) {
        if (get(k) != EMPTY) {
            if (k < _boardArray.length - 10) {
                if (get(k + 10) == EMPTY
                        && get(k + 5) == _whoseMove.opposite()
                        && get(k + 5) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k + 10), row(k + 10)));
                }
            }
            if (k > _boardArray.length - 16) {
                if (get(k - 10) == EMPTY
                        && get(k - 5) == _whoseMove.opposite()
                        && get(k - 5) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k - 10), row(k - 10)));
                }
            }
            if (k % 5 == 0 || k % 5 == 1 || k % 5 == 2) {
                if (get(k + 2) == EMPTY
                        && get(k + 1) == _whoseMove.opposite()
                        && get(k + 1) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k + 2), row(k + 2)));
                }
            }
            if (k % 5 == 2 || k % 5 == 3 || k % 5 == 4) {
                if (get(k - 2) == EMPTY
                        && get(k - 1) == _whoseMove.opposite()
                        && get(k - 1) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k - 2), row(k - 2)));
                }
            }
            if (k == 0 || k == 6 || k == 12 || k == 2 || k == 10) {
                if (get(k + 12) == EMPTY
                        && get(k + 6) == _whoseMove.opposite()
                        && get(k + 6) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k + 12), row(k + 12)));
                }
            }
            if (k == 2 || k == 4 || k == 8 || k == 12 || k == 14) {
                if (get(k + 8) == EMPTY
                        && get(k + 4) == _whoseMove.opposite()
                        && get(k + 4) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k + 8), row(k + 8)));
                }
            }
            if (k == 12 || k == _boardArray.length - 7
                    || k == _boardArray.length - 1
                    || k == 14 || k == _boardArray.length - 3) {
                if (get(k - 12) == EMPTY
                        && get(k - 6) == _whoseMove.opposite()
                        && get(k - 6) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k - 12), row(k - 12)));
                }
            }
            if (k == 10 || k == 12 || k == 16
                    || k == _boardArray.length - 5
                    || k == _boardArray.length - 3) {
                if (get(k - 8) == EMPTY
                        && get(k - 4) == _whoseMove.opposite()
                        && get(k - 4) == get(k).opposite()) {
                    moves.add(move(col(k), row(k), col(k - 8), row(k - 8)));
                }
            }
        }
    }

    /** Return true iff MOV is a valid jump sequence on the current board.
     *  MOV must be a jump or null.  If ALLOWPARTIAL, allow jumps that
     *  could be continued and are valid as far as they go.  */
    boolean checkJump(Move mov, boolean allowPartial) {
        if (mov == null) {
            return true;
        }
        if (!(jumpPossible(mov.col0(), mov.row0()))) {
            return false;
        }
        if (!allowPartial) {
            return checkJump(mov.jumpTail(), false);
        }
        return true;
    }

    /** Return true iff a jump is possible for a piece at position C R. */
    boolean jumpPossible(char c, char r) {
        return jumpPossible(index(c, r));
    }

    /** Return true iff a jump is possible for a piece at position with
     *  linearized index K. */
    boolean jumpPossible(int k) {
        if (get(k) != EMPTY) {
            if (k < _boardArray.length - 10) {
                if (get(k + 10) == EMPTY
                        && get(k + 5) == _whoseMove.opposite()
                        && get(k + 5) == get(k).opposite()) {
                    return true;
                }
            }
            if (k > _boardArray.length - 16) {
                if (get(k - 10) == EMPTY
                        && get(k - 5) == _whoseMove.opposite()
                        && get(k - 5) == get(k).opposite()) {
                    return true;
                }
            }
            if (k % 5 == 0 || k % 5 == 1 || k % 5 == 2) {
                if (get(k + 2) == EMPTY
                        && get(k + 1) == _whoseMove.opposite()
                        && get(k + 1) == get(k).opposite()) {
                    return true;
                }
            }
            if (k % 5 == 2 || k % 5 == 3 || k % 5 == 4) {
                if (get(k - 2) == EMPTY
                        && get(k - 1) == _whoseMove.opposite()
                        && get(k - 1) == get(k).opposite()) {
                    return true;
                }
            }
            if (k == 0 || k == 6 || k == 12 || k == 2 || k == 10) {
                if (get(k + 12) == EMPTY
                        && get(k + 6) == _whoseMove.opposite()
                        && get(k + 6) == get(k).opposite()) {
                    return true;
                }
            }
            if (k == 2 || k == 4 || k == 8 || k == 12 || k == 14) {
                if (get(k + 8) == EMPTY
                        && get(k + 4) == _whoseMove.opposite()
                        && get(k + 4) == get(k).opposite()) {
                    return true;
                }
            }
            if (k == 12 || k == _boardArray.length - 7
                    || k == _boardArray.length - 1
                    || k == 14 || k == _boardArray.length - 3) {
                if (get(k - 12) == EMPTY
                        && get(k - 6) == _whoseMove.opposite()
                        && get(k - 6) == get(k).opposite()) {
                    return true;
                }
            }
            if (k == 10 || k == 12 || k == 16
                    || k == _boardArray.length - 5
                    || k == _boardArray.length - 3) {
                if (get(k - 8) == EMPTY
                        && get(k - 4) == _whoseMove.opposite()
                        && get(k - 4) == get(k).opposite()) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Return true iff a jump is possible from the current board. */
    boolean jumpPossible() {
        for (int k = 0; k <= MAX_INDEX; k += 1) {
            if (jumpPossible(k)) {
                return true;
            }
        }
        return false;
    }

    /** Return the color of the player who has the next move.  The
     *  value is arbitrary if gameOver(). */
    PieceColor whoseMove() {
        return _whoseMove;
    }

    /** Perform the move C0R0-C1R1. Assumes that legalMove(C0, R0, C1, R1). */
    void makeMove(char c0, char r0, char c1, char r1) {
        makeMove(Move.move(c0, r0, c1, r1, null));
    }

    /** Make the multi-jump C0 R0-C1 R1..., where NEXT is C1R1....
     *  Assumes the result is legal. */
    void makeMove(char c0, char r0, char c1, char r1, Move next) {
        makeMove(Move.move(c0, r0, c1, r1, next));
    }

    /** Make the Move MOV on this Board, assuming it is legal. */
    void makeMove(Move mov) {
        assert legalMove(mov);
        moveStack.add(mov);
        if (mov.isJump()) {
            while (mov.jumpTail() != null) {
                set(mov.jumpedIndex(), EMPTY);
                set(mov.toIndex(), get(mov.fromIndex()));
                set(mov.fromIndex(), EMPTY);
                mov = mov.jumpTail();
                horizontal[index(mov.col1(), mov.row1())] = 0;
            }
            horizontal[index(mov.col1(), mov.row1())] = 0;
            set(mov.jumpedIndex(), EMPTY);

        }
        set(mov.toIndex(), get(mov.fromIndex()));
        set(mov.fromIndex(), EMPTY);

        if (mov.isLeftMove()) {
            horizontal[index(mov.col1(), mov.row1())] = -1;
        }
        if (mov.isRightMove()) {
            horizontal[index(mov.col1(), mov.row1())] = 1;
        }

        if (!isMove()) {
            gameOver();
        }
        _whoseMove = _whoseMove.opposite();
        setChanged();
        notifyObservers();
    }

    /** Undo the last move, if any. */
    void undo() {
        _whoseMove = _whoseMove.opposite();
        Move lastMove = moveStack.peek();
        if (lastMove.isJump()) {
            while (lastMove != null) {
                set(lastMove.jumpedIndex(), whoseMove().opposite());
                set(lastMove.fromIndex(), whoseMove());
                set(lastMove.toIndex(), EMPTY);
                lastMove = lastMove.jumpTail();
            }
        } else {
            set(lastMove.fromIndex(), whoseMove());
            set(lastMove.toIndex(), EMPTY);
        }
        moveStack.pop();


        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /** Return a text depiction of the board.  If LEGEND, supply row and
     *  column numbers around the edges. */
    String toString(boolean legend) {
        Formatter out = new Formatter();
        StringBuilder sb = new StringBuilder();
        for (int i = 4; i > -1; i--) {
            for (int j = 0; j < 5; j++) {
                if (j == 0) {
                    if (legend) {
                        sb.append(i + 1);
                    }
                    sb.append("  ");
                }
                sb.append(_boardArray[i * 5 + j].shortName());
                if (j == 4) {
                    if (i != 0) {
                        sb.append("\n");
                    }
                } else {
                    sb.append(" ");
                }
            }
        }

        if (legend) {
            sb.append("\n   a b c d e");
        }

        out.format("%s", sb);
        return out.toString();
    }

    /** Return true iff there is a move for the current player. */
    private boolean isMove() {
        if (getMoves().isEmpty() && !jumpPossible()) {
            return false;
        }
        return true;
    }

    /**
     * Array to store value.
     */
    private PieceColor[] _boardArray = new PieceColor[Move.SIDE * Move.SIDE];

    /** Player that is on move. */
    private PieceColor _whoseMove;

    /** Set true when game ends. */
    private boolean _gameOver;

    /** Convenience value giving values of pieces at each ordinal position. */
    static final PieceColor[] PIECE_VALUES = PieceColor.values();

    /** One cannot create arrays of ArrayList<Move>, so we introduce
     *  a specialized private list type for this purpose. */
    private static class MoveList extends ArrayList<Move> {
    }

    /** A read-only view of a Board. */
    private class ConstantBoard extends Board implements Observer {
        /** A constant view of this Board. */
        ConstantBoard() {
            super(Board.this);
            Board.this.addObserver(this);
        }

        @Override
        void copy(Board b) {
            assert false;
        }

        @Override
        void clear() {
            assert false;
        }

        @Override
        void makeMove(Move move) {
            assert false;
        }

        /** Undo the last move. */
        @Override
        void undo() {
            assert false;
        }

        @Override
        public void update(Observable obs, Object arg) {
            super.copy((Board) obs);
            setChanged();
            notifyObservers(arg);
        }
    }

    /**
     * Array to represent horizontal move
     * If piece is from left move, I set it as 1.
     * If peice is from right move, I set it as -1. Otherwise I set it as 0.
     */
    private int[] horizontal = new int[_boardArray.length];

    /** moveStack of all moves. */
    private Stack<Move> moveStack = new Stack<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Board) {
            Board b = (Board) o;
            return (b.toString().equals(toString())
                    && _whoseMove == b.whoseMove());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
