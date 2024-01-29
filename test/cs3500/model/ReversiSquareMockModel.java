package cs3500.model;


/**
 * A mock model for reversi, does everything that the original model does,
 * except that the client doesn't have any restrictions on where to put the disc
 * use for testing.
 */
public class ReversiSquareMockModel extends SquareReversi implements MutableModel<Square> {
  private StringBuilder log;

  /**
   * Constructor for the mock model,
   * takes in a string builder to log down all the moves that a player makes.
   *
   * @param log string builder
   */
  public ReversiSquareMockModel(StringBuilder log) {
    super();
    this.log = log;
  }

  @Override
  public void putDisc(Square square) throws IllegalStateException, IllegalArgumentException {

    if (getLegalMoves(currentPlayerPiece).isEmpty()) {
      pass();
      return;
    }

    boolean moveMade = false;
    while (!moveMade) {
      // if there is already player there -> dont put
      if (getPlayerAt(square) == PlayerPiece.EMPTY) {
        // dont make a move here
        quadramap.put(square, currentPlayerPiece);

        log.append(String.format("Player " + currentPlayerPiece + " chose square at: %d, %d\n",
                square.getCoord("x"), square.getCoord("y")));
      }
      // pass the turn to the next player
      currentPlayerPiece = getNextPlayer(); // updated
      moveMade = true;
    }

    // switch to the next player
    // forcefully passes also this is the next player
    // if black has any legal moves
    if (getLegalMoves(currentPlayerPiece).isEmpty()) {
      pass();
    }
  }
}
