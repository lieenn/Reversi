package cs3500.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Tests package-visible functionality of the BasicReversi. We test all functionality that
 * includes the ViewReversi methods.
 */
public class BasicReversiTests {
  MutableModel<Hexagon> model;
  Hexagon[][] board2;
  Hexagon[][] board3;


  /**
   * Counts how many hexagon is in the given board.
   *
   * @param board given 2d array
   * @return number of Hexagons is in the board
   */

  private int countHexagons(Hexagon[][] board) {
    int count = 0;
    for (Hexagon[] row : board) {
      for (Hexagon hexagon : row) {
        if (hexagon != null) {
          count++;
        }
      }
    }
    return count;
  }


  /**
   * Counts how many nulls are in the given board.
   *
   * @param board given 2d array
   * @return number of Hexagons is in the board
   */
  private int countNulls(Hexagon[][] board) {
    int count = 0;
    for (Hexagon[] row : board) {
      for (Hexagon hexagon : row) {
        if (hexagon == null) {
          count++;
        }
      }
    }
    return count;
  }


  /**
   * Check if the given hexagon is in the given board.
   *
   * @param board given board game
   * @param hex   hexagon we're looking for
   * @return true if the given hexagon exists
   */
  private boolean isHexagonInBoard(Hexagon[][] board, Hexagon hex) {
    for (Hexagon[] row : board) {
      for (Hexagon h : row) {
        if (h != null && h.equals(hex)) {
          return true;
        }
      }
    }
    return false;
  }


  @Before
  public void initReversi() {
    model = new BasicReversi();
    board3 = model.makeBoard(3);
    board2 = model.makeBoard(2);


  }


  @Test
  public void testMakeBoardBaseCase() {
    initReversi();
    Hexagon[][] board = model.makeBoard(3);

    //tests if all the hexagons is in the board
    for (int q = -3; q <= 3; q++) {
      for (int r = -3; r <= 3; r++) {
        for (int s = -3; s <= 3; s++) {
          if (q + r + s == 0) {
            Hexagon newHex = new Hexagon(q, r, s);
            Hexagon zero = new Hexagon(0, 0, 0);

            Assert.assertTrue(isHexagonInBoard(board, newHex));
            Assert.assertTrue(isHexagonInBoard(board, zero));
          }
        }
      }
    }
    assertEquals(37, countHexagons(board));
    Assert.assertNotNull(board[3][3]);
    Assert.assertNull(board[0][0]);
    Assert.assertNull(board[6][6]);
    assertEquals(countNulls(board), 12);
  }


  @Test
  public void testMakeBoard2() {
    Hexagon[][] board = model.makeBoard(2);
    int diameter = 2 * 3 + 1;

    for (int q = -2; q <= 2; q++) {
      for (int r = -2; r <= 2; r++) {
        for (int s = -2; s <= 2; s++) {
          if (q + r + s == 0) {
            Hexagon newHex = new Hexagon(q, r, s);
            Hexagon zero = new Hexagon(0, 0, 0);

            Assert.assertTrue(isHexagonInBoard(board, newHex));
            Assert.assertTrue(isHexagonInBoard(board, zero));
          }
        }
      }
    }
    assertEquals(19, countHexagons(board));
    assertEquals(countNulls(board), 6);
  }

  @Test
  public void testManyBoards() {
    int radiusTarget = 100;
    for (int radius = 2; radius < radiusTarget; radius++) {
      Hexagon[][] board = model.makeBoard(radius);
      assertEquals((radius * radius) + radius, countNulls(board));
    }
  }


