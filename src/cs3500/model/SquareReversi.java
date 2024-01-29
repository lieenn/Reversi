package cs3500.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.controller.ModelStatusListener;


/**
 * The m
 */
public class SquareReversi implements MutableModel<Square> {
  protected final Map<Square, PlayerPiece> quadramap;
  protected boolean gameStarted;
  protected PlayerPiece currentPlayerPiece;
  protected int size;
  protected Square[][] board;
  protected boolean xPassed;
  protected boolean oPassed;

  private final List<ModelStatusListener> listeners;

  /**
   * Constructor of ROSquare reversi.
   * Initialize startGame as false.
   * Instantiate hexmap that keeps track of a quadramap mapped to a player
   * Initialize pass statuses for both players as false
   */

  public SquareReversi() {
    this.gameStarted = false;
    quadramap = new HashMap<>();
    xPassed = false;
    oPassed = false;

    this.listeners = new ArrayList<>();
  }


  /**
   * For the other public methods, if the game hasn't started yet, throw IllegalStateException.
   */

  protected void gameHasntStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Have you started the game yet?");
    }
  }

  /**
   * Checks whether the given coordinate has a valid square
   *
   * @param q q coordinate of the board
   * @param r r coordinate of the board
   * @return if its a valid square
   * @throws IllegalArgumentException if it is not on the board.
   */
  protected boolean isValidSquare(int q, int r) throws IllegalArgumentException {
    if (q < 0 || r < 0 || q > size - 1 || r > size - 1) {
      return false;
    }
    if (this.board[q][r] != null) {
      return true;
    }
    else {
      return true;
    }
  }



  /**
   * Create a 2D board game for Reversi.
   * Add square with cube coordinates to the hexamap that keep track of the players on the square.
   * Set each square to have empty player by default, which means a blank boardGame with no disc
   * on the board.
   *
   * @param size size of the board, radius of the giant squareal board
   * @return a 2D board of squares, board[col][row] returns null if there is no square inside
   * @throws IllegalArgumentException if the given radius is less than 1
   */
  @Override
  public Square[][] makeBoard(int size) {
    // if size if 1, it's just gonna be 1 square
    if (size <= 2 || size % 2 != 0) {
      throw new IllegalArgumentException("Can't make a board with that radius!");
    }
    this.size = size;
    Square[][] board = new Square[size][];

    for (int q = 0; q < size; q++) {
      board[q] = new Square[size];
    }
    // top-left is 0,0
    // setting up the board with empties
    for (int q = 0; q < size; q++) {
      for (int r = 0; r < size; r++) {
          Square square = new Square(q, r);
          board[q][r] = square;
          quadramap.put(square, PlayerPiece.EMPTY);
      }
    }
    return board;
  }



  /**
   * Sets up the board with 6 pieces around the middle; center square would be left empty.
   * It would first get the neighbors of the center square as a list. This list of
   * neighbors would be where the starting discs will be placed at.
   * Here are the neighbors of the middle:
   * Player O: NE, W, SE -> 1, 3, 5 (odd indices)
   * Player X: E, NW, SW  -> 0, 2, 4 (even indices).
   *
   * @param board takes in a blank board
   * @throws IllegalStateException    if game has already started
   * @throws IllegalArgumentException if the board is null
   * @throws IllegalArgumentException if the board is not emptied prior to beginning the game.
   */
  @Override
  public void startGame(Square[][] board) {
    if (gameStarted) {
      throw new IllegalStateException("Game already started");
    }
    if (board == null) {
      throw new IllegalArgumentException("Board is null");
    }

    int sizeMinus1 = size - 1;
    this.gameStarted = true;
    this.currentPlayerPiece = PlayerPiece.X;
    // if the hexamap contains any that are Player.X or Player.O
    // -> throw IAE
    int numX = getPlayerScore(PlayerPiece.X);
    int numO = getPlayerScore(PlayerPiece.O);

    if (numX != 0 || numO != 0) {
      throw new IllegalArgumentException("Starting board needs to be empty!");
    }

    Square middleTL = new Square(sizeMinus1 / 2, sizeMinus1 / 2);
    Square middleTR = new Square((sizeMinus1 / 2) + 1, sizeMinus1 / 2);
    Square middleBL = new Square(sizeMinus1 / 2, (sizeMinus1 / 2) + 1);
    Square middleBR = new Square((sizeMinus1 / 2) + 1, (sizeMinus1 / 2) + 1);

    this.quadramap.put(middleTL, PlayerPiece.X);
    this.quadramap.put(middleTR, PlayerPiece.O);
    this.quadramap.put(middleBL, PlayerPiece.O);
    this.quadramap.put(middleBR, PlayerPiece.X);


    // even -> put player X

    this.board = board;
    this.oPassed = false;
    this.xPassed = false;
    notifyPlayersTurn();

  }

  /**
   * Puts the disc at the given location and passes the turn to the next player.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if square's coordinates are not valid
   */
  @Override
  public void putDisc(int q, int r) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (!isValidSquare(q, r)) {
      throw new IllegalArgumentException("Not a valid coordinate");
    }

    if (getLegalMoves(currentPlayerPiece).isEmpty()) {
      notifyNoMoves();
      pass();
      return;
    }

    updatePass(false);
    boolean moveMade = false;
    while (!moveMade) {
      Square chosenSquare = getPShapeAt(q, r);
      // can't put the disc there!
      if (!isLegalMove(chosenSquare, currentPlayerPiece)) {
        throw new IllegalArgumentException("You cannot put your disc there!");
      } else {
        quadramap.put(chosenSquare, currentPlayerPiece);
        // flip sandwiched discs here
        for (Square square : getDiscsToFlip(q, r)) {
          quadramap.put(square, currentPlayerPiece);
        }
        // pass the turn to the next player
        currentPlayerPiece = getNextPlayer(); // updated
        moveMade = true;
      }
    }

    if (getLegalMoves(currentPlayerPiece).isEmpty()) {
      notifyNoMoves();
      pass();
    }
    notifyPlayersTurn();

    if (!isGameContinuing()) {
      notifyIsGameOver();
    }
  }

  /**
   * Puts the disc at the given square ont he board and passes the turn to the next player.
   *
   * @param square square that the client wants to place their disc down
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if square's coordinates are not valid
   */
  @Override
  public void putDisc(Square square) throws IllegalStateException, IllegalArgumentException {
    int x = square.getX();
    int y = square.getY();
    putDisc(x, y);

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

  /**
   * Passes turn to the next player, update passing status of current player.
   *
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public void pass() {
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







  /**
   * We leave this as public since in the future we will implement a view such that
   * players can look at the possible moves that they can play their disc at. The reason
   * why that currentPlayer is a param is because there will be two boards being played
   * at the same time, and we want to show both valid moves for each player at the same time.
   * This list is made of all of the PShapes in which a player can play at.
   *
   * @param playerPiece is the player whose legal moves we want.
   * @return the list of PShapes where a player can possibly play their disc.
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the player that gets passed in is not Player.X or Player.O
   */
  @Override
  public List<Square> getLegalMoves(PlayerPiece playerPiece) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (playerPiece != PlayerPiece.X && playerPiece != PlayerPiece.O) {
      throw new IllegalArgumentException("Not a real player");
    }
    List<Square> legalMoves = new ArrayList<>();
    //get all of the empty hexagons in the board
    for (Square square : quadramap.keySet()) {
      if (quadramap.get(square) == PlayerPiece.EMPTY)
        // get out the ones with empties
        if (isLegalMove(square, playerPiece)) {
          legalMoves.add(square);
        }
    }
    return legalMoves;
  }

  /**
   * Get the player at the given board coordinates.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @return The player on the PShape of the board's cell
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the board's coordinates are not valid
   */
  @Override
  public PlayerPiece getPlayerAt(int q, int r) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (!isValidSquare(q, r)) {
      throw new IllegalArgumentException("Not a valid hexagon");
    }
    Square square = getPShapeAt(q, r);
    return quadramap.get(square);
  }

  /**
   * Get the player at the given PShape on the board.
   *
   * @param square given PShape with cube coordinates
   * @return model.getPlayerAt(boardQ, boardR)
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the PShape's coordinates are not valid
   */
  @Override
  public PlayerPiece getPlayerAt(Square square) throws IllegalStateException, IllegalArgumentException {
    int x = square.getX();
    int y = square.getY();
    return getPlayerAt(x, y);
  }

  /**
   * Returns the PShape at the given board coordinates.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @return The PShape on the board's cell.
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if PShape's coordinates are not valid
   */
  @Override
  public Square getPShapeAt(int q, int r) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (!isValidSquare(q, r)) {
      throw new IllegalArgumentException("Not a valid hexagon");
    }
    Square square = board[q][r];
    return new Square(square.getX(), square.getY());
  }

  /**
   * Checks if the move is a legal move for that player.
   *
   * @param square         the coordinate of the PShape on the board.
   * @param playerPiece the player that wants to check their legal move
   * @return if the move is a legal move for that player.
   */
  @Override
  public boolean isLegalMove(Square square, PlayerPiece playerPiece) {
    gameHasntStarted();
    if (quadramap.get(square) == null) {
      throw new IllegalArgumentException("Square does not exist!");
    }
    List<Square> neighbors = square.getNeighbors();
    //List<Hexagon> otherPlayerHex = new ArrayList<>();
    for (Square neighbor : neighbors) {
      if (quadramap.get(neighbor) == getNextPlayer()) {
        // otherPlayerHex.add(neighbor);
        // gets the directions of all 3 coordinates
        int directionX = neighbor.getX() - square.getX();
        int directionY = neighbor.getY() - square.getY();
        Square direction = new Square(directionX, directionY);
        //neighbor of the neighbor
        Square nextSquare = square.add(direction);
        while (quadramap.get(nextSquare) == getNextPlayer()) {
          Square neighborSquare = nextSquare.add(direction);
          if (quadramap.get(neighborSquare) == getNextPlayer()) {
            nextSquare = neighborSquare;
            continue;
          } else if (quadramap.get(neighborSquare) == getCurrentPlayer()) {
            return true;
          }
          break;
        }
      }
    }
    // empty or end of the board -> false
    return false;
  }

  /**
   * Gets how many discs that the player has put down and flipped over. If
   * Player is Player.Empty, getNumPlayer counts how many PShapes do not have a player's disc
   * on that PShape.
   *
   * @param playerPiece is a player, either Player.X, Player.O, or Player.EMPTY.
   * @return how many discs covered by a player or how many discs are EMPTY.
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the given player is null
   */
  @Override
  public int getPlayerScore(PlayerPiece playerPiece) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (playerPiece == null) {
      throw new IllegalArgumentException("Can't be null");
    }
    // count how many key in the hashmap has given as value
    Set<Square> keys = quadramap.keySet();
    int count = 0;
    for (Square key : keys) {
      if (playerPiece == quadramap.get(key)) {
        count += 1;
      }
    }
    return count;
  }

  /**
   * Returns the Next Player of the game.
   *
   * @return the next player.
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public PlayerPiece getNextPlayer() throws IllegalStateException {
    gameHasntStarted();
    if (currentPlayerPiece == PlayerPiece.X) {
      return PlayerPiece.O;
    } else {
      return PlayerPiece.X;
    }
  }

  /**
   * Returns the Current Player of the game.
   *
   * @return the current player.
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public PlayerPiece getCurrentPlayer() throws IllegalStateException {
    gameHasntStarted();
    return this.currentPlayerPiece;
  }

  /**
   * Returns true if player has no moves.
   *
   * @param playerPiece that you are checking for no moves.
   * @return if player has no moves.
   * @throws IllegalArgumentException if you give it an empty player
   */
  protected boolean playerHasNoMoves(PlayerPiece playerPiece) throws IllegalArgumentException {
    if (playerPiece.equals(PlayerPiece.X)) {
      return getLegalMoves(PlayerPiece.X).isEmpty();
    } else if (playerPiece.equals(PlayerPiece.O)) {
      return getLegalMoves(PlayerPiece.O).isEmpty();
    } else {
      throw new IllegalArgumentException("Not a player");
    }
  }

  /**
   * Checks if the game is over. The game is over if both players have passed
   *
   * @return true if the game over
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if the game hasn't ended yet
   */
  @Override
  public boolean isGameContinuing() throws IllegalStateException {
    gameHasntStarted();

    // there's a player that still has moves
    return !((playerHasNoMoves(PlayerPiece.O) && playerHasNoMoves(PlayerPiece.X))
            // both have not passed -> still continues
            || (xPassed && oPassed));
  }


  /**
   * If there is a stalemate at the end of the game.
   *
   * @return true if there is a stalemate.
   * @throws IllegalStateException if there is no winner yet.
   */
  protected boolean isStalemate() throws IllegalStateException {
    if (isGameContinuing()) {
      throw new IllegalStateException("No winner yet!");
    }

    return (getPlayerScore(PlayerPiece.O) == getPlayerScore(PlayerPiece.X));
  }

  /**
   * Winner of the game. This method is only called if and only if there is a winner in the game.
   * The Player with the most pieces in the game is the winner if the game has ended.
   *
   * @return the winner of the game
   * @throws IllegalStateException if there is a stalemate or a tie
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if the game hasn't ended yet
   */
  @Override
  public PlayerPiece getWinner() throws IllegalStateException {
    gameHasntStarted();
    if (isGameContinuing()) {
      throw new IllegalStateException("No winner yet!");
    }
    //return PlayerO if o > x, else return PlayerX
    if (isStalemate()) {
      throw new IllegalStateException("No winner here");
    } else {
      return getPlayerScore(PlayerPiece.O) >
              getPlayerScore(PlayerPiece.X) ? PlayerPiece.O : PlayerPiece.X;
    }
  }

  /**
   * Get the diameter of the board in this game in number of PShapes.
   *
   * @return diameter of the board
   */
  @Override
  public int getSizeTimesTwoPlusOne() {
    return size * 2 + 1;
  }

  /**
   * Get the radius if the board in this game in number of PShapes.
   *
   * @return radius of the board
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Gets the board at the game's current state.
   *
   * @return board at the game's current state
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  public Square[][] getBoard() {
    gameHasntStarted();
    Square[][] copiedBoard = new Square[size][];

    // Iterate through the existing board to create a deep copy
    for (int q = 0; q < size; q++) {
      copiedBoard[q] = new Square[size];
      for (int r = 0; r < size; r++) {
        if (board[q][r] != null) {
          int x = board[q][r].getX();
          int y = board[q][r].getY();
          copiedBoard[q][r] = new Square(x, y);
          // If needed, copy other properties or objects within Hexagon
        } else {
          copiedBoard[q][r] = null; // Retain the null elements in the column
        }
      }
    }
    return copiedBoard;
  }

  /**
   * Check if it is the given Player's turn.
   *
   * @param playerPiece the given player
   * @return true if it is the given player's turn
   * @throws IllegalStateException    If the has not started
   * @throws IllegalArgumentException If the player is neither X nor O
   */
  @Override
  public boolean isPlayersTurn(PlayerPiece playerPiece) throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (playerPiece == PlayerPiece.EMPTY) {
      throw new IllegalArgumentException("Not a player!");
    }
    if (playerPiece == PlayerPiece.X && currentPlayerPiece != PlayerPiece.X) {
      return false;
    } else {
      return playerPiece != PlayerPiece.O || currentPlayerPiece == PlayerPiece.O;
    }
  }

  /**
   * The discs that flip when you put disc on the axial coordinate spot (q, r).
   *
   * @param q where you would potentially put your disc at coordinate q.
   * @param r where you would potentially put your disc at coordinate r.
   * @return The discs that flip when you put disc on the axial coordinate spot (q, r).
   */
  @Override
  public List<Square> getDiscsToFlip(int q, int r) {
    gameHasntStarted();
    Square chosenSquare = getPShapeAt(q, r);
    if (!isLegalMove(chosenSquare, currentPlayerPiece)) {
      throw new IllegalArgumentException("Can't find any sandwiches :(");
    }
    List<Square> neighbors = chosenSquare.getNeighbors();
    List<Square> toFlip = new ArrayList<>();
    //iterate through all 6 neighbors
    for (Square neighbor : neighbors) {
      //if neighbor is not opposite color, skip
      if (quadramap.get(neighbor) != getNextPlayer()) {
        continue;
      }
      //directional vector hexagon
      Square direction = new Square(neighbor.getX() - chosenSquare.getX(),
              neighbor.getY() - chosenSquare.getY());
      //set the first peek to be the neighbor

      //buffer will be our temporary list of sandwiched hexagons
      toFlip.addAll(toBeFlipped(neighbor, direction));
    }
    //finally return the comprehensive list of all stones to flip when tile q, r is played
    //by player
    return toFlip;
  }

  /**
   * A helper method for getDiscToFlip.
   * Iterate down the given direction until it hits a hexagon that doesn't belong to the nextPlayer
   * If it hits the currentPlayer hexagon, it would return a list of hexagons that it passed through
   * Otherwise, it would return an empty list.
   *
   * @param peek      given neighbor
   * @param direction the current direction that we're iterating toward
   * @return A list of nextPlayer's hexagons if currentPlayer's hexagon is at the end
   */
  protected List<Square> toBeFlipped(Square peek, Square direction) {
    List<Square> maybeFlipped = new ArrayList<>();
    // adds the neighbor to the maybeFlipped
    maybeFlipped.add(peek);
    // while the neighbors (starting with the initial neighbor) is opposite color...
    while (quadramap.get(peek) == getNextPlayer()) {
      //move it forward in the same direction
      peek = peek.add(direction);
      //if it is still opposite color then add the peek to maybeFlipped and continue
      if (quadramap.get(peek) == getNextPlayer()) {
        maybeFlipped.add(peek);
        //otherwise if it's the actual player then we know we have the full sandwich, so
        //add it to the return list(toFlip) and break
      } else if (quadramap.get(peek) == currentPlayerPiece) {
        return maybeFlipped;
      }
      //this will only execute if the peek is Player.EMPTY, in which case, clear the maybeFlipped
      //and move on to the next neighbor direction
    }
    return new ArrayList<>();
  }


  @Override
  public Map<Square, PlayerPiece> getHexamap() {
    Map<Square, PlayerPiece> deepCopyMap = new HashMap<>();

    for (Map.Entry<Square, PlayerPiece> entry : quadramap.entrySet()) {
      Square ogHex = entry.getKey();
      PlayerPiece ogDisc = entry.getValue();

      Square copyHex = copyOfSquare(ogHex);
      deepCopyMap.put(copyHex, ogDisc);
    }
    return deepCopyMap;
  }

  private Square copyOfSquare(Square ogHex) {
    return new Square(ogHex.getX(), ogHex.getY());
  }


}
