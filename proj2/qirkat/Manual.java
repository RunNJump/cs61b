package qirkat;

import static qirkat.PieceColor.*;
import static qirkat.Command.Type.*;

/** A Player that receives its moves from its Game's getMoveCmnd method.
 *  @author Hong Seok Jang
 */
class Manual extends Player {

    /** A Player that will play MYCOLOR on GAME, taking its moves from
     *  GAME. */
    Manual(Game game, PieceColor myColor) {
        super(game, myColor);
        _prompt = myColor + ": ";
    }

    /** Return a legal move for me. Assumes that
     *  board.whoseMove() == myColor and that !board.gameOver(). */
    @Override
    Move myMove() {
        Move myMove = null;
        Command com = this.game().getMoveCmnd(_prompt);
        if (com != null) {
            myMove = Move.parseMove(com.operands()[0]);
        }
        return myMove;
    }

    /** Identifies the player serving as a source of input commands. */
    private String _prompt;
}

