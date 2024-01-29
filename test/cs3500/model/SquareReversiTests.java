package cs3500.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.textview.ReversiSquareTextualView;
import cs3500.textview.TextView;

import static org.junit.Assert.assertEquals;

public class SquareReversiTests {

  /**
   * Counts how many Square is in the given board.
   *
   * @param board given 2d array
   * @return number of Squares is in the board
   */

  private int countSquare(Square[][] board) {
    int count = 0;
    for (Square[] row : board) {
      for (Square square : row) {
        if (square != null) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Check if the given square is in the given board.
   *
   * @param board given board game
   * @param hex   square we're looking for
   * @return true if the given square exists
   */
  private boolean isSquareInBoard(Square[][] board, Square hex) {
    for (Square[] row : board) {
      for (Square h : row) {
        if (h != null && h.equals(hex)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Counts how many nulls are in the given board.
   *
   * @param board given 2d array
   * @return number of Squares is in the board
   */
  private int countNulls(Square[][] board) {
    int count = 0;
    for (Square[] row : board) {
      for (Square Square : row) {
        if (Square == null) {
          count++;
        }
      }
    }
    return count;
  }

  MutableModel<Square> smodel;
  Square[][] sboard6;
  Square[][] sboard4;

  @Before
  public void initSquareReversi() {
    smodel = new SquareReversi();
    sboard4 = smodel.makeBoard(4);
    sboard6 = smodel.makeBoard(6);

  }

  @Test
  public void testMakeBoard() {
    initSquareReversi();
    Square[][] squareBoard = smodel.makeBoard(4);
    int numSquare = countSquare(squareBoard);

    for (int q = 0; q < 4; q++) {
      for (int r = 0; r < 4; r++) {
        Square newHex = new Square(q, r);
        Square zero = new Square(0, 0);
        Assert.assertTrue(isSquareInBoard(squareBoard, newHex));
        Assert.assertTrue(isSquareInBoard(squareBoard, zero));
      }
    }

    Assert.assertEquals(numSquare, 16);
    assertEquals(countNulls(squareBoard), 0);
  }

  @Test
  public void testMakeBoardThrows() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            smodel.makeBoard(-1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            smodel.makeBoard(1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            smodel.makeBoard(0));

  }


  @Test
  public void testStartGameThrowsState() {
    initSquareReversi();
    smodel.startGame(sboard6);
    Assert.assertThrows(IllegalStateException.class, () ->
            smodel.startGame(sboard6));
  }

  @Test
  public void testStartGameNull() {
    initSquareReversi();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            smodel.startGame(null));
  }


  //testing if the all the colored disc are laid out
  @Test
  public void testStartGame2() {
    MutableModel<Square> model = new SquareReversi();
    Square[][] board = model.makeBoard(6);
    model.startGame(board);

    int numX = model.getPlayerScore(PlayerPiece.X);
    int numO = model.getPlayerScore(PlayerPiece.O);
    int numMt = model.getPlayerScore(PlayerPiece.EMPTY);

    assertEquals(2, numX);
    assertEquals(2, numO);
    assertEquals(numX, numO);
    assertEquals(32, numMt);
    Assert.assertEquals(numX + numO + numMt, 36);
  }

  @Test
  public void testStartGame3() {
    MutableModel<Square> model = new SquareReversi();
    Square[][] board = model.makeBoard(24);
    model.startGame(board);
    Square mid = new Square(24 / 2, 24 / 2);

    int numX = model.getPlayerScore(PlayerPiece.X);
    int numO = model.getPlayerScore(PlayerPiece.O);
    int numMt = model.getPlayerScore(PlayerPiece.EMPTY);

    assertEquals(2, numX);
    assertEquals(2, numO);
    assertEquals(numX, numO);
    assertEquals(572, numMt);
    Assert.assertEquals(numX + numO + numMt, 24 * 24);
  }


  @Test
  public void testGetLegalMovesThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getLegalMoves(PlayerPiece.X));
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getLegalMoves(PlayerPiece.O));
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getLegalMoves(PlayerPiece.EMPTY));
    // game didnt start
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetLegalMovesNullPlayer() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    model.getLegalMoves(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLegalMovesMtPlayer() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    model.getLegalMoves(PlayerPiece.EMPTY);
  }

  @Test
  public void testGetLegalAt() {
    MutableModel<Square> model = new SquareReversi();
    model.startGame(model.makeBoard(6));
    TextView view = new ReversiSquareTextualView(model);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");
    Assert.assertEquals(model.getPlayerAt(2, 2), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(2, 4);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    model.putDisc(1, 4);
    model.putDisc(4, 2);
    model.putDisc(2, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.O);
    model.putDisc(0, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.X);
    model.putDisc(2, 1);
    model.putDisc(1, 0);
    model.putDisc(2, 0);
    model.putDisc(3, 0);
    model.putDisc(4, 1);
    model.putDisc(1, 5);
    model.putDisc(5, 2);
    model.putDisc(5, 0);
    model.putDisc(4, 0);
    model.putDisc(1, 1);
    model.putDisc(0, 0);
    model.putDisc(3, 1);
    model.putDisc(0, 1);
    model.putDisc(5, 1);
    model.putDisc(1, 2);
    Assert.assertEquals(view.toString(), " O O O O O X\n" +
            " O O O O O X\n" +
            " _ O O O O O\n" +
            " _ _ X X _ _\n" +
            " _ X X _ _ _\n" +
            " X X O _ _ _\n");
    model.putDisc(5, 3);
    model.putDisc(0, 3);
    model.putDisc(0, 2);
    model.putDisc(1, 3);
    model.putDisc(0, 4);
    model.putDisc(4, 3);
    model.putDisc(3, 4);
    model.pass();
    Assert.assertEquals(model.isGameContinuing(), true);

    model.putDisc(3, 5);
    model.putDisc(4, 5);
    model.putDisc(4, 4);
    model.putDisc(5, 4);
    model.putDisc(5, 5);
    Assert.assertEquals(model.getPlayerScore(PlayerPiece.O), model.getPlayerScore(PlayerPiece.X));
    Assert.assertEquals(view.toString(), " O O O O O X\n" +
            " O O O O O X\n" +
            " X O O O O X\n" +
            " X O O O O X\n" +
            " X X X X X X\n" +
            " X X X X X X\n");
    Assert.assertFalse(model.isGameContinuing());
    assertEquals(model.getLegalMoves(PlayerPiece.X), new ArrayList<>());
    assertEquals(model.getLegalMoves(PlayerPiece.O), new ArrayList<>());
  }


  @Test
  public void testGetLegalMoves() {
    MutableModel<Square> model = new SquareReversi();
    Square[][] newBoard = model.makeBoard(4);
    assertEquals(countSquare(newBoard), 16);
    model.startGame(newBoard);

    Square zeroTL = new Square(2, 2);
    Square zeroTR = new Square(3, 2);
    Square zeroBL = new Square(2, 3);
    Square zeroBR = new Square(3, 3);

    List<Square> legalMoves = new ArrayList<>();

    legalMoves.add(new Square(0, 2));
    legalMoves.add(new Square(3, 1));
    legalMoves.add(new Square(1, 3));
    legalMoves.add(new Square(2, 0));

    assertEquals(model.getLegalMoves(PlayerPiece.X).size(), 4);
    assertEquals(model.getLegalMoves(PlayerPiece.X), legalMoves);

  }


  @Test
  public void testGetPlayerAt() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    model.startGame(model.makeBoard(6));
    Assert.assertEquals(model.getPlayerAt(2, 2), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(2, 4);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    model.putDisc(1, 4);
    model.putDisc(4, 2);
    model.putDisc(2, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.O);
    model.putDisc(0, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.X);


    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(-3, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(0, -5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(-3, -3));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(20, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(0, 45));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(20, 40));
  }


  @Test
  public void testGetPlayerAtThrowsNull() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(15, 0));
  }


  @Test
  public void testGetPlayerAtIllegalMove() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(9, 2));
  }

