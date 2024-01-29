package cs3500.textview;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.model.BasicReversi;
import cs3500.model.Hexagon;
import cs3500.model.MutableModel;

/**
 * Tests what the view of the game looks like in the command line.
 */
public class ReversiTextualViewTest {
  MutableModel<Hexagon> model;

  @Before
  public void init() {
    model = new BasicReversi();
  }

  @Test
  public void testModelToString() {
    Hexagon[][] board = model.makeBoard(3);
    model.startGame(board);
    TextView view = new ReversiTextualView(model);
    Assert.assertEquals(view.toString(),
            "    _ _ _ _\n" +
                    "   _ _ _ _ _\n" +
                    "  _ _ X O _ _\n" +
                    " _ _ O _ X _ _\n" +
                    "  _ _ X O _ _ \n" +
                    "   _ _ _ _ _  \n" +
                    "    _ _ _ _   \n");

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

    Assert.assertEquals(view.toString(),
            "    _ O O O\n" +
                    "   X _ O _ O\n" +
                    "  X X O O X O\n" +
                    " X _ X _ O _ _\n" +
                    "  O O O O O O \n" +
                    "   O _ X _ O  \n" +
                    "    O O O O   \n");
  }


}