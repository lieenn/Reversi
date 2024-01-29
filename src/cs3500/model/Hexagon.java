package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a hexagon in cube coordinates.
 * Hexagons are represented using cube coordinates in a hexagonal grid.
 * The hexagon is defined by three axial coordinates: q, r, and s.
 */
public class Hexagon implements IShape<Hexagon>{

  private final int q;
  private final int r;
  private final int s;
  /**
   * Constructs a hexagon with the cube coordinates q, r, and s.
   *
   * @param q The q coordinate in cube coordinates
   * @param r The r coordinate in cube coordinates
   * @param s The s coordinate in cube coordinates
   * @throws IllegalArgumentException if the q + r + s is not equal to 0
   */

  public Hexagon(int q, int r, int s) {
    if (q + r + s != 0) {
      throw new IllegalArgumentException("q + r + s must be 0");
    }

    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * Adds the coordinates of another hexagon to this hexagon, resulting in a new hexagon.
   *
   * @param b The hexagon to add.
   * @return A new hexagon resulting from the addition.
   */
  public Hexagon add(Hexagon b) {
    return new Hexagon(q + b.q, r + b.r, s + b.s);
  }

  /**
   * Subtracts the coordinates of another hexagon from this hexagon, resulting in a new hexagon.
   *
   * @param b The hexagon to subtract.
   * @return A new hexagon resulting from the subtraction.
   */
  public Hexagon subtract(Hexagon b) {
    return new Hexagon(q - b.q, r - b.r, s - b.s);
  }


  /**
   * List of Hexagon vectors representing different directions in a hexagonal grid.
   * 0.East 1.Northeast 2.Northwest 3.West 4.SouthWest 5.SouthEast
   *
   * @return list of directions.
   */

  public List<Hexagon> directions() {
    List<Hexagon> directions = new ArrayList<>();

    directions.add(new Hexagon(1, 0, -1)); //east
    directions.add(new Hexagon(1, -1, 0)); //ne
    directions.add(new Hexagon(0, -1, 1)); //nw
    directions.add(new Hexagon(-1, 0, 1)); //w
    directions.add(new Hexagon(-1, 1, 0)); //sw
    directions.add(new Hexagon(0, 1, -1)); //se

    return directions;
  }

  /**
   * Retrieves the neighboring hexagon in a specified direction.
   *
   * @param direction An integer representing the direction (0 to 5) to find the neighbor.
   *                  0.East 1.Northeast 2.Northwest 3.West 4.SouthWest 5.SouthEast
   * @return A new Hexagon representing the neighbor in the specified direction.
   */
  // for directions // Hex neightbor = new Hex(direction) // add neighbor
  public Hexagon neighbor(int direction) {
    return add(directions().get(direction));
  }


  /**
   * All the neighbors of the hex.
   *
   * @return all the closest neighbors of this hexagon
   */
  public List<Hexagon> getNeighbors() {
    List<Hexagon> newNeighbors = new ArrayList<>();
    for (int dir = 0; dir < directions().size(); dir++) {
      newNeighbors.add(neighbor(dir));
    }
    return newNeighbors;
  }

  /**
   * Gets the coordinate of this hexagon by inputting the coordinate.
   *
   * @param coord String that is one of q, r, s
   * @return the coordinate of this hexagon
   */

  public int getCoord(String coord) throws IllegalArgumentException {
    if (coord.equals("q")) {
      return this.q;
    }
    if (coord.equals("r")) {
      return this.r;
    }
    if (coord.equals("s")) {
      return this.s;
    } else {
      throw new IllegalArgumentException("Not a coordinate of a hexagon, must q r s");
    }

  }


  /**
   * Compares this hexagon with another object to check for equality.
   *
   * @param other The object to compare with this hexagon.
   * @return true if the provided object is a Hexagon and has the same q, r, and s coords
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other instanceof Hexagon) {
      Hexagon that = (Hexagon) other;
      return this.q == that.q && this.r == that.r && this.s == that.s;
    }
    return false;
  }


  /**
   * Generates a hash code with prime integers for this hexagon
   * based on its q, r, and s coordinates.
   *
   * @return a unique hash code value for this hexagon
   */

  @Override
  public int hashCode() {
    return Objects.hashCode(this.r * 31 + this.s * 17 + this.q * 37);
  }

  /**
   * Generates the coordinates to string of the hexagon.
   */
  @Override
  public String toString() {
    return "Hexagon: " + this.q + " " + this.r + " " + this.s;
  }

}

