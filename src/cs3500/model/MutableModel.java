package cs3500.model;


/**
 * MutableModel is the model with both read and write configurations. It is able to be mutated
 * with the following methods.
 */
public interface MutableModel<PShape> extends ROModel<PShape>, ModelStatus {

  /**
   * Create a 2D board game for Reversi.
   * Add PShape with cube coordinates to the PShapeMap that keep track of the players' pieces
   * on the PShape.
   * Set each PShape to have empty player by default, which means a blank boardGame with no disc
   * on the board. 
   *
   * @param size size of the board, radius of the giant  PShape-al board if you are playing
   *              PShape-al Reversi and the length of the board if you are playing Square Reversi.
   * @return a 2D board of PShapes, board[col][row] returns null if there is no  PShape inside
   * @throws IllegalArgumentException if the given size does not make sense for the type of game
   * you are playing, i.e., if hexagonal radius is 2 or less or if square size is 2 or less or
   * is an odd number.
   */
  PShape[][] makeBoard(int size);

  /**
   * For Hexagonal Reversi: Sets up the board with 6 pieces around the middle;
   * center PShape would be left empty.
   * It would first get the neighbors of the center Hexagon as a list. This list of
   * neighbors would be where the starting discs will be placed at.
   * Here are the neighbors of the middle:
   * Player O: NE, W, SE -> 1, 3, 5 (odd indices)
   * Player X: E, NW, SW  -> 0, 2, 4 (even indices).
   * If one is to be playing Square Reversi, the board would be set up with 4 pieces in the
   * middle by getting the 4 coordinates in the middle of the board using the size of the board.
   *
   * @param board takes in a blank board
   * @throws IllegalStateException    if game has already started
   * @throws IllegalArgumentException if the board is null
   * @throws IllegalArgumentException if the board is not emptied prior to beginning the game.
   */
  void startGame(PShape[][] board);

  /**
   * Puts the disc at the given location and passes the turn to the next player.
   *
   * @param q column coordinate of the cell on the board
   * @param r row coordinate of the cell on the board
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if  PShape's coordinates are not valid
   */

  void putDisc(int q, int r) throws IllegalStateException, IllegalArgumentException;

  /**
   * Puts the disc at the given  PShape on the board and passes the turn to the next player.
   *
   * @param  pShape  PShape that the client wants to place their disc down
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if  PShape's coordinates are not valid
   */
  void putDisc(PShape pShape) throws IllegalStateException, IllegalArgumentException;

  // i pass turn to you, you put down disc but putDisc forces you to pass after your turn

  /**
   * Passes turn to the next player, update passing status of current player.
   *
   * @throws IllegalStateException if the game hasn't been started yet
   */
  void pass();
}
