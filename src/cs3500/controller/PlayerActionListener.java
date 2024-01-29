package cs3500.controller;

import cs3500.view.HexagonTile;
import cs3500.view.ITile;

/**
 * Interface for handling player actions in the Reversi game.
 * Respond to user interactions from the view and communicate with the model accordingly.
 */
public interface PlayerActionListener<PShape> {

  /**
   * When the view notified listener that the user has made a move
   * Listener would tell the model to put a disc at that position.
   * The ModelStatusListener is responsible for the switching of screens.
   *
   * @param hexagonTile the place where user wants to place their disc.
   */
  void onPlayerPutDisc(ITile<PShape> hexagonTile);

  /**
   * When the view tells the listener that the user has pressed "p"
   * Listener would tell the model that the currentPlayer has passed.
   */
  void onPlayerPass();

  /**
   * When the view notified listener that the user has clicked,
   * which can enable them to enter later,
   * Listener would tell the model to put a disc at that position.
   * The ModelStatusListener is responsible for the switching of screens.
   *
   * @param hexagonTile the place where user wants to place their disc.
   */
  void onPlayerClick(ITile<PShape> hexagonTile);

}
