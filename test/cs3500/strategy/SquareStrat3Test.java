package cs3500.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.model.ReversiSquareMockModel;
import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.model.ReversiMockModel;
import cs3500.textview.ReversiSquareTextualView;
import cs3500.textview.ReversiTextualView;
import cs3500.textview.TextView;

/**
 * Tests for the Reversi model using the going to the corner strategy.
 * Both users, X and O are using the strategy. They want to go to the corner.
 * They yearn for the corner.
 */

public class SquareStrat3Test {
  @Test
  public void testChooseSquare() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Square> mockModel = new ReversiSquareMockModel(log);

    Square[][] board = mockModel.makeBoard(4);
    mockModel.startGame(board);
    TextView view = new ReversiSquareTextualView(mockModel);

    IMoveStrategy<Square> moveStrategy = new SquareStrat3(mockModel);

    Square moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
    mockModel.putDisc(moveX);
    Assert.assertTrue(mockModel.isGameContinuing());

    Square moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO);

    mockModel.putDisc(new Square(0, 0)); // chose position for X
    mockModel.putDisc(new Square(2, 0)); // chose position for X
    mockModel.putDisc(new Square(2, 2)); // chose position for X

    //the corner is available
    Square moveO2 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO2);

    Assert.assertTrue(mockModel.isGameContinuing());

    System.out.println(log);
  }

  @Test
  public void testChooseSquareAllNextToCorner() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Square> mockModel = new ReversiSquareMockModel(log);

    Square[][] board = mockModel.makeBoard(4);
    mockModel.startGame(board);
    TextView view = new ReversiSquareTextualView(mockModel);

    IMoveStrategy<Square> moveStrategy = new SquareStrat3(mockModel);

    Square moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
    mockModel.putDisc(moveX);
    System.out.println(view);
    Assert.assertTrue(mockModel.isGameContinuing());

    Square moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO);
    System.out.println(view);

    mockModel.putDisc(new Square(0, 0)); //chose position for X
    mockModel.putDisc(new Square(2, 0)); // chose position for X
    mockModel.putDisc(new Square(2, 2)); // chose position for X

    //all valid moves are next to corners, player is forced to use a different strategy
    Square moveO2 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(moveO2);
    System.out.println(view);

    Assert.assertTrue(mockModel.isGameContinuing());

    System.out.println(log);
  }
}
