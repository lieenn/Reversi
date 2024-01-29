package cs3500.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Objects;

import javax.swing.JButton;

/**
 * AbstractTile is a base class for individual tiles to be drawn on the board.
 * It is also a button that can be clicked on and will change color depending on where you click.
 *
 * @param <T> the type of shape (e.g., Hexagon, Square)
 */
public abstract class AbstractTile<T> extends JButton implements ITile<T> {

  protected final int windowsX;
  protected final int windowsY;
  protected final int side;
  protected final Polygon tileShape;
  protected Color color;

  /**
   * Constructs an AbstractTile with a color, x and y coordinates, size, and shape coordinates.
   *
   * @param color color of the tile (can't be null).
   * @param x     x-coordinate of the center of the window.
   * @param y     y-coordinate of the center of the window.
   * @param side  size of the tile.
   * @param shape shape coordinates (cube coord).
   * @throws IllegalArgumentException If x, y, or size is negative.
   */
  public AbstractTile(Color color, int x, int y, int side, T shape) {
    if (x < 0 || y < 0 || side < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative.");
    }
    this.color = Objects.requireNonNull(color);
    this.windowsX = x;
    this.windowsY = y;
    this.side = side;

    this.tileShape = createTile(x, y, side);
  }

  /**
   * Creates a tile polygon based on the specified top-left corner coordinates and size.
   *
   * @param x    x-coordinate of the center.
   * @param y    y-coordinate of the center.
   * @param size size of the tile.
   * @return tile polygon on the window.
   */
  protected abstract Polygon createTile(int x, int y, int size);

  /**
   * Draws the tile with Graphics class.
   *
   * @param g is the graphics we use to draw the tile out.
   */
  public void draw(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();
    Color oldColor = this.color;
    g.setColor(Color.BLACK);
    g.drawPolygon(tileShape);
    g.setColor(oldColor);
    g.fillPolygon(tileShape);
    g.setColor(oldColor);
  }

  /**
   * If this tile contains the given point.
   *
   * @param point is the point you want to see if the tile has
   * @return If this tile contains the given point.
   */
  public boolean containsPoint(Point2D point) {
    return tileShape.contains(point);
  }

  /**
   * The windows coordinate-x of the screen.
   *
   * @return the x-coordinate.
   */
  public int getWindowsX() {
    return this.windowsX;
  }

  /**
   * The windows coordinate-x of the screen.
   *
   * @return the x-coordinate.
   */
  public int getWindowsY() {
    return this.windowsY;
  }

  /**
   * If this is equal to something else.
   *
   * @param other that you compare this to.
   * @return If this is equal to something else.
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other instanceof AbstractTile) {
      AbstractTile<?> that = (AbstractTile<?>) other;
      return this.tileShape.equals(that.tileShape);
    }
    return false;
  }

  /**
   * The hashcode of this tile.
   *
   * @return If this is equal to something else.
   */
  @Override
  public int hashCode() {
    return this.tileShape.hashCode() * 31;
  }

  /**
   * Prints out the cube coordinates of this tile on the board.
   *
   * @return cubed coordinates of the tile
   */
  @Override
  public String toString() {
    return tileShape.toString();
  }

  /**
   * The current color of the tile.
   *
   * @return The current color of the tile.
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Sets the color of the tile.
   *
   * @param color of the tile.
   */
  public void setColor(Color color) {
    this.color = Objects.requireNonNull(color);
  }

  /**
   * Gets the PShape of this tile.
   *
   * @return the PShape of this tile.
   */
  public abstract T getPShape();

  /**
   * Gets the size of this tile.
   *
   * @return The current size of the tile.
   */
  public int getSide() {
    return this.side;
  }

  /**
   * Gets the part of the coordinate.
   *
   * @param coord of the tile
   * @return the coordinate part of the tile
   */
  public abstract int getCoord(String coord);

  /**
   * Draws the disc on the board (the game pieces).
   *
   * @param g the graphics that draws this disc
   */
  public abstract void drawDisc(Graphics g);
}
