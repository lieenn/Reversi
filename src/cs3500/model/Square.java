package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Square implements IShape<Square> {
  private final int x;
  private final int y;
  /**
   * Constructs a square from the top left being (0, 0).
   *
   * @param x The x coordinate
   * @param y The y coordinate
   * @throws IllegalArgumentException if the x or y are less than 0
   */

  public Square(int x, int y) {
    if (x < -1 || y < -1) {
      throw new IllegalArgumentException("That's not a real square");
    }

    this.x = x;
    this.y = y;
  }



  /**
   * Adds the coordinates of another Square to this Square, resulting in a new Square.
   *
   * @param first The Square to add.
   * @return A new Square resulting from the addition.
   */
  
  public Square add(Square first) {
    return new Square(this.x + first.x, this.y + first.y);

  }

  /**
   * Subtracts the coordinates of another Square from this Square, resulting in a new Square.
   *
   * @param first The Square to subtract.
   * @return A new Square resulting from the subtraction.
   */
  
  public Square subtract(Square first) {
    return new Square(this.x - first.x, this.y - first.y);
  }

  /**
   * List of Square vectors representing different directions in a Square-al grid.
   *
   * @return list of directions.
   */
  
  public List<Square> directions() {
    List<Square> directions = new ArrayList<>();

    // counter - clockwise
    directions.add(new Square(1, 0)); //east
    directions.add(new Square(1, -1)); //ne
    directions.add(new Square(0, -1)); //north
    directions.add(new Square(-1, -1)); //nw
    directions.add(new Square(-1, 0)); // west
    directions.add(new Square(-1, 1)); //sw
    directions.add(new Square(0, 1)); //south
    directions.add(new Square(1, 1)); //se

    return directions;
  }

  /**
   * Retrieves the neighboring Square in a specified direction.
   *
   * @param direction An integer representing the direction (0 to 5) to find the neighbor.
   *                  0.East 1.Northeast 2.Northwest 3.West 4.SouthWest 5.SouthEast
   * @return A new Square representing the neighbor in the specified direction.
   */
  
  public Square neighbor(int direction) {
    return add(directions().get(direction));
  }

  /**
   * All the neighbors of the hex.
   *
   * @return all the closest neighbors of this Square
   */
  
  public List<Square> getNeighbors() {
    List<Square> newNeighbors = new ArrayList<>();
    for (int dir = 0; dir < directions().size(); dir++) {
      newNeighbors.add(neighbor(dir));
    }
    return newNeighbors;
  }

  public int getX() {
    return x;
  }

  public int getY(){
    return y;
  }

  public int getCoord(String coord) {
    if (coord.equals("x")) {
      return x;
    }
    if (coord.equals("y")) {
      return y;
    }
    else {
      throw new IllegalArgumentException("not a real coordinate");
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other instanceof Square) {
      Square that = (Square) other;
      return this.x == that.x && this.y == that.y;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.x * 31 + this.y * 17);
  }
  
  @Override
  public String toString() {
    return "Square " + x + " " + y;
  }

}
