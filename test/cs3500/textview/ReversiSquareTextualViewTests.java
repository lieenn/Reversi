package cs3500.textview;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.model.BasicReversi;
import cs3500.model.PlayerPiece;
import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.SquareReversi;

/**
 * Tests what the view of the game looks like in the command line.
 */
public class ReversiSquareTextualViewTests {
  MutableModel<Square> model;

  @Before
  public void init() {
    model = new SquareReversi();
  }

  @Test
  public void testViewBaseCase() {
    model.startGame(model.makeBoard(6));
    TextView view = new ReversiSquareTextualView(model);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");

  }

  @Test
  public void testThereIsWinnerCase() {
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


  }

  @Test
  public void testThereIsTie() {
    model.startGame(model.makeBoard(6));
    TextView view = new ReversiSquareTextualView(model);
    Assert.assertEquals(view.toString(), " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            " _ _ O X _ _\n" +
            " _ _ _ _ _ _\n" +
            " _ _ _ _ _ _\n");
    Assert.assertEquals(model.getPlayerAt(2,2), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(2,3), PlayerPiece.O);
    Assert.assertEquals(model.getCurrentPlayer(), PlayerPiece.X);
    model.putDisc(2, 4);
    Assert.assertEquals(model.getPlayerAt(2,3), PlayerPiece.X);
    model.putDisc(1, 4);
    model.putDisc(4,2);
    model.putDisc(2,5);
    Assert.assertEquals(model.getPlayerAt(2,3), PlayerPiece.O);
    Assert.assertEquals(model.getPlayerAt(1,4), PlayerPiece.O);
    model.putDisc(0,5);
    Assert.assertEquals(model.getPlayerAt(2,3), PlayerPiece.X);
    Assert.assertEquals(model.getPlayerAt(1,4), PlayerPiece.X);
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

  }




}