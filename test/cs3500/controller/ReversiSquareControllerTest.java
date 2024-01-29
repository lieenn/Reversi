package cs3500.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.controller.players.HumanPlayer;
import cs3500.controller.players.HumanSquarePlayer;
import cs3500.controller.players.IPlayer;
import cs3500.model.BasicReversi;
import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.model.SquareReversi;
import cs3500.strategy.PromptUser;
import cs3500.strategy.SquareStrat4;
import cs3500.strategy.SquareStratHuman;
import cs3500.view.AReversiPanel;
import cs3500.view.SquareTile;
import cs3500.view.SquarePanel;
import cs3500.view.ITile;
import cs3500.view.ReversiView;

/**
 * Tests the changes to the model when a controller makes a move for the model.
 */

public class ReversiSquareControllerTest {
  MutableModel<Square> model;
  ReversiSquareController controller1;
  ReversiSquareController controller2;

  ReversiView<Square> view;
  ReversiView<Square> view2;

  IPlayer<Square> human;
  IPlayer<Square> human2;

  AReversiPanel<Square> panel;
  AReversiPanel<Square> panel2;

  @Before
  public void init() {
    model = new SquareReversi();
    model.startGame(model.makeBoard(6));
    view = new ReversiSQMockView(model);
    view2 = new ReversiSQMockView(model);
    panel = view.getPanel();
    panel2 = view2.getPanel();
    human = new HumanSquarePlayer(PlayerPiece.X, new SquareStratHuman(view));
    human2 = new HumanSquarePlayer(PlayerPiece.O, new SquareStratHuman(view));
    controller1 = new ReversiSquareController(model, human, view);
    controller2 = new ReversiSquareController(model, human2, view2);
  }

  @Test
  public void testModelMoves() {
    Square sq = new Square(3, 1);
    Assert.assertEquals(model.getPlayerAt(sq), PlayerPiece.EMPTY);
    ITile<Square> hexTile = panel.pshapeToTile(sq);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    controller1.onPlayerPutDisc(hexTile);
    Assert.assertEquals(model.getPlayerAt(sq), PlayerPiece.X);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
  }

  @Test
  public void testModelMovesThrows() {
    Square sq = new Square(0, 0);
    Assert.assertEquals(model.getPlayerAt(sq), PlayerPiece.EMPTY);
    ITile<Square> hexTile = panel.pshapeToTile(sq);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    controller1.onPlayerPutDisc(hexTile);
    Assert.assertEquals(model.getPlayerAt(sq), PlayerPiece.EMPTY);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
  }

  @Test
  public void testModelPass() {
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    controller1.onPlayerPass();
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
  }

  @Test
  public void testGameOver() {
    Assert.assertEquals(model.isGameContinuing(), true);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    controller1.onPlayerPass();
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
    controller2.onPlayerPass();
    //controller1.onGameOver();
    Assert.assertEquals(model.isGameContinuing(), false);
  }

  @Test
  public void testPlayerPiece() {
    Assert.assertEquals(controller1.playerPiece(), PlayerPiece.X);

    Assert.assertEquals(controller2.playerPiece(), PlayerPiece.O);

  }

}
