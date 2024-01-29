package cs3500.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Reversi that only has read only permissions. We can only observe the state
 * of the model, not mutate it. Can't be tested on its own because the game has to start
 * first before the player use these methods but this class does not have startGame.
 */

public class ROReversi implements ROModel<Hexagon> {
  //keeps track of player in each Hexagon
  protected final Map<Hexagon, PlayerPiece> hexamap;
  protected boolean gameStarted;
  protected PlayerPiece currentPlayerPiece;
  protected int radius;
  protected Hexagon[][] board;
  protected boolean xPassed;
  protected boolean oPassed;


  /**
   * Constructor of Basic reversi.
   * Initialize startGame as false.
   * Instantiate hexmap that keeps track of a hexagon mapped to a player
   * Initialize pass statuses for both players as false
   */

  public ROReversi() {
    this.gameStarted = false;
    hexamap = new HashMap<>();
    xPassed = false;
    oPassed = false;
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
   * If this is a valid hexagon.
   *
   * @param q is the q coordinate of the hexagon
   * @param r is the r coordinate of the hexagon
   * @return if it's a valid hexagon.
   * @throws IllegalArgumentException if it is not on the board.
   */
  // validates the coordinates of the hexagon, enforces the invariant in the methods
  protected boolean isValidHexagon(int q, int r) throws IllegalArgumentException {
    int hexQ = q - radius;
    int hexR = r - radius;
    int hexS = -hexQ - hexR;

    // has to be in the range of -radius, radius
    if (hexQ > radius || hexQ < -radius || hexR > radius || hexR < -radius
            || hexS > radius || hexS < -radius || hexQ + hexR + hexS != 0) {
      return false;
    }

    return this.board[q][r] != null;
  }

  /**
   * Validates if the hexagon is on the board.
   *
   * @param q is the q coordinate of the hexagon
   * @param r is the r coordinate of the hexagon
   * @return is the array of the q and r coordinate if it's a valid hexagon.
   * @throws IllegalArgumentException if it is not on the board.
   */
  protected void validateHexagon(int q, int r) throws IllegalArgumentException {
    if (this.board[q][r] == null) {
      throw new IllegalArgumentException("Hexagon is null");
    }

    int hexQ = q - radius;
    int hexR = r - radius;
    int hexS = -hexQ - hexR;

    // has to be in the range of -radius, radius
    if (hexQ > radius || hexQ < -radius || hexR > radius || hexR < -radius
            || hexS > radius || hexS < -radius || hexQ + hexR + hexS != 0) {
      throw new IllegalArgumentException("Hexagon doesn't exist on the board!");
    }
  }


  /**
   * Gets all the legal moves for that player.
   *
   * @param playerPiece is the player whose legal moves we want.
   * @return the legal moves for that player.
   * @throws IllegalStateException    if game has not started.
   * @throws IllegalArgumentException If you didn't put a real player down.
   */

  @Override
  // getValidMoves method to find all valid moves for the current player
  public List<Hexagon> getLegalMoves(PlayerPiece playerPiece)
          throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    if (playerPiece != PlayerPiece.X && playerPiece != PlayerPiece.O) {
      throw new IllegalArgumentException("Not a real player");
    }
    List<Hexagon> legalMoves = new ArrayList<>();
    //get all of the empty hexagons in the board
    for (Hexagon hex : hexamap.keySet()) {
      if (hexamap.get(hex) == PlayerPiece.EMPTY)
        // get out the ones with empties
        if (isLegalMove(hex, playerPiece)) {
          legalMoves.add(hex);
        }
    }
    return legalMoves;
  }


  /**
   * If this move is a legal move for the current player.
   *
   * @param hex         the coordinate of the hexagon on the board.
   * @param playerPiece the player that wants to check their legal move
   * @return If this move is a legal move for the current player.
   * @throws IllegalStateException    if the game hasnt started
   * @throws IllegalArgumentException if the hexagon does not exist
   */
  public boolean isLegalMove(Hexagon hex, PlayerPiece playerPiece)
          throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();

