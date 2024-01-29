package cs3500.model;

//import cs3500.controller.ModelStatusListener;

import cs3500.controller.ModelStatusListener;

/**
 * The notifier for the controller to alert it that it is a player's turn or if a game is over or
 * not so that the controller does not have to keep pressuring the model for an answer.
 */
public interface ModelStatus {

  /**
   * Notify player that it's their turn. Happens when the turn is passed to the next player.
   */
  void notifyPlayersTurn();

  /**
   * Notify controller that game is over. Happens when the game is over.
   */
  void notifyIsGameOver();

  /**
   * Adds the listeners for the model status. They listen for changes to the model, for example,
   * if it is a player's turn or if a game is over.
   *
   * @param modelStatusListener the listener that will be added to listen for the model status.
   */
  void addModelStatusListener(ModelStatusListener modelStatusListener);

  /**
   * Notifies the controller that there are no moves. Usually means that the controller will pass.
   */
  void notifyNoMoves();

}
