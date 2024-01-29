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
 * Test for the first strategy, the player would choose move
 * that capture the most disc of the other player,
 * if there are multiple moves, chose move that is the most top-left.
 */
public class CaptureAsManyPiecesTest {

  @Test
  public void testChooseHexagon() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);
    Hexagon[][] board = mockModel.makeBoard(2);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new CaptureAsManyPieces(mockModel);

    // ties all the way
    while (mockModel.isGameContinuing()) {
      if (!mockModel.isGameContinuing()) {
        break;
      }
      moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.X));
      Hexagon moveX = moveStrategy.choosePShape(mockModel, PlayerPiece.X);
      mockModel.putDisc(moveX);
      //System.out.println(view);
      Assert.assertTrue(mockModel.isGameContinuing());

      moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.O));
      Hexagon moveO = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
      mockModel.putDisc(moveO);
      //System.out.println(view);
    }
    Assert.assertFalse(mockModel.isGameContinuing());

    System.out.println(log);
  }

  @Test
  public void testChooseHexagonMostMoves() {
    // Create a mock implementation of the ROModel interface
    StringBuilder log = new StringBuilder();
    MutableModel<Hexagon> mockModel = new ReversiMockModel(log);
    Hexagon[][] board = mockModel.makeBoard(3);
    mockModel.startGame(board);
    TextView view = new ReversiTextualView(mockModel);

    IMoveStrategy<Hexagon> moveStrategy = new CaptureAsManyPieces(mockModel);
    //System.out.println(view);
    mockModel.putDisc(new Hexagon(0, 0, 0)); // We chose position for X
    //System.out.println(view);

    moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.O));
    Hexagon move10 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    Assert.assertEquals(moveStrategy.choosePShape(mockModel, PlayerPiece.O),
            new Hexagon(0, -2, 2)); // O finds
    mockModel.putDisc(move10);
    //System.out.println(view);

    mockModel.putDisc(new Hexagon(1, 1, -2));
    //System.out.println(view);
    // tie, but it will go to the left bottom coordinate
    moveStrategy.updateLegalMoves(mockModel.getLegalMoves(PlayerPiece.O));
    Hexagon move20 = moveStrategy.choosePShape(mockModel, PlayerPiece.O);
    mockModel.putDisc(move20);

    //System.out.println(view);


    System.out.println(log);
  }
}

