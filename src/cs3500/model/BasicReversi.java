package cs3500.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.controller.ModelStatusListener;

/**
 * Basic Reversi is a traditional game of Reversi played on a board of hexagons.
 * Each disc that is played on the Reversi board will be placed on a hexagon of this board.
 * Class invariant: The radius is always greater than 1. This is upheld by the constructor
 * as well as the methods (that never mutate the radius). In addition, game would be already
 * over if 6/7 hexagons were already filled at the started.
 */
public class BasicReversi extends ROReversi implements MutableModel<Hexagon>  {
  /**
   * Constructor of Basic reversi.
   * Initialize startGame as false.
   * Instantiate hexmap that keeps track of a hexagon mapped to a player
   * Initialize pass statuses for both players as false
   */

  private final List<ModelStatusListener> listeners;

  /**
   * Constructs a basic reversi such that it has listeners that listen to the events, such
   * as the state of the game or if certain moves were made.
   */
  public BasicReversi() {
    super();
    this.listeners = new ArrayList<>();
  }

  @Override
  public Hexagon[][] makeBoard(int radius) throws IllegalArgumentException {
    // Game would be already over if 6/7 hexagons were already filled at the started.
    if (radius < 2) {
      throw new IllegalArgumentException("Size can't be less than 2");
    }
    this.radius = radius;
    int diameter = 2 * radius + 1;
    Hexagon[][] board = new Hexagon[diameter][];
    for (int q = 0; q < diameter; q++) {
      board[q] = new Hexagon[diameter];
    }
    // setting up the board with empties
    for (int q = 0; q < diameter; q++) {
      for (int r = 0; r < diameter; r++) {
        int hexQ = q - radius;
        int hexR = r - radius;
        int hexS = -hexQ - hexR;
        if (hexQ + hexR + hexS == 0 && hexS <= radius && hexS >= -radius) {
          Hexagon hexagon = new Hexagon(hexQ, hexR, hexS);
          board[q][r] = hexagon;
          hexamap.put(hexagon, PlayerPiece.EMPTY);
        } else {
          board[q][r] = null;
        }
      }
    }
    return board;
  }

  @Override
  public void startGame(Hexagon[][] board) throws IllegalStateException, IllegalArgumentException {
    if (gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    if (board == null) {
      throw new IllegalArgumentException("Board is null");
    }
    this.gameStarted = true;
    this.currentPlayerPiece = PlayerPiece.X;
    // if the hexamap contains any that are Player.X or Player.O
    // -> throw IAE
    int numX = getPlayerScore(PlayerPiece.X);
    int numO = getPlayerScore(PlayerPiece.O);

    if (numX != 0 || numO != 0) {
      throw new IllegalArgumentException("Starting board needs to be empty!");
    }
    Hexagon midHex = new Hexagon(0, 0, 0);
    List<Hexagon> neighbors = midHex.getNeighbors();
    // even -> put player X
    for (int n = 0; n < 6; n++) {
      Hexagon hex = neighbors.get(n);
      if (n % 2 == 0) {
        this.hexamap.put(hex, PlayerPiece.X);
      } else {
        this.hexamap.put(hex, PlayerPiece.O);
      }
    }
    this.board = board;
    this.oPassed = false;
    this.xPassed = false;
    notifyPlayersTurn();
  }

  @Override
  public void putDisc(int q, int r) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    validateHexagon(q, r);
    // cant really do anything
    // if white has no legal moves -> forcefully pass to black
    if (getLegalMoves(currentPlayerPiece).isEmpty()) {
      notifyNoMoves();
      pass();
      return;
    }
    updatePass(false);
    boolean moveMade = false;
    while (!moveMade) {
      Hexagon chosenHex = getPShapeAt(q, r);
      // can't put the disc there!
      if (!isLegalMove(chosenHex, currentPlayerPiece)) {
        throw new IllegalArgumentException("You cannot put your disc there!");
      } else {
        hexamap.put(chosenHex, currentPlayerPiece);
        // flip sandwiched discs here
        for (Hexagon hex : getDiscsToFlip(q, r)) {
          hexamap.put(hex, currentPlayerPiece);
        }
        // pass the turn to the next player
        currentPlayerPiece = getNextPlayer(); // updated
        moveMade = true;
      }
    }

    // switch to the next player
    // forcefully passes also this is the next player
    // if black has any legal moves
    if (getLegalMoves(currentPlayerPiece).isEmpty()) {
      notifyNoMoves();
      pass();
    }
    notifyPlayersTurn();

    if (!isGameContinuing()) {
      notifyIsGameOver();
    }
  }

  @Override
  public void putDisc(Hexagon hexagon) throws IllegalStateException, IllegalArgumentException {
    int q = hexagon.getCoord("q") + this.getSize();
    int r = hexagon.getCoord("r") + this.getSize();
    putDisc(q, r);
  }

  /**
   * Update the passing status of the current player to the given state.
   *
   * @param state passing status of the player we are changing to.
   */
  protected void updatePass(Boolean state) {
    if (currentPlayerPiece == PlayerPiece.X) {
      xPassed = state;
      return;
    }
    if (currentPlayerPiece == PlayerPiece.O) {
      oPassed = state;
    }
  }

  @Override
  public void pass() throws IllegalStateException {
    gameHasntStarted();
    updatePass(true);
    this.currentPlayerPiece = this.getNextPlayer();
    // make it so that the previous current player can't move!
    if (isGameContinuing()) {
      notifyPlayersTurn();
    } else {
      notifyIsGameOver();
    }
  }

  @Override
  public void notifyPlayersTurn() {
    for (ModelStatusListener listener : listeners) {
      listener.onPlayersTurn();
    }
  }

  @Override
  public void notifyIsGameOver() {
    for (ModelStatusListener listener : listeners) {
      listener.onGameOver();
    }
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    listeners.add(listener);
  }

  @Override
  public void notifyNoMoves() {
    for (ModelStatusListener listener : listeners) {
      listener.onNoLegalMoves();
    }
  }
}