    if (hexamap.get(hex) == null) {
      throw new IllegalArgumentException("Not legal Move");
    }
    List<Hexagon> neighbors = hex.getNeighbors();
    //List<Hexagon> otherPlayerHex = new ArrayList<>();
    for (Hexagon neighbor : neighbors) {
      if (hexamap.get(neighbor) == getNextPlayer()) {
        // otherPlayerHex.add(neighbor);
        // gets the directions of all 3 coordinates
        int directionQ = neighbor.getCoord("q") - hex.getCoord("q");
        int directionR = neighbor.getCoord("r") - hex.getCoord("r");
        int directionS = neighbor.getCoord("s") - hex.getCoord("s");
        Hexagon direction = new Hexagon(directionQ, directionR, directionS);
        //neighbor of the neighbor
        Hexagon nextHex = hex.add(direction);
        while (hexamap.get(nextHex) == getNextPlayer()) {
          Hexagon neighborHex = nextHex.add(direction);
          if (hexamap.get(neighborHex) == getNextPlayer()) {
            nextHex = neighborHex;
            continue;
          } else if (hexamap.get(neighborHex) == getCurrentPlayer()) {
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
   * Get the player at the given board coordinates.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @return The player on the hexagon of the board's cell
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the board's coordinates are not valid
   */

  @Override
  public PlayerPiece getPlayerAt(int q, int r) throws IllegalStateException,
          IllegalArgumentException {
    gameHasntStarted();
    if (!isValidHexagon(q, r)) {
      throw new IllegalArgumentException("Not a valid hexagon");
    }
    Hexagon hex = getPShapeAt(q, r);
    return hexamap.get(hex);
  }

  /**
   * Get the player at the given Hexagon on the board.
   *
   * @param hexagon given hexagon with cube coordinates
   * @return model.getPlayerAt(boardQ, boardR)
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the hexagon's coordinates are not valid
   */
  @Override
  public PlayerPiece getPlayerAt(Hexagon hexagon) throws IllegalStateException,
          IllegalArgumentException {
    int boardQ = hexagon.getCoord("q") + getSize();
    int boardR = hexagon.getCoord("r") + getSize();
    return getPlayerAt(boardQ, boardR);
  }

  /**
   * Returns the hexagon at the given board coordinates.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @return The hexagon on the board's cell.
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if hexagon's coordinates are not valid
   */
  @Override
  public Hexagon getPShapeAt(int q, int r) throws IllegalArgumentException {
    gameHasntStarted();
    if (!isValidHexagon(q, r)) {
      throw new IllegalArgumentException("Not a valid hexagon");
    }
    Hexagon hexagon = board[q][r];
    return new Hexagon(hexagon.getCoord("q"), hexagon.getCoord("r"), hexagon.getCoord("s"));
  }


  /**
   * Gets how many discs that the player has put down and flipped over. If
   * Player is Player.Empty, getNumPlayer counts how many hexagons do not have a player's disc
   * on that hexagon.
   *
   * @param playerPiece is a player, either Player.X, Player.O, or Player.EMPTY.
   * @return how many discs covered by a player or how many discs are EMPTY.
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the given player is null
   */
  @Override
  public int getPlayerScore(PlayerPiece playerPiece) throws IllegalStateException,
          IllegalArgumentException {
    gameHasntStarted();
    if (playerPiece == null) {
      throw new IllegalArgumentException("Can't be null");
    }
    // count how many key in the hashmap has given as value
    Set<Hexagon> keys = hexamap.keySet();
    int count = 0;
    for (Hexagon key : keys) {
      if (playerPiece == hexamap.get(key)) {
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


  //TODO: WHEN WAS THIS????
  // question: should we say that it is basically already in getLegalMoves? feels redundant
  protected boolean doesPlayerHaveMoves() {
    return getLegalMoves(PlayerPiece.X).isEmpty();

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
   * Get the diameter of the board in this game in number of hexagons.
   *
   * @return diameter of the board
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  public int getSizeTimesTwoPlusOne() {
    return 2 * radius + 1;
  }

  /**
   * Get the radius if the board in this game in number of hexagons.
   *
   * @return radius of the board
   * @throws IllegalStateException if the game has not started yet
   */
  @Override
  public int getSize() {
    return this.radius;
  }


  /**
   * Gets the board at the game's current state.
   *
   * @return board at the game's current state
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  public Hexagon[][] getBoard() throws IllegalStateException {
    gameHasntStarted();
    int diameter = this.getSizeTimesTwoPlusOne();
    Hexagon[][] copiedBoard = new Hexagon[diameter][];

    // Iterate through the existing board to create a deep copy
    for (int q = 0; q < diameter; q++) {
      copiedBoard[q] = new Hexagon[diameter];
      for (int r = 0; r < diameter; r++) {
        if (board[q][r] != null) {
          int qCoord = board[q][r].getCoord("q");
          int rCoord = board[q][r].getCoord("r");
          int sCoord = board[q][r].getCoord("s");
          copiedBoard[q][r] = new Hexagon(qCoord, rCoord, sCoord);
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
  public boolean isPlayersTurn(PlayerPiece playerPiece)
          throws IllegalStateException, IllegalArgumentException {
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
  public List<Hexagon> getDiscsToFlip(int q, int r)
          throws IllegalStateException, IllegalArgumentException {
    gameHasntStarted();
    Hexagon chosenHex = getPShapeAt(q, r);
    if (!isLegalMove(chosenHex, currentPlayerPiece)) {
      throw new IllegalArgumentException("Can't find any sandwiches :(");
    }
    List<Hexagon> neighbors = chosenHex.getNeighbors();
    List<Hexagon> toFlip = new ArrayList<>();
    //iterate through all 6 neighbors
    for (Hexagon neighbor : neighbors) {
      //if neighbor is not opposite color, skip
      if (hexamap.get(neighbor) != getNextPlayer()) {
        continue;
      }
      //directional vector hexagon
      Hexagon direction = new Hexagon(neighbor.getCoord("q") - chosenHex.getCoord("q"),
              neighbor.getCoord("r") - chosenHex.getCoord("r"),
              neighbor.getCoord("s") - chosenHex.getCoord("s"));
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
  protected List<Hexagon> toBeFlipped(Hexagon peek, Hexagon direction) {
    List<Hexagon> maybeFlipped = new ArrayList<>();
    // adds the neighbor to the maybeFlipped
    maybeFlipped.add(peek);
    // while the neighbors (starting with the initial neighbor) is opposite color...
    while (hexamap.get(peek) == getNextPlayer()) {
      //move it forward in the same direction
      peek = peek.add(direction);
      //if it is still opposite color then add the peek to maybeFlipped and continue
      if (hexamap.get(peek) == getNextPlayer()) {
        maybeFlipped.add(peek);
        //otherwise if it's the actual player then we know we have the full sandwich, so
        //add it to the return list(toFlip) and break
      } else if (hexamap.get(peek) == currentPlayerPiece) {
        return maybeFlipped;
      }
      //this will only execute if the peek is Player.EMPTY, in which case, clear the maybeFlipped
      //and move on to the next neighbor direction
    }
    return new ArrayList<>();
  }


  @Override
  public Map<Hexagon, PlayerPiece> getHexamap() {
    Map<Hexagon, PlayerPiece> deepCopyMap = new HashMap<>();

    for (Map.Entry<Hexagon, PlayerPiece> entry : hexamap.entrySet()) {
      Hexagon ogHex = entry.getKey();
      PlayerPiece ogDisc = entry.getValue();

      Hexagon copyHex = copyOfHex(ogHex);
      deepCopyMap.put(copyHex, ogDisc);
    }
    return deepCopyMap;
  }

  private Hexagon copyOfHex(Hexagon ogHex) {
    return new Hexagon(ogHex.getCoord("q"), ogHex.getCoord("r"), ogHex.getCoord("s"));
  }

}