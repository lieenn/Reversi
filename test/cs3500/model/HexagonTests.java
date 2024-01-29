package cs3500.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Tests for Hexagon in the model interface.
 */
public class HexagonTests {

  @Test
  public void testHexagonArithmetic() {
    Assert.assertEquals(new Hexagon(4, -10, 6),
            new Hexagon(1, -3, 2).add(new Hexagon(3, -7, 4)));
    Assert.assertEquals(new Hexagon(-2, 4, -2),
            new Hexagon(1, -3, 2).subtract(new Hexagon(3, -7, 4)));

  }


  // testing the directions here
  @Test
  public void testHexagonAdd0() {
    // east
    Hexagon expectedHex = new Hexagon(1, 0, -1);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(1, 0, -1));
    Hexagon newHex2 = ogHex.neighbor(0);
    Assert.assertEquals(expectedHex, newHex2);

    Assert.assertEquals(expectedHex, newHex);

  }

  @Test
  public void testHexagonAdd1() {
    // northeast
    Hexagon expectedHex = new Hexagon(1, -1, 0);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(1, -1, 0));
    Hexagon newHex2 = ogHex.neighbor(1);
    Assert.assertEquals(expectedHex, newHex2);

    Assert.assertEquals(expectedHex, newHex);

  }

  @Test
  public void testHexagonAdd2() {
    // northwest
    Hexagon expectedHex = new Hexagon(0, -1, 1);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(0, -1, 1));
    Hexagon newHex2 = ogHex.neighbor(2);
    Assert.assertEquals(expectedHex, newHex2);

    Assert.assertEquals(expectedHex, newHex);
  }

  @Test
  public void testHexagonAdd4() {
    // northwest
    Hexagon expectedHex = new Hexagon(-1, 1, 0);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(-1, 1, 0));
    Hexagon newHex2 = ogHex.neighbor(4);
    Assert.assertEquals(expectedHex, newHex2);

    Assert.assertEquals(expectedHex, newHex);
  }

  @Test
  public void testHexagonAdd5() {
    // northwest
    Hexagon expectedHex = new Hexagon(0, 1, -1);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(0, 1, -1));
    Hexagon newHex2 = ogHex.neighbor(5);
    Assert.assertEquals(expectedHex, newHex2);

    Assert.assertEquals(expectedHex, newHex);
  }


  @Test
  public void testHexagonAdd3() {
    // northwest
    Hexagon expectedHex = new Hexagon(-1, 0, 1);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(-1, 0, 1));
    Hexagon newHex2 = ogHex.neighbor(3);
    Assert.assertEquals(expectedHex, newHex2);

    Assert.assertEquals(expectedHex, newHex);
  }

  @Test
  public void testGetNeighbors() {
    Hexagon hex0 = new Hexagon(0, 0, 0);

    List<Hexagon> hex0neighbors = new ArrayList<Hexagon>() {
      {
        add(new Hexagon(1, 0, -1)); //
        add(new Hexagon(1, -1, 0));
        add(new Hexagon(0, -1, 1));
        add(new Hexagon(-1, 0, 1));
        add(new Hexagon(-1, 1, 0));
        add(new Hexagon(0, 1, -1));
      }
    };

    Assert.assertEquals(hex0.getNeighbors(), hex0neighbors);
    for (Hexagon neighbor : hex0neighbors) {
      Assert.assertTrue(hex0.getNeighbors().contains(neighbor));
    }
  }

  @Test
  public void testHexagonDirection() {
    Hexagon zero = new Hexagon(0, 0, 0);
    Assert.assertEquals(new Hexagon(0, -1, 1),
            zero.directions().get(2));
  }

  @Test
  public void testHexagonNeighbor() {
    Assert.assertEquals(new Hexagon(1, -3, 2),
            new Hexagon(1, -2, 1).neighbor(2));
  }


  @Test
  public void testHexagonEquality() {
    // northwest
    Hexagon expectedHex = new Hexagon(-1, 1, 0);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(-1, 1, 0));
    Hexagon newHex2 = ogHex.neighbor(4);
    Assert.assertEquals(expectedHex.equals(newHex2), true);
    Assert.assertEquals(expectedHex.equals(newHex), true);
    Assert.assertEquals(newHex2.equals(newHex), true);

  }

  @Test
  public void testHexagonHashCode() {
    // northwest
    Hexagon expectedHex = new Hexagon(-1, 1, 0);

    Hexagon ogHex = new Hexagon(0, 0, 0);
    // test 0
    Hexagon newHex = ogHex.add(new Hexagon(-1, 1, 0));
    Hexagon newHex2 = ogHex.neighbor(4);
    Assert.assertEquals(expectedHex.hashCode(), -6);
    Assert.assertEquals(newHex.hashCode(), -6);
    Assert.assertEquals(newHex2.hashCode(), -6);
    Assert.assertEquals(newHex2.hashCode(), newHex.hashCode());
  }

  @Test
  public void testGetCoord() {
    // northwest
    Hexagon expectedHex = new Hexagon(-1, 1, 0);
    Hexagon ogHex = new Hexagon(0, 0, 0);
    Assert.assertEquals(expectedHex.getCoord("q"), -1);
    Assert.assertEquals(expectedHex.getCoord("r"), 1);
    Assert.assertEquals(expectedHex.getCoord("s"), 0);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            expectedHex.getCoord("a"));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            expectedHex.getCoord("/"));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            expectedHex.getCoord("1"));


    Assert.assertEquals(ogHex.getCoord("q"), 0);
    Assert.assertEquals(ogHex.getCoord("r"), 0);
    Assert.assertEquals(ogHex.getCoord("s"), 0);

  }

  @Test
  public void testToString() {
    Hexagon expectedHex = new Hexagon(-1, 1, 0);
    Hexagon ogHex = new Hexagon(0, 0, 0);
    Assert.assertEquals(expectedHex.toString(), "Hexagon: -1 1 0");
    Assert.assertEquals(ogHex.toString(), "Hexagon: 0 0 0");


  }

}
