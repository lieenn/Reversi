package cs3500.model;

import java.util.List;

/**
 * Common interface for Square and Hexagon.
 */
public interface IShape<PShape> {
  /**
   * Adds the coordinates of another IShape to this IShape, resulting in a new IShape.
   *
   * @param other The IShape to add.
   * @return A new IShape resulting from the addition.
   */
  PShape add(PShape other);

  /**
   * Subtracts the coordinates of another IShape from this IShape, resulting in a new IShape.
   *
   * @param other The IShape to subtract.
   * @return A new IShape resulting from the subtraction.
   */

  PShape subtract(PShape other);

  /**
   * List of IShape vectors representing different directions in a grid.
   *
   * @return list of directions.
   */
  List<PShape> directions();

  /**
   * Retrieves the neighboring IShape in a specified direction.
   *
   * @param direction An integer representing the direction to find the neighbor.
   * @return A new IShape representing the neighbor in the specified direction.
   */
  PShape neighbor(int direction);

  /**
   * All the neighbors of the IShape.
   *
   * @return all the closest neighbors of this IShape
   */
  List<PShape> getNeighbors();


  /**
   * Gets the coordinate of this IShape by inputting the coordinate.
   *
   * @param coord String that is one of "x" or "y"
   * @return the coordinate of this IShape
   */
  int getCoord(String coord);
}