  @Test
  public void testGetPlayerAtIllegalMove1() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(-1, -13));
  }

  @Test
  public void testGetPlayerAtIllegalMoveNeg() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(-2, 0));
  }


  @Test
  public void testgetPShapeAt() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    Square mid = new Square(3, 3);
    Square notMid = new Square(1, 2);


    model.startGame(sboard6);

    assertEquals(model.getPShapeAt(3, 3), mid);

    assertEquals(model.getPShapeAt(1, 2), notMid);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(-2, -1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(15, 5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(-1, 300));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtNegCoords() {
    Square[][] board = smodel.makeBoard(2);
    smodel.startGame(board);
    smodel.getPShapeAt(-2, -5);
  }


  @Test
  public void testGetHexAtThrowsNull() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(7, 7));
  }


  @Test
  public void testGetHexAtThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPShapeAt(5, 2));
  }

  @Test
  public void testGetHexAtIllegalMove() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(9, 2));

  }

  @Test
  public void testGetHexAtIllegalMove1() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(6, 6));
  }

  @Test
  public void testGetHexIllegalMoveNeg() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(-2, 0));
  }


  @Test
  public void testPutDiscAtThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.putDisc(5, 2));

  }


  @Test
  public void testPutDiscAtThrowsIllegalMove1() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.putDisc(6, 0));
  }


  @Test
  public void testPutDiscAtNull() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.putDisc(0, 0));
  }


  @Test
  public void testPutDiscAt() {
    MutableModel<Square> model = new SquareReversi();
    model.startGame(model.makeBoard(6));
    TextView view = new ReversiSquareTextualView(model);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");
    Assert.assertEquals(model.getPlayerAt(2, 2), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(2, 4);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    model.putDisc(1, 4);
    model.putDisc(4, 2);
    model.putDisc(2, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.O);
    model.putDisc(0, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.X);
    model.putDisc(2, 1);
    model.putDisc(1, 0);
    model.putDisc(2, 0);
    model.putDisc(3, 0);
    model.putDisc(4, 1);
    model.putDisc(1, 5);
    model.putDisc(5, 2);
    model.putDisc(5, 0);
    model.putDisc(4, 0);
    model.putDisc(1, 1);
    model.putDisc(0, 0);
    model.putDisc(3, 1);
    model.putDisc(0, 1);
    model.putDisc(5, 1);
    model.putDisc(1, 2);
    Assert.assertEquals(view.toString(), " O O O O O X\n" +
            " O O O O O X\n" +
            " _ O O O O O\n" +
            " _ _ X X _ _\n" +
            " _ X X _ _ _\n" +
            " X X O _ _ _\n");
    model.putDisc(5, 3);
    model.putDisc(0, 3);
    model.putDisc(0, 2);
    model.putDisc(1, 3);
    model.putDisc(0, 4);
    model.putDisc(4, 3);
    model.putDisc(3, 4);
    model.pass();
    Assert.assertEquals(model.isGameContinuing(), true);

    model.putDisc(3, 5);
    model.putDisc(4, 5);
    model.putDisc(4, 4);
    model.putDisc(5, 4);
    model.putDisc(5, 5);
    Assert.assertEquals(model.getPlayerScore(PlayerPiece.O), model.getPlayerScore(PlayerPiece.X));
    Assert.assertEquals(view.toString(), " O O O O O X\n" +
            " O O O O O X\n" +
            " X O O O O X\n" +
            " X O O O O X\n" +
            " X X X X X X\n" +
            " X X X X X X\n");

    Assert.assertEquals(model.isGameContinuing(), false);


    // find out the legal moves after that move
  }


  @Test
  public void testPassThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.pass());
  }

  @Test
  public void testPass() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    assertEquals(model.isGameContinuing(), true);
    assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.pass();
    assertEquals(model.isGameContinuing(), true);

    assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
    model.pass();
    assertEquals(model.isGameContinuing(), false);
  }


  @Test
  public void testGetNumPlayerThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPlayerScore(PlayerPiece.X));
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPlayerScore(PlayerPiece.O));
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPlayerScore(PlayerPiece.EMPTY));
    //game has not started
  }

  @Test
  public void testGetNumPlayer() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    // must start with player x
    assertEquals(model.getPlayerScore(PlayerPiece.O), 2);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 2);

    model.putDisc(3, 1);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 4);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 1);
    model.putDisc(4, 1);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 3);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 3);


    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerScore(null));
  }

  @Test
  public void testGetNextPlayerBaseCase() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);

    Assert.assertTrue(model.isGameContinuing());
    assertEquals(PlayerPiece.X, model.getCurrentPlayer());
    model.pass();
    Assert.assertTrue(model.isGameContinuing());
    assertEquals(PlayerPiece.O, model.getCurrentPlayer());
    model.pass();
    Assert.assertFalse(model.isGameContinuing());
  }

  @Test
  public void testGetNextPlayerThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getNextPlayer());
  }

  @Test
  public void testGetNextPlayer() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    // must start with player x
    assertEquals(model.getNextPlayer(), PlayerPiece.O);
    model.putDisc(3, 1);
    assertEquals(model.getNextPlayer(), PlayerPiece.X);
  }


  @Test
  public void testGetCurrentPlayerThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getCurrentPlayer());

  }

  @Test
  public void testGetCurrentPlayer() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    // must start with player x
    assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(3, 1);
    assertEquals(model.getCurrentPlayer(), PlayerPiece.O);


  }


  @Test
  public void testIsGameContinuingThrows() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.isGameContinuing());

  }


  @Test
  public void testIsGameContinuing() {

    MutableModel<Square> model = new SquareReversi();
    model.startGame(model.makeBoard(6));
    TextView view = new ReversiSquareTextualView(model);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");
    Assert.assertEquals(model.isGameContinuing(), true);

    Assert.assertEquals(model.getPlayerAt(2, 2), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(2, 4);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    model.putDisc(1, 4);
    model.putDisc(4, 2);
    model.putDisc(2, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.O);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.O);
    model.putDisc(0, 5);
    Assert.assertEquals(model.getPlayerAt(2, 3), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(1, 4), PlayerPiece.X);
    model.putDisc(2, 1);
    model.putDisc(1, 0);
    model.putDisc(2, 0);
    model.putDisc(3, 0);
    model.putDisc(4, 1);
    model.putDisc(1, 5);
    model.putDisc(5, 2);
    model.putDisc(5, 0);
    model.putDisc(4, 0);
    model.putDisc(1, 1);
    model.putDisc(0, 0);
    model.putDisc(3, 1);
    model.putDisc(0, 1);
    Assert.assertEquals(model.isGameContinuing(), true);

    model.putDisc(5, 1);
    model.putDisc(1, 2);
    Assert.assertEquals(view.toString(), " O O O O O X\n" +
            " O O O O O X\n" +
            " _ O O O O O\n" +
            " _ _ X X _ _\n" +
            " _ X X _ _ _\n" +
            " X X O _ _ _\n");
    model.putDisc(5, 3);
    model.putDisc(0, 3);
    model.putDisc(0, 2);
    model.putDisc(1, 3);
    model.putDisc(0, 4);
    model.putDisc(4, 3);
    model.putDisc(3, 4);
    model.pass();
    Assert.assertEquals(model.isGameContinuing(), true);

    model.putDisc(3, 5);
    model.putDisc(4, 5);
    model.putDisc(4, 4);
    model.putDisc(5, 4);
    model.putDisc(5, 5);
    Assert.assertEquals(model.getPlayerScore(PlayerPiece.O), model.getPlayerScore(PlayerPiece.X));
    Assert.assertEquals(view.toString(), " O O O O O X\n" +
            " O O O O O X\n" +
            " X O O O O X\n" +
            " X O O O O X\n" +
            " X X X X X X\n" +
            " X X X X X X\n");
    // find out the legal moves after that move
    assertEquals(model.isGameContinuing(), false);

  }

  @Test
  public void testGetWinnerGameNotStarted() {
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getWinner());

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getWinner());

  }

  @Test
  public void testGetWinnerPassPass() {
    // trying to get the no winner condition (stalemate)
    MutableModel<Square> model = new SquareReversi();
    sboard6 = model.makeBoard(6);
    model.startGame(sboard6);
    assertEquals(model.isGameContinuing(), true);

    model.pass();
    model.pass();

    assertEquals(model.isGameContinuing(), false);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 2);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 2);

    // there should be no winner.
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getWinner());

  }


  @Test
  public void testGetWinner() {
    MutableModel<Square> model = new SquareReversi();
    model.startGame(model.makeBoard(6));
    TextView view = new ReversiSquareTextualView(model);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");
    model.putDisc(3, 1);
    model.pass();
    model.pass();
    Assert.assertEquals(model.getWinner(), PlayerPiece.X);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ X _ _\n" +
            " _ _ X X _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");

    assertEquals(model.isGameContinuing(), false);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 4);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 1);
    assertEquals(model.getWinner(), PlayerPiece.X);
  }


  @Test
  public void testGetDiameter2() {
    Square[][] board = smodel.makeBoard(4);
    smodel.startGame(board);
    assertEquals(smodel.getSizeTimesTwoPlusOne(), 9);
  }

  @Test
  public void testGetRadius2() {
    Square[][] board = smodel.makeBoard(4);
    smodel.startGame(board);
    assertEquals(smodel.getSize(), 4);
  }

  @Test
  public void testGetDiameter3() {
    Square[][] board = smodel.makeBoard(6);
    smodel.startGame(board);

    assertEquals(smodel.getSizeTimesTwoPlusOne(), 6 * 2 + 1);
  }

  @Test
  public void testGetRadius() {

    Square[][] board = smodel.makeBoard(6);
    smodel.startGame(board);
    Assert.assertEquals(smodel.getSize(), 6);

  }


}





