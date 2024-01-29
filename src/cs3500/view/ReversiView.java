package cs3500.view;

import java.awt.event.MouseListener;
import java.util.List;

import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;

/**
 * Interface for the GUI of Reversi. It displays the Reversi game's graphical user interface.
 * Handles the presentation of the game state and user interactions.
 */
public interface ReversiView<PShape> extends PlayerAction<PShape> {

  /**
   * Set up the ActionListener to handle click events in this view.
   * When you click on cell -> turn it cyan
   *
   * @param listener the controller
   */
  void addClickListener(MouseListener listener); // mouseClickListener // features interface

  /**
   * Make the view visible to start the game session.
   */
  void display();

  /**
   * Notifies view that it's their turn.
   */
  void isYourTurn();

  /**
   * Notifies view that it's not their turn.
   */
  void isNotYourTurn();

  /**
   * Popup to notifies view that it's not their turn. Basically
   * the view (that we refer to) can't click.
   */
  void notYourTurnYet();

  /**
   * Notifies view that the game over. Can no longer click on the screen, has an ending screen.
   * Displays who the winner is or if it's a stalemate if there is one.
   */
  void gameOver(PlayerPiece piece);

  /**
   * Displays a window that it's not their turn if they click or if the move is illegal.
   */
  void wrongMove();

  /**
   * Refreshes the view to the most updated version of the game.
   */
  void refresh();

  /**
   * Gets the panel that the frame is attached to.
   *
   * @return the panel that the frame is attached to
   */
  AReversiPanel<PShape> getPanel();

  /**
   * Set the legal moves that the current player can make.
   *
   * @param legalMoves that the player can make
   */
  void setLegalMoves(List<PShape> legalMoves);

  /**
   * If there are no legal moves, there is a pop up window saying that you have to pass.
   */
  void gottaPass();

  /**
   * Set the PlayerPiece for the panel so the panel can use to check with the model
   * about the player.
   *
   * @param piece player that the panel is showing view for
   */
  void setPlayerPiece(PlayerPiece piece);

}
