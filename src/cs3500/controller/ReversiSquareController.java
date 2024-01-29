package cs3500.controller;

import cs3500.controller.players.IPlayer;
import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.view.ITile;
import cs3500.view.ReversiView;

/**
 * The controller is a PlayerActionListener and a ModelStatusListener. It listens for events
 * from the model and the view and updates each according to those events using the observer
 * pattern.
 */
public class ReversiSquareController implements PlayerActionListener<Square>, ModelStatusListener{

  private final MutableModel<Square> model;
  private final ReversiView<Square> view;
  private final IPlayer<Square> player;
  private ITile<Square> SquareTile;

  /**
   * A ReversiController takes in a model, player, and a view. Players may have
   * different strategies, especially if they are different types of players (i.e., HumanPlayer
   * or AIPlayer).
   *
   * @param model  the model that is being changed throughout the game.
   * @param player the player that is using this controller.
   * @param view   that is being updated throughout the game according to different events.
   */
  public ReversiSquareController(MutableModel<Square> model, IPlayer<Square> player, ReversiView<Square> view) {
    this.model = model;
    this.view = view;
    this.player = player;
    model.addModelStatusListener(this);
    view.addPlayerActionListener(this);
    player.setView(view);
//    view.setLegalMoves(model.getLegalMoves(playerPiece()));
    view.setPlayerPiece(player.getPlayerPiece());
    onPlayersTurn();
    view.refresh();
  }

  @Override
  public void onPlayersTurn() {
    PlayerPiece myPiece = player.getPlayerPiece();
    PlayerPiece currentPlayer = model.getCurrentPlayer();
    if (!model.isGameContinuing()) {
      return;
    }

    if (currentPlayer.equals(myPiece)) {
      view.setLegalMoves(model.getLegalMoves(playerPiece()));
      view.refresh();
      view.isYourTurn();
      player.playPShape();
    } else {
      view.isNotYourTurn();
    }
    view.refresh();
  }

  @Override
  public void onGameOver() {
    if (!model.isGameContinuing()) {
      view.gameOver(playerPiece());
    }
  }

  @Override
  public void onNoLegalMoves() {
    if (model.getCurrentPlayer() == playerPiece()) {
      view.gottaPass();
    }
  }

  @Override
  public PlayerPiece playerPiece() {
    return player.getPlayerPiece();
  }

  @Override
  public void onPlayerPutDisc(ITile<Square> SquareTile) {
    Square hex = SquareTile.getPShape();
    if (playerPiece().equals(model.getCurrentPlayer())) {
      try {
        model.putDisc(hex);
      } catch (IllegalArgumentException e) {
        view.wrongMove();
      }
      // makes it so that we unclick the Square on the board
      this.SquareTile = null;
      view.refresh();
    } else {
      view.notYourTurnYet();
    }
  }

  @Override
  public void onPlayerPass() {
    if (model.getCurrentPlayer() == player.getPlayerPiece()) {
      model.pass();
      this.SquareTile = null;
      view.refresh();
    } else {
      view.notYourTurnYet();
    }
  }

  @Override
  public void onPlayerClick(ITile<Square> SquareTile) {
    this.SquareTile = SquareTile;
  }


}
