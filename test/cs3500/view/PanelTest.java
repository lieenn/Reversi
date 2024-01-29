//package cs3500.view;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.awt.Color;
//
//import cs3500.model.BasicReversi;
//import cs3500.model.Hexagon;
//import cs3500.model.MutableModel;
//
///**
// * Tests the panel's use in converting different elements to other elements within the panel.
// */
//public class PanelTest {
//  MutableModel model;
//  ReversiView view;
//
//  @Before
//  public void init() {
//    model = new BasicReversi();
//    Hexagon[][] board = model.makeBoard(3);
//    //model.startGame(model.getBoard(3));
//    model.startGame(board);
//
//    view = new ReversiFrame(model, "blah blah");
//  }
//
//  @Test
//  public void testHexToTile() {
//    Hexagon hex0 = new Hexagon(0, 0, 0);
//    // Color color, int x, int y, int size, Hexagon hex
//    HexagonTile hexTile0 = new HexagonTile(Color.PINK, 300, 300, 54, hex0);
//    HexagonTile hexagonTile = view.getPanel().hexToTile(hex0);
//    System.out.println(hexagonTile);
//    Assert.assertEquals(hexagonTile, hexTile0);
//  }
//
//}
