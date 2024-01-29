package cs3500.controller.players;

import cs3500.model.Square;
import cs3500.model.PlayerPiece;
import cs3500.strategy.IMoveStrategy;
import cs3500.view.ITile;

/**
 * Represents a HumanPlayer, who can click on the board and select a Square, and enter that
 * selected Square.
 */
public class HumanSquarePlayer extends AbstractSquarePlayer {

  /**
   * The human player works as follows:
   * controller tells player that its their turn
   * player decides on a move, then tell the controller their move
   * the view gets the move from the user, then tell set the player's move
   * player tells the controller.
   *
   * @param piece    that the human is using to place on the board.
   * @param strategy that the human uses (usually is PromptUser, but we like abstracting).
   */
  public HumanSquarePlayer(PlayerPiece piece, IMoveStrategy<Square> strategy) {
    super(piece, strategy);

  }

  @Override
  public void playPShape() {
    Square hex = pertainPShape(model);
    ITile<Square> SquareTile = panel.pshapeToTile(hex);
    view.clickHex(SquareTile);
  }

}
