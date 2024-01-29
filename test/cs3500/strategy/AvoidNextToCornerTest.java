package cs3500.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.model.Hexagon;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.model.ReversiMockModel;
import cs3500.textview.ReversiTextualView;
import cs3500.textview.TextView;

/**
 * Tests the avoid the hexagons that are next to corner strategy since if your pieces are close to
 * the corner, your opponent has the chance to flip all the pieces from that corner, giving them
 * an advantage.
 */
public class AvoidNextToCornerTest {

  @Test
  public void testChooseHexagonAllNextToCorner() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);

    Hexagon[][] board = mockModel.makeBoard(3);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new AvoidNextToCorner(mockModel);

    Hexagon moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
    mockModel.putDisc(moveX);
    System.out.println(view);
    Assert.assertTrue(mockModel.isGameContinuing());

    Hexagon moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO);
    System.out.println(view);

    mockModel.putDisc(new Hexagon(0, 0, 0)); //chose position for X


    //all valid moves are next to corners, player is forced to use a different strategy
    Hexagon moveO2 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO2);
    System.out.println(view);

    Assert.assertTrue(mockModel.isGameContinuing());

    System.out.println(log);
  }

  @Test
  public void testChooseHexagon() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);

    Hexagon[][] board = mockModel.makeBoard(4);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new AvoidNextToCorner(mockModel);

    Hexagon moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
    mockModel.putDisc(moveX);
    System.out.println(view);
    Assert.assertTrue(mockModel.isGameContinuing());

    Hexagon moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO);
    System.out.println(view);

    mockModel.putDisc(new Hexagon(0, 0, 0)); // chose position for X
    mockModel.putDisc(new Hexagon(2, 0, -2)); // chose position for X
    mockModel.putDisc(new Hexagon(0, -2, 2)); // chose position for X

    // the move that captures the most disc is next to the corner,
    // player is forced to make a different move
    Hexagon moveO2 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO2);
    System.out.println(view);

    Assert.assertTrue(mockModel.isGameContinuing());

    System.out.println(log);
  }

}
