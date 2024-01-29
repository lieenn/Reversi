package cs3500.view;

import java.util.Objects;

/**
 * Represents a point in a 2-D Cartesian coordinate system with integer coordinates (x, y).
 * We can initialize a point with specified x and y coordinates, get individual coordinates,
 * and can combine points to combine two points. We use this class to draw points on the window.
 */
public class PointXY {
  private int x;
  private int y;

  /**
   * Constructs a PointXY object with specified x and y coords.
   *
   * @param x x-coordinate of the point.
   * @param y y-coordinate of the point.
   */
  public PointXY(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-coordinate of the point.
   *
   * @return x-coordinate.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-coordinate of the point.
   *
   * @return y-coordinate.
   */
  public int getY() {
    return y;
  }

  /**
   * Adds the coordinates of another point to this point. Returns that new point. Does not mutate.
   *
   * @param otherPoint other point to be added.
   * @return a new PointXY with sum of coordinates.
   */
  public PointXY add(PointXY otherPoint) {
    int newX = this.x + otherPoint.x;
    int newY = this.y + otherPoint.y;
    return new PointXY(newX, newY);
  }

  /**
   * Checks if this point is equal to something else.
   *
   * @param other object to compare for equality.
   * @return if the objects are equal.
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other instanceof PointXY) {
      PointXY that = (PointXY) other;
      return this.x == that.x && this.y == that.y;
    }
    return false;
  }


  /**
   * THe hash code for this point.
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * The written version of PointXY.
   *
   * @return a string in the format "(x, y)".
   */
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
