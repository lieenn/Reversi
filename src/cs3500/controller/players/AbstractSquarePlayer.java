package cs3500.controller.players;

import cs3500.model.Square;
import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;
import cs3500.strategy.IMoveStrategy;
import cs3500.view.AReversiPanel;
import cs3500.view.ReversiView;


/**
 * Abstract player for the game, methods are shared between the AIPlayer and the HumanPlayer.
 */
public abstract class AbstractSquarePlayer implements IPlayer<Square> {

  // controller tells player that its their turn
  // player decides on a move, then tell the controller their move
  // the view gets the move from the user, then tell set the player's move
  // -> player tells the controller

  protected final PlayerPiece piece;
  protected final IMoveStrategy<Square> strategy;
  // not final because they change throughout the game
  protected ReversiView<Square> view;
  protected AReversiPanel<Square> panel;
  protected ROModel<Square> model;

  /**
   * A player has a piece and a strategy that they use in order to play the game.
   *
   * @param piece    that the player uses on the board.
   * @param strategy that the player needs to play the game to find the best Square.
   */
  public AbstractSquarePlayer(PlayerPiece piece, IMoveStrategy<Square> strategy) {
    this.piece = piece;
    // and then find the Square clicked?
    this.strategy = strategy;
  }

  @Override
  public Square pertainPShape(ROModel<Square> model) {
    this.model = model;
    return strategy.choosePShape(model, piece);
  }

  // only one that is different
  @Override
  public abstract void playPShape();

  @Override
  public PlayerPiece getPlayerPiece() {
    return this.piece;
  }

  @Override
  public void setView(ReversiView<Square> view) {
    this.view = view;
    panel = view.getPanel();
  }


}
