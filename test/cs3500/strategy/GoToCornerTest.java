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
 * Tests for the Reversi model using the going to the corner strategy.
 * Both users, X and O are using the strategy. They want to go to the corner.
 * They yearn for the corner.
 */

public class GoToCornerTest {
  @Test
  public void testChooseHexagon() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);

    Hexagon[][] board = mockModel.makeBoard(3);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new GoToCorner(mockModel);

    Hexagon moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
    mockModel.putDisc(moveX);
    Assert.assertTrue(mockModel.isGameContinuing());

    Hexagon moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO);

    mockModel.putDisc(new Hexagon(0, 0, 0)); // chose position for X
    mockModel.putDisc(new Hexagon(2, 0, -2)); // chose position for X
    mockModel.putDisc(new Hexagon(0, -2, 2)); // chose position for X

    //the corner is available
    Hexagon moveO2 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO2);

    Assert.assertTrue(mockModel.isGameContinuing());

    System.out.println(log);
  }

  @Test
  public void testChooseHexagonAllNextToCorner() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);

    Hexagon[][] board = mockModel.makeBoard(4);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new GoToCorner(mockModel);

    Hexagon moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
    mockModel.putDisc(moveX);
    System.out.println(view);
    Assert.assertTrue(mockModel.isGameContinuing());

    Hexagon moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO);
    System.out.println(view);

    mockModel.putDisc(new Hexagon(0, 0, 0)); //chose position for X
    mockModel.putDisc(new Hexagon(2, 0, -2)); // chose position for X
    mockModel.putDisc(new Hexagon(0, -2, 2)); // chose position for X

    //all valid moves are next to corners, player is forced to use a different strategy
    Hexagon moveO2 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO2);
    System.out.println(view);

    Assert.assertTrue(mockModel.isGameContinuing());

    System.out.println(log);
  }
}
