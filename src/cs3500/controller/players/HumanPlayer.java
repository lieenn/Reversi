package cs3500.controller.players;

import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;
import cs3500.strategy.IMoveStrategy;
import cs3500.view.ITile;

/**
 * Represents a HumanPlayer, who can click on the board and select a hexagon, and enter that
 * selected hexagon.
 */
public class HumanPlayer extends AbstractPlayer {

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
  public HumanPlayer(PlayerPiece piece, IMoveStrategy<Hexagon> strategy) {
    super(piece, strategy);

  }

  @Override
  public void playPShape() {
    Hexagon hex = pertainPShape(model);
    ITile<Hexagon> hexagonTile = panel.pshapeToTile(hex);
    view.clickHex(hexagonTile);
  }

}
