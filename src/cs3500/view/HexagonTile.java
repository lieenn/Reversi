package cs3500.view;

import java.awt.*;

import cs3500.model.Hexagon;

/**
 * A HexagonTile is an individual hexagon to be drawn on the board. This is also a button that
 * can be clicked on and will change color depending on where you click.
 */
public class HexagonTile extends AbstractTile<Hexagon> {

  private final Hexagon hex;

  /**
   * Constructs a HexagonTile with a color, x and y coordinates, size, and Hexagon coordinates.
   *
   * @param color color of the hexagon (can't be null).
   * @param x     x-coordinate of the center of the window.
   * @param y     y-coordinate of the center of the window.
   * @param side  size of the hexagon tile.
   * @param hex   Hexagon coordinates (cube coord).
   */
  public HexagonTile(Color color, int x, int y, int side, Hexagon hex) {
    super(color, x, y, side, hex);
    this.hex = hex;
  }

  @Override
  protected Polygon createTile(int x, int y, int size) {
    Polygon hexagon = new Polygon();
    for (int corner = 0; corner < 6; corner++) {
      double angle = 2.0 * Math.PI / 6 * corner + Math.PI / 2;  // Rotate by 90 degrees
      int xPos = x + (int) Math.ceil((size * Math.cos(angle)));
      int yPos = y + (int) Math.ceil((size * Math.sin(angle)));
      hexagon.addPoint(xPos, yPos);
    }
    return hexagon;
  }

  @Override
  public Hexagon getPShape() {
    return new Hexagon(this.hex.getCoord("q"), this.hex.getCoord("r"), this.hex.getCoord("s"));
  }

  @Override
  public int getCoord(String coord) {
    return this.hex.getCoord(coord);
  }

  @Override
  public void drawDisc(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    int discSize = side;
    Color oldColor = this.color;

    // position of circle
    int discX = windowsX - discSize / 2;
    int discY = windowsY - discSize / 2;

    g2d.fillOval(discX, discY, discSize, discSize);

    g2d.setColor(oldColor);
    g2d.dispose();
  }
}
