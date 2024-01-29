package cs3500.view;

import java.awt.*;
import java.awt.geom.Point2D;

import cs3500.model.Hexagon;

public interface ITile<PShape> {
  /**
   * Draws the hexagon tile with Graphics class.
   *
   * @param g is the graphics we use to draw the hexagon tile out.
   */
  void draw(Graphics g);

  /**
   * If this hexagon tile contain the given point.
   *
   * @param point is the point you want to see if the hexagon has
   * @return If this hexagon tile contain the given point.
   */
  boolean containsPoint(Point2D point);

  /**
   * The windows coordinate-x of the screen.
   *
   * @return the x-coordinate.
   */

  int getWindowsX();

  /**
   * The windows coordinate-x of the screen.
   *
   * @return the x-coordinate.
   */
  int getWindowsY();

  /**
   * The current color of the hexagon tile.
   *
   * @return The current color of the hexagon tile.
   */
  Color getColor();

  /**
   * Sets the color of the hexagon tile.
   *
   * @param color of the hexagon tile.
   */

  void setColor(Color color);

  /**
   * The part of the coordinate.
   *
   * @param coord of the hexagon
   * @return the coordinate part of the hexagon
   */

  int getCoord(String coord);

  /**
   * Gets the PShape of this hexagonTile.
   * @return the PShape of this hexagonTile.
   */

  PShape getPShape();



  /**
   * Draws the disc on the board (the game pieces).
   *
   * @param g the graphics that draws this disc
   */
  void drawDisc(Graphics g);

  void setSelected(boolean selected);

  boolean isSelected();

  int getSide();




}
