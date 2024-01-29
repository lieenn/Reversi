package cs3500.view;

import java.awt.*;

import cs3500.model.Square;

/**
 * A SquareTile is an individual Square to be drawn on the board. This is also a button that
 * can be clicked on and will change color depending on where you click.
 */
public class SquareTile extends AbstractTile<Square> {

  private final Square square;

  /**
   * Constructs a SquareTile with a color, x and y coordinates, size, and Square coordinates.
   *
   * @param color color of the Square (can't be null).
   * @param x     x-coordinate of the center of the window.
   * @param y     y-coordinate of the center of the window.
   * @param side  size of the Square tile.
   * @param square   Square coordinates (cube coord).
   */
  public SquareTile(Color color, int x, int y, int side, Square square) {
    super(color, x, y, side, square);
    this.square = square;
  }

  @Override
  protected Polygon createTile(int x, int y, int size) {
    Polygon square = new Polygon();

    // Define the corners of the square in clockwise order
    square.addPoint(x, y);
    square.addPoint(x + size, y);
    square.addPoint(x + size, y + size);
    square.addPoint(x, y + size);

    return square;
  }

  @Override
  public Square getPShape() {
    return new Square(this.square.getX(), this.square.getY());
  }

  @Override
  public int getCoord(String coord) {
    return this.square.getCoord(coord);
  }

  @Override
  public void drawDisc(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    int discSize = side * 2 / 3;
    Color oldColor = this.color;

    // position of circle
    int discX = windowsX + discSize / 4;
    int discY = windowsY + discSize / 4;

    g2d.fillOval(discX, discY, discSize, discSize);

    g2d.setColor(oldColor);
    g2d.dispose();
  }
}
