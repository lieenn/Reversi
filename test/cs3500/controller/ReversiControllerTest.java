package cs3500.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.controller.players.HumanPlayer;
import cs3500.controller.players.IPlayer;
import cs3500.model.BasicReversi;
import cs3500.model.Hexagon;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.strategy.PromptUser;
import cs3500.view.AReversiPanel;
import cs3500.view.HexagonTile;
import cs3500.view.HexagonPanel;
import cs3500.view.ITile;
import cs3500.view.ReversiView;

/**
 * Tests the changes to the model when a controller makes a move for the model.
 */

public class ReversiControllerTest {
  MutableModel<Hexagon> model;
  ReversiController controller1;
  ReversiController controller2;

  ReversiView<Hexagon> view;
  ReversiView<Hexagon> view2;

  IPlayer<Hexagon> human;
  IPlayer<Hexagon> human2;

  AReversiPanel<Hexagon> panel;
  AReversiPanel<Hexagon> panel2;

  @Before
  public void init() {
    model = new BasicReversi();
    model.startGame(model.makeBoard(3));
    view = new ReversiMockView(model);
    view2 = new ReversiMockView(model);
    panel = view.getPanel();
    panel2 = view2.getPanel();
    human = new HumanPlayer(PlayerPiece.X, new PromptUser(view));
    human2 = new HumanPlayer(PlayerPiece.O, new PromptUser(view));
    controller1 = new ReversiController(model, human, view);
    controller2 = new ReversiController(model, human2, view2);
  }

  @Test
  public void testModelMoves() {
    Hexagon hexagon = new Hexagon(1, -2, 1);
    Assert.assertEquals(model.getPlayerAt(hexagon), PlayerPiece.EMPTY);
    ITile<Hexagon> hexTile = panel.pshapeToTile(hexagon);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    controller1.onPlayerPutDisc(hexTile);
    Assert.assertEquals(model.getPlayerAt(hexagon), PlayerPiece.X);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
  }

  @Test
  public void testModelMovesThrows() {
    Hexagon hexagon = new Hexagon(0, 0, 0);
    Assert.assertEquals(model.getPlayerAt(hexagon), PlayerPiece.EMPTY);
    ITile<Hexagon> hexTile = panel.pshapeToTile(hexagon);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    controller1.onPlayerPutDisc(hexTile);
    Assert.assertEquals(model.getPlayerAt(hexagon), PlayerPiece.EMPTY);
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
