package cs3500.controller;

import cs3500.model.PlayerPiece;

/**
 * Listener for the model status, which is the status of the game, as in whose turn it is and
 * the game status, i.e. if the game is over or not.
 */
public interface ModelStatusListener {

  /**
   * When the listener gets notified that it's their turn, Effects for later:
   * takes input from the view, notify the model that the player clicks on a specific hexagon.
   */
  void onPlayersTurn();


  /**
   * When the listener gets notified that the game is over,
   * The listener would notify the view that game is over
   * Notify controllers that they can't do a move.
   */
  void onGameOver();


  /**
   * When the listener gets notified that the player does not have legal moves,
   * The listener would notify the view that there are no legal moves.
   * Notify controllers that they can't do a move.
   */
  void onNoLegalMoves();

  /**
   * Every listener represents a Player playing the game.
   * The ModelStatus needs to know which player does this listener represents.
   *
   * @return PlayerPiece that the listener represents.
   */
  PlayerPiece playerPiece();
}
