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
 * Tests the top left move. This is not required for the assignment, we just wanted to test a
 * simple strategy without thinking too much.
 */
public class FindMostTopLeftMoveTest {
  IMoveStrategy<Hexagon> strategy;

  // there will never be a tie, just passing.
  @Test
  public void testChooseHexagon() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);
    Hexagon[][] board = mockModel.makeBoard(2);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new FindMostTopLeftMove(mockModel);

    int runcounter = 0;
    while (mockModel.isGameContinuing() || runcounter++ < 10) {
      if (!mockModel.isGameContinuing()) {
        break;
      }

      moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.X));
      Hexagon moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
      mockModel.putDisc(moveX);
      System.out.println(view);
      Assert.assertTrue(mockModel.isGameContinuing());

      moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.O));
      Hexagon moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
      mockModel.putDisc(moveO);
      System.out.println(view);
    }

    Assert.assertFalse(mockModel.isGameContinuing());

    System.out.println(log);
  }


}
