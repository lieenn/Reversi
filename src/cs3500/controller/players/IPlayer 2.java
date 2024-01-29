package cs3500.controller.players;

import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;
import cs3500.view.ReversiView;

/**
 * A player Interface such that human or AI players can interact with the model.
 * Delegates most of its complexity to a strategy for choosing where to play next (hexagon).
 */
public interface IPlayer {
  /**
   * When a player wants to pass, they would press "p", the view
   * would the communicates with the controller and then the controller would call
   * the pass(); method
   * in the model, then model would note that the current player wants to pass
   * If the Player is a HumanPlayer, then we return the Hexagon they click at.
   * If the Player is an AIPlayer, we return the Hexagon that the strategy returns.
   *
   * @param model model that will be
   * @return the hexagon that the strategy of the player returns.
   */
  Hexagon pertainHexagon(ROModel model);

  /**
   * Tells the PAListener the choice of the player for hexagon. Plays the hexagon on the board.
   */
  void playHexagon();

  /**
   * If the current player is using the X or O disc.
   *
   * @return the current player is using the X or O disc.
   */
  PlayerPiece getPlayerPiece();

  /**
   * Sets the view for the Player. The view comes from the controller.
   */
  void setView(ReversiView view);

}
