package cs3500.view;

import java.awt.*;

import cs3500.model.ROModel;
import cs3500.model.Square;

public class SquarePanel extends AReversiPanel<Square> {
  /**
   * Constructs the panel for reversi so it can render the visual representation
   * of the game state and handles user interactions w/ clicks.
   *
   * @param model that is being displayed
   */
  public SquarePanel(ROModel<Square> model) {
    super(model);

    widthOfWindow = 600;
    heightOfWindow = 600;

    pShapeSize = heightOfWindow / model.getSize();
    setReversiBoard();

  }


  public void setReversiBoard() {
    int size = model.getSize();
//    int topLeftX = pShapeSize / 2;
//    int topLeftY = pShapeSize / 2;
        int topLeftX = 0;
    int topLeftY = 0;

    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        Square square = new Square(x, y);
        ITile<Square> tile = new SquareTile(defaultColor, topLeftX + (x * pShapeSize),
                topLeftY + (y * pShapeSize), pShapeSize, square);
        pShapeList.add(tile);
      }
    }
  }
}