  @Test
  public void testMakeBoardThrows1() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.makeBoard(1));
  }

  @Test
  public void testMakeBoardThrows() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.makeBoard(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeBoardThrows2() {
    model.makeBoard(-2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullBoard() {
    MutableModel<Hexagon> model = new BasicReversi();
    model.startGame(null);
  }


  @Test
  public void testStartGameThrowsState() {
    initReversi();
    model.startGame(board3);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.startGame(board3));
  }

  //testing if the all the colored disc are laid out
  @Test
  public void testStartGame2() {
    MutableModel<Hexagon> model = new BasicReversi();
    board2 = model.makeBoard(2);
    assertEquals(19, countHexagons(board2));

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPlayerScore(PlayerPiece.EMPTY));

    model.startGame(board2);
    assertEquals(19, countHexagons(board2));
    Hexagon mid = new Hexagon(0, 0, 0);
    //making sure that the neighbors of middle hexagon exists
    List<Hexagon> midNeighbors = mid.getNeighbors();
    for (Hexagon neighbor : midNeighbors) {
      Assert.assertTrue(isHexagonInBoard(board2, neighbor));
    }

    int numX = model.getPlayerScore(PlayerPiece.X);
    int numO = model.getPlayerScore(PlayerPiece.O);
    int numMt = model.getPlayerScore(PlayerPiece.EMPTY);

    assertEquals(3, numX);
    assertEquals(3, numO);
    assertEquals(numX, numO);
    assertEquals(13, numMt);
  }

  @Test
  public void testStartGame3() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    Hexagon mid = new Hexagon(0, 0, 0);

    List<Hexagon> midNeighbors = mid.getNeighbors();
    for (Hexagon neighbor : midNeighbors) {
      Assert.assertTrue(isHexagonInBoard(board3, neighbor));
    }

    model.startGame(board3);

    int numX = model.getPlayerScore(PlayerPiece.X);
    int numO = model.getPlayerScore(PlayerPiece.O);
    int numMt = model.getPlayerScore(PlayerPiece.EMPTY);

    assertEquals(3, numX);
    assertEquals(3, numO);
    assertEquals(numX, numO);
    assertEquals(31, numMt);
  }


  @Test
  public void testGetLegalMovesThrows() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

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
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    model.getLegalMoves(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLegalMovesMtPlayer() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    model.getLegalMoves(PlayerPiece.EMPTY);
  }


  @Test
  public void testGetLegalAt() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    model.putDisc(5, 2);
    model.putDisc(4, 1);
    model.putDisc(2, 2);
    model.putDisc(1, 4);
    model.putDisc(2, 5);
    model.putDisc(6, 2);
    model.putDisc(5, 0);
    model.putDisc(1, 6);
    model.putDisc(2, 6);
    model.putDisc(1, 2);
    model.putDisc(6, 1);
    model.putDisc(4, 4);
    model.putDisc(4, 5);
    model.putDisc(5, 4);
    model.putDisc(2, 1);
    model.putDisc(6, 0);
    model.putDisc(0, 5);
    model.putDisc(3, 6);
    model.putDisc(0, 3);
    model.putDisc(0, 4);
    model.putDisc(0, 6);
    model.putDisc(4, 0);

    Assert.assertFalse(model.isGameContinuing());
    assertEquals(model.getLegalMoves(PlayerPiece.X), new ArrayList<>());
    assertEquals(model.getLegalMoves(PlayerPiece.O), new ArrayList<>());
  }


  @Test
  public void testGetLegalMoves() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Hexagon zero = new Hexagon(0, 0, 0);
    List<Hexagon> legalMoves = new ArrayList<>();

    legalMoves.add(new Hexagon(1, -2, 1)); // east
    legalMoves.add(new Hexagon(-1, 2, -1));// ne
    legalMoves.add(new Hexagon(-2, 1, 1)); // sw
    legalMoves.add(new Hexagon(2, -1, -1));// west
    legalMoves.add(new Hexagon(-1, -1, 2));// se
    legalMoves.add(new Hexagon(1, 1, -2)); // nw

    assertEquals(model.getLegalMoves(PlayerPiece.X), legalMoves);

  }


  @Test
  public void testGetPlayerAt() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPShapeAt(3, 3));

    model.startGame(board3);

    assertEquals(model.getPlayerAt(3, 3), PlayerPiece.EMPTY);

    assertEquals(model.getPlayerAt(1, 2), PlayerPiece.EMPTY);
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.O);
    assertEquals(model.getPlayerAt(3, 2), PlayerPiece.X);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(0, 0));
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
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(0, 0));
  }


  @Test
  public void testGetPlayerAtIllegalMove() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(9, 2));
  }

  @Test
  public void testGetPlayerAtIllegalMove1() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(0, 0));
  }

  @Test
  public void testGetPlayerAtIllegalMoveNeg() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerAt(-2, 0));
  }


  @Test
  public void testgetPShapeAt() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    Hexagon mid = new Hexagon(0, 0, 0);
    Hexagon notMid = new Hexagon(-2, -1, 3);


    model.startGame(board3);

    assertEquals(model.getPShapeAt(3, 3), mid);

    assertEquals(model.getPShapeAt(1, 2), notMid);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(0, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(15, 5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(-1, 2));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetHexAtNegCoords() {
    Hexagon[][] board = model.makeBoard(2);
    model.startGame(board);
    model.getPShapeAt(-2, -5);
  }


  @Test
  public void testGetHexAtThrowsNull() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(0, 0));
  }


  @Test
  public void testGetHexAtThrows() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getPShapeAt(5, 2));
  }

  @Test
  public void testGetHexAtIllegalMove() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(9, 2));

  }

  @Test
  public void testGetHexAtIllegalMove1() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(6, 6));
  }

  @Test
  public void testGetHexIllegalMoveNeg() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPShapeAt(-2, 0));
  }


  @Test
  public void testPutDiscAtThrows() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.putDisc(5, 2));

  }


  @Test
  public void testPutDiscAtThrowsIllegalMove1() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.putDisc(6, 0));
  }


  @Test
  public void testPutDiscAtNull() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.putDisc(0, 0));
  }


  @Test
  public void testPutDiscAt() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

    assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    // 1 -1 0
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.O);

    // 1, -2, 1
    assertEquals(model.getPlayerAt(5, 2), PlayerPiece.EMPTY);
    model.putDisc(5, 2);

    // 1 -1 0
    // this is the disc that should be flipped over
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.X);

    assertEquals(model.getPlayerAt(5, 2), PlayerPiece.X);

    // there should now be a disc at 1, -2, 1
    // check who the player is
    assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.X);
    assertEquals(model.getPlayerAt(3, 2), PlayerPiece.X);

    model.putDisc(4, 1);

    assertEquals(model.getPlayerAt(3, 2), PlayerPiece.O);
    model.putDisc(2, 2);

    model.putDisc(1, 4);
    model.putDisc(2, 5);
    model.putDisc(6, 2);
    model.putDisc(5, 0);
    model.putDisc(1, 6);
    model.putDisc(2, 6);
    model.putDisc(1, 2);
    model.putDisc(6, 1);
    model.putDisc(4, 4);
    model.putDisc(4, 5);
    model.putDisc(5, 4);
    model.putDisc(2, 1);
    model.putDisc(6, 0);
    model.putDisc(0, 5);
    model.putDisc(3, 6);
    model.putDisc(0, 3);
    model.putDisc(0, 4);
    model.putDisc(0, 6);
    model.putDisc(4, 0);
    Assert.assertFalse(model.isGameContinuing());

    // find out the legal moves after that move
  }


  @Test
  public void testPassThrows() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.pass());
  }

  @Test
  public void testPass() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
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
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

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
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    // must start with player x
    assertEquals(model.getPlayerScore(PlayerPiece.O), 3);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 3);

    model.putDisc(5, 2);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 5);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 2);
    model.putDisc(4, 1);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 4);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 4);


    Assert.assertThrows(IllegalArgumentException.class, () ->
            model.getPlayerScore(null));
  }

  @Test
  public void testGetNextPlayerBaseCase() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);

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
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getNextPlayer());
  }

  @Test
  public void testGetNextPlayer() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    // must start with player x
    assertEquals(model.getNextPlayer(), PlayerPiece.O);
    model.putDisc(5, 2);
    assertEquals(model.getNextPlayer(), PlayerPiece.X);
  }


  @Test
  public void testGetCurrentPlayerThrows() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getCurrentPlayer());

  }

  @Test
  public void testGetCurrentPlayer() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    // must start with player x
    assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(5, 2);
    assertEquals(model.getCurrentPlayer(), PlayerPiece.O);


  }


  @Test
  public void testIsGameContinuingThrows() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    Assert.assertThrows(IllegalStateException.class, () ->
            model.isGameContinuing());

  }


  @Test
  public void testIsGameContinuing() {

    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    // should be true when the game starts
    assertEquals(model.isGameContinuing(), true);
    model.putDisc(5, 2);
    model.putDisc(4, 1);
    // should be true throughout the game
    assertEquals(model.isGameContinuing(), true);
    model.putDisc(2, 2);
    model.putDisc(1, 4);
    model.putDisc(2, 5);
    model.putDisc(6, 2);
    model.putDisc(5, 0);
    model.putDisc(1, 6);
    model.putDisc(2, 6);
    model.putDisc(1, 2);
    model.putDisc(6, 1);
    model.putDisc(4, 4);
    assertEquals(model.isGameContinuing(), true);
    model.putDisc(4, 5);
    model.putDisc(5, 4);
    model.putDisc(2, 1);
    model.putDisc(6, 0);
    model.putDisc(0, 5);
    model.putDisc(3, 6);
    model.putDisc(0, 3);
    assertEquals(model.isGameContinuing(), true);
    model.putDisc(0, 4);
    model.putDisc(0, 6);
    assertEquals(model.isGameContinuing(), true);
    model.putDisc(4, 0);
    // find out the legal moves after that move
    assertEquals(model.isGameContinuing(), false);
    assertEquals(model.getWinner(), PlayerPiece.O);
  }

  @Test
  public void testGetWinnerGameNotStarted() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getWinner());

    Assert.assertThrows(IllegalStateException.class, () ->
            model.getWinner());

  }

  @Test
  public void testGetWinnerPassPass() {
    // trying to get the no winner condition (stalemate)
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    assertEquals(model.isGameContinuing(), true);

    model.pass();
    model.pass();

    assertEquals(model.isGameContinuing(), false);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 3);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 3);

    // there should be no winner.
    Assert.assertThrows(IllegalStateException.class, () ->
            model.getWinner());

  }


  @Test
  public void testGetWinner() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    // 1 -1 0
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.O);
    // 1, -2, 1
    assertEquals(model.getPlayerAt(5, 2), PlayerPiece.EMPTY);
    model.putDisc(5, 2);
    // 1 -1 0
    // this is the disc that should be flipped over
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.X);

    assertEquals(model.getPlayerAt(5, 2), PlayerPiece.X);

    // there should now be a disc at 1, -2, 1
    // check who the player is
    assertEquals(model.getCurrentPlayer(), PlayerPiece.O);
    assertEquals(model.getPlayerAt(4, 2), PlayerPiece.X);
    assertEquals(model.getPlayerAt(3, 2), PlayerPiece.X);

    model.putDisc(4, 1);

    assertEquals(model.getPlayerAt(3, 2), PlayerPiece.O);
    model.putDisc(2, 2);

    model.putDisc(1, 4);
    model.putDisc(2, 5);
    model.putDisc(6, 2);
    model.putDisc(5, 0);
    model.putDisc(1, 6);
    model.putDisc(2, 6);
    model.putDisc(1, 2);
    model.putDisc(6, 1);
    model.putDisc(4, 4);
    model.putDisc(4, 5);
    model.putDisc(5, 4);
    model.putDisc(2, 1);
    model.putDisc(6, 0);
    model.putDisc(0, 5);
    model.putDisc(3, 6);
    model.putDisc(0, 3);
    model.putDisc(0, 4);
    model.putDisc(0, 6);
    assertEquals(model.isGameContinuing(), true);

    model.putDisc(4, 0);
    // find out the legal moves after that move

    assertEquals(model.isGameContinuing(), false);
    assertEquals(model.getPlayerScore(PlayerPiece.X), 7);
    assertEquals(model.getPlayerScore(PlayerPiece.O), 21);
    assertEquals(model.getWinner(), PlayerPiece.O);
  }


  @Test
  public void testGetDiameter2() {
    Hexagon[][] board = model.makeBoard(2);
    model.startGame(board);
    assertEquals(model.getSizeTimesTwoPlusOne(), 5);
  }

  @Test
  public void testGetRadius2() {
    Hexagon[][] board = model.makeBoard(2);
    model.startGame(board);
    assertEquals(model.getSize(), 2);
  }

  @Test
  public void testGetDiameter3() {
    Hexagon[][] board = model.makeBoard(3);
    model.startGame(board);

    assertEquals(model.getSizeTimesTwoPlusOne(), 3 + 3 + 1);
  }

  @Test
  public void testGetBoardSizeEqualAtStart() {
    Hexagon[][] board = model.makeBoard(2);

    for (int q = -2; q <= 2; q++) {
      for (int r = -2; r <= 2; r++) {
        for (int s = -2; s <= 2; s++) {
          if (q + r + s == 0) {
            Hexagon newHex = new Hexagon(q, r, s);
            Hexagon zero = new Hexagon(0, 0, 0);
            Assert.assertTrue(isHexagonInBoard(board, newHex));
            Assert.assertTrue(isHexagonInBoard(board, zero));
          }
        }
      }
    }
    assertEquals(19, countHexagons(board));
    assertEquals(countNulls(board), 6);

    model.startGame(board);

    Hexagon[][] copyBoard = model.getBoard();

    for (int q = -2; q <= 2; q++) {
      for (int r = -2; r <= 2; r++) {
        for (int s = -2; s <= 2; s++) {
          if (q + r + s == 0) {
            Hexagon newHex = new Hexagon(q, r, s);
            Hexagon zero = new Hexagon(0, 0, 0);
            Assert.assertTrue(isHexagonInBoard(copyBoard, newHex));
            Assert.assertTrue(isHexagonInBoard(copyBoard, zero));
          }
        }
      }
    }
    assertEquals(19, countHexagons(copyBoard));
    assertEquals(countNulls(copyBoard), 6);
  }

  @Test
  public void testGetBoardLocation() {
    Hexagon[][] board = model.makeBoard(2);
    // start game mutates the original board to map players onto the 6 surrounding hex

    model.startGame(board);
    Hexagon[][] copy = model.getBoard();

    // Compare the elements of both boards
    for (int q = 0; q < board.length; q++) {
      for (int r = 0; r < board[q].length; r++) {
        if (board[q][r] == null) {
          Assert.assertNull(copy[q][r]); // Check if null elements match
        } else {
          // For non-null elements, verify that the coordinates are equal
          Assert.assertEquals(board[q][r].getCoord("q"), copy[q][r].getCoord("q"));
          Assert.assertEquals(board[q][r].getCoord("r"), copy[q][r].getCoord("r"));
          Assert.assertEquals(board[q][r].getCoord("s"), copy[q][r].getCoord("s"));
        }
      }
    }
  }

  @Test
  public void testGetBoardPlayer() {
    Hexagon[][] board = model.makeBoard(2);
    // start game mutates the original board to map players onto the 6 surrounding hex

    model.startGame(board);
    Hexagon[][] copy = model.getBoard();

    // Compare the elements of both boards

    List<PlayerPiece> new1 = new ArrayList<>();
    List<PlayerPiece> new2 = new ArrayList<>();
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.O);
    new2.add(PlayerPiece.X);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.X);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.O);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.O);
    new2.add(PlayerPiece.X);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);
    new2.add(PlayerPiece.EMPTY);

    for (Hexagon[] hexM : copy) {
      for (Hexagon hex : hexM) {
        if (hex != null) {
          PlayerPiece currentPlayerPiece = model.getPlayerAt(hex);
          new1.add(currentPlayerPiece);
        }
      }
    }

    Assert.assertEquals(new1, new2);


  }

  @Test
  public void testGetRadius() {

    Hexagon[][] board = model.makeBoard(2);
    model.startGame(board);
    Assert.assertEquals(model.getSize(), 2);

  }


  @Test
  public void testPlayersTurn() {
    Hexagon[][] board = model.makeBoard(2);
    // start game mutates the original board to map players onto the 6 surrounding hex

    model.startGame(board);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    Assert.assertTrue(model.isPlayersTurn(PlayerPiece.X));
  }

  @Test
  public void testGetPlayerAtHexagon() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    Hexagon xTopLeft = new Hexagon(0, -1, 1);
    Assert.assertEquals(PlayerPiece.X, model.getPlayerAt(xTopLeft));
    Hexagon oTopRight = new Hexagon(1, -1, 0);
    Assert.assertEquals(PlayerPiece.O, model.getPlayerAt(oTopRight));
  }


  @Test
  public void testPutDiscAtHexagon() {
    MutableModel<Hexagon> model = new BasicReversi();
    board3 = model.makeBoard(3);
    model.startGame(board3);
    Hexagon target = new Hexagon(-1, -1, 2);
    model.putDisc(target);
    assertEquals(target, model.getPShapeAt(2, 2));
  }


}



