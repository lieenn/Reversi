package cs3500.view;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import cs3500.model.Hexagon;
import cs3500.model.ROModel;

/**
 * ReversiPanel represents the graphical user interface for the Reversi game board.
 * It provides a visual representation of the game state and handles user interactions w/ clicks.
 * Invariant: Throughout the entire game, there are always 1 or 0 white hexagons
 * (meaning it's clicked).
 */
public class HexagonPanel extends AReversiPanel<Hexagon> {


  /**
   * Constructs the panel for reversi so it can render the visual representation
   * of the game state and handles user interactions w/ clicks.
   *
   * @param model that is being displayed
   */
  public HexagonPanel(ROModel<Hexagon> model) {
    super(model);
    widthOfWindow = 660;
    heightOfWindow = 600;
    pShapeSize = (int) Math.floor(heightOfWindow / (1.5 * (model.getSize() * 2 + 1) + 0.5));


    horizontalSpacing = (int) Math.ceil(Math.sqrt(3) * pShapeSize);
    verticalSpacing = (int) Math.ceil((double) 3 / 2 * pShapeSize);
    setReversiBoard();
  }


  /**
   * Creates and returns a new PTile that is a neighbor of the given PTile.
   * The directions are: 0.East 1.Northeast 2.Northwest 3.West 4.SouthWest 5.SouthEast.
   *
   * @param direction  The direction (0-5) in which to find the neighbor:
   * @param currentHex The PTile for which to find the neighbor.
   * @return A new PTile representing the neighbor in the specified direction.
   */
  private ITile<Hexagon> createNeighborPShape(int direction, ITile<Hexagon> currentHex) {
    List<PointXY> directions = hexDirections();
    PointXY directionVector = directions.get(direction);
    Hexagon hex = currentHex.getPShape();
    int newX = currentHex.getWindowsX() + directionVector.getX();
    int newY = currentHex.getWindowsY() + directionVector.getY();
    return new HexagonTile(defaultColor, newX, newY, pShapeSize, hex.neighbor(direction));
  }

//  @Override
//  public Dimension getPreferredSize() {
//    return new Dimension(widthOfWindow, heightOfWindow);
//  }


  /**
   * Initializes the Reversi game board with a PShapeal pattern around the center.
   * The pShapeList is populated with PTiles in concentric rings, following a spiral pattern.
   */


  public void setReversiBoard() {
    int midWindowX = widthOfWindow / 2;
    int midWindowY = heightOfWindow / 2;
    int boardRadius = model.getSize();

    Hexagon center = new Hexagon(0, 0, 0);
    ITile<Hexagon> midHex = new HexagonTile(defaultColor, midWindowX, midWindowY, pShapeSize, center);
    pShapeList.add(midHex);// (the center PShape)
    ITile<Hexagon> currentHex = createNeighborPShape(4, midHex);
    pShapeList.add(currentHex);// (the SW PShape of the center)

    //iterates through the rings in the starting from SW direction
    for (int ring = 0; ring < boardRadius + 1; ring++) {
      for (int dir = 0; dir < 6; dir++) {
        for (int movesInDirection = 0; movesInDirection < ring; movesInDirection++) {
          // get the last PShape (added) in the list as the currentHex
          currentHex = pShapeList.get(pShapeList.size() - 1);
          // create the next PShape and add it to the list
          ITile<Hexagon> nextHex = createNeighborPShape(dir, currentHex);
          if (pShapeList.contains(nextHex)) {
            // basically goes straight down
            if (ring != boardRadius) {
              nextHex = createNeighborPShape(4, nextHex);
              pShapeList.add(nextHex);
            }
            dir = 6;
            break;
          } else {
            pShapeList.add(nextHex);
          }
        }
      }
    }
  }

  /**
   * Returns a list of vector directions for six cardinal directions in a PShapeal grid.
   * 0.East 1.Northeast 2.Northwest 3.West 4.SouthWest 5.SouthEast.
   *
   * @return List of PointXY representing vector directions.
   */
  private List<PointXY> hexDirections() {
    List<PointXY> directions = new ArrayList<>();
    directions.add(new PointXY(horizontalSpacing, 0));
    directions.add(new PointXY(horizontalSpacing / 2, -verticalSpacing));
    directions.add(new PointXY(-horizontalSpacing / 2, -verticalSpacing));
    directions.add(new PointXY(-horizontalSpacing, 0));
    directions.add(new PointXY(-horizontalSpacing / 2, verticalSpacing));
    directions.add(new PointXY(horizontalSpacing / 2, verticalSpacing));
    return directions;
  }




}
