package cs3500.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.model.ReversiSquareMockModel;
import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.model.ReversiMockModel;
import cs3500.model.SquareReversi;
import cs3500.textview.ReversiSquareTextualView;
import cs3500.textview.ReversiTextualView;
import cs3500.textview.TextView;

public class SquareStrat4Test {
  IMoveStrategy<Square> strategy;

  // there will never be a tie, just passing.
  @Test
  public void testChooseSquare() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Square> mockModel = new ReversiSquareMockModel(log);
    Square[][] board = mockModel.makeBoard(6);
    mockModel.startGame(board);
    TextView view = new ReversiSquareTextualView(mockModel);

    IMoveStrategy<Square> moveStrategy = new SquareStrat4(mockModel);

    int runcounter = 0;
    while (mockModel.isGameContinuing() || runcounter++ < 10) {
      if (!mockModel.isGameContinuing()) {
        break;
      }

      moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.X));
      Square moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
      System.out.println(moveX);

      mockModel.putDisc(moveX);
      System.out.println(view);
      Assert.assertTrue(mockModel.isGameContinuing());

      moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.O));
      Square moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
      mockModel.putDisc(moveO);
      System.out.println(view);
    }

    Assert.assertFalse(mockModel.isGameContinuing());

    System.out.println(log);
  }

}
