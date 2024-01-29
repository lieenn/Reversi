package cs3500.view;

//import cs3500.controller.PlayerActionListener;

import cs3500.controller.PlayerActionListener;

/**
 * The player-action interface that consists of methods that only players can do.
 * Handles user input, such as placing a disc, passing a turn,and responding to
 * player clicks on the game board.
 */
public interface PlayerAction<PShape> {
  /**
   * We press "enter" and call the model's putDisc. If the hexagon is a legal move,
   * we enter in the disc into the cell and update the view + model to reflect the player action.
   * If it is not a legal move, we have an error pop up.
   *
   * @param hexagonTile where we place the disc on the screen.
   */
  void putDisc(ITile<PShape> hexagonTile);

  /**
   * We press "p" and call the model's pass. We update the view + model to reflect
   * the player action.
   */
  void pass(); // when you press "p" on the keyboard

  /**
   * When a player clicks on a hexagon, the view would notifies its PlayerActionListener.
   *
   * @param hexagonTile where the player clicks.
   */
  void clickHex(ITile<PShape> hexagonTile);


  void addPlayerActionListener(PlayerActionListener<PShape> listener);


}
