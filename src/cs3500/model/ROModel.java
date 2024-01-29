package cs3500.model;

import java.util.List;
import java.util.Map;


/**
 * The read only model has observer methods to display the current state of the game.
 * None of the methods should mutate the model. A PShape is a Parameterized Shape. The
 * q and r that is in this ROModel correspond to the Hexagonal coordinates on a cube-coordinate
 * system, which represent the row and column of the board the Reversi is being played on.
 * This q and r correspond to the x and y of a Square Reversi Board.
 */

public interface ROModel<PShape> {

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
  List<PShape> getLegalMoves(PlayerPiece playerPiece)
          throws IllegalStateException, IllegalArgumentException;


  /**
   * Get the player at the given board coordinates.
   *
   * @param q column coordinate of the cell on the board (if you are playing Hexagonal Reversi)
   *          and the x on the Square Reversi is the column coordinate on the board.
   * @param r row coordinate of the cell on the board (if you are playing Hexagonal Reversi)
   *          and the y on the Square Reversi is the row coordinate on the board.
   * @return The player on the PShape of the board's cell
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the board's coordinates are not valid
   */

  PlayerPiece getPlayerAt(int q, int r) throws IllegalStateException, IllegalArgumentException;


  /**
   * Get the player at the given PShape on the board.
   *
   * @param hex given PShape with cube coordinates
   * @return model.getPlayerAt(boardQ, boardR)
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if the PShape's coordinates are not valid
   */
  PlayerPiece getPlayerAt(PShape hex) throws IllegalStateException, IllegalArgumentException;


  /**
   * Returns the PShape at the given board coordinates.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @return The PShape on the board's cell.
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if PShape's coordinates are not valid
   */

  PShape getPShapeAt(int q, int r) throws IllegalStateException, IllegalArgumentException;

  /**
   * Checks if the move is a legal move for that player.
   *
   * @param hex         the coordinate of the PShape on the board.
   * @param playerPiece the player that wants to check their legal move
   * @return if the move is a legal move for that player.
   */
  boolean isLegalMove(PShape hex, PlayerPiece playerPiece);

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
  int getPlayerScore(PlayerPiece playerPiece) throws IllegalStateException,
          IllegalArgumentException;

  /**
   * Returns the Next Player of the game.
   *
   * @return the next player.
   * @throws IllegalStateException if the game hasn't been started yet
   */
  PlayerPiece getNextPlayer() throws IllegalStateException;

  /**
   * Returns the Current Player of the game.
   *
   * @return the current player.
   * @throws IllegalStateException if the game hasn't been started yet
   */
  PlayerPiece getCurrentPlayer() throws IllegalStateException;


  /**
   * Checks if the game is over. The game is over if both players have passed
   *
   * @return true if the game over
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if the game hasn't ended yet
   */
  boolean isGameContinuing() throws IllegalStateException;


  /**
   * Winner of the game. This method is only called if and only if there is a winner in the game.
   * The Player with the most pieces in the game is the winner if the game has ended.
   *
   * @return the winner of the game
   * @throws IllegalStateException if there is a stalemate or a tie
   * @throws IllegalStateException if the game hasn't been started yet
   * @throws IllegalStateException if the game hasn't ended yet
   */
  PlayerPiece getWinner() throws IllegalStateException;

  /**
   * Get the diameter of the board in this game in number of PShapes for Hexagonal Board.
   * Also a nice convenience method to have, in case one would like to calculate the size of
   * anything times two plus one.
   *
   * @return two times the size plus one of the board
   */
  int getSizeTimesTwoPlusOne();

  /**
   * Get the radius if the board in this game in number of PShapes.
   *
   * @return radius of the board
   */
  int getSize();

  /**
   * Gets the board at the game's current state.
   *
   * @return board at the game's current state
   * @throws IllegalStateException if the game has not started yet.
   */
  PShape[][] getBoard();

  /**
   * Check if it is the given Player's turn.
   *
   * @param playerPiece the given player
   * @return true if it is the given player's turn
   * @throws IllegalStateException    If the has not started
   * @throws IllegalArgumentException If the player is neither X nor O
   */
  boolean isPlayersTurn(PlayerPiece playerPiece) throws IllegalStateException,
          IllegalArgumentException;

  /**
   * The discs that flip when you put disc on the axial coordinate spot (q, r).
   *
   * @param q where you would potentially put your disc at coordinate q or x.
   * @param r where you would potentially put your disc at coordinate r o y.
   * @return The discs that flip when you put disc on the axial coordinate spot (q, r).
   */
  List<PShape> getDiscsToFlip(int q, int r);

  /**
   * Gets the mapping of the hexagon coordinates to the player pieces.
   */

  Map<PShape, PlayerPiece> getHexamap();

}
