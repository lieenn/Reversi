package cs3500.textview;

import cs3500.model.Hexagon;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;

/**
 * Textual rendering of model, so we can visualize the data
 * Comments that TA should use code to produce visualization.
 * Using the visualization: run model.toString()?
 */

public class ReversiTextualView implements TextView {
  private final MutableModel<Hexagon> model;

  public ReversiTextualView(MutableModel<Hexagon> model) {
    this.model = model;
  }

  /**
   * Return the textual view of the current state of the Reversi board.
   * Display the board with "_" for empty cells, "X" for black discs, and "O" for white discs.
   *
   * @return String representing the board state.
   */
  public String toString() {
    StringBuilder view = new StringBuilder();

    int radius = (model.getSizeTimesTwoPlusOne() - 1) / 2;

    for (int row = 0; row < model.getSizeTimesTwoPlusOne(); row++) {
      // bottom half of the screen
      if (row > radius) {
        // add spaces for rows after you reach the diameter
        int spacesToAdd = row - radius;
        view.append(" ".repeat(spacesToAdd));
      }

      for (int col = 0; col < model.getSizeTimesTwoPlusOne(); col++) {
        try {
          // put the Player.EMPTY...
          if (model.getPlayerAt(col, row) == PlayerPiece.EMPTY) {
            view.append(" _"); //placeholder
          } else if (model.getPlayerAt(col, row) == PlayerPiece.X) {
            view.append(" X");
          } else if (model.getPlayerAt(col, row) == PlayerPiece.O) {
            view.append(" O");
          }
          // null spaces, or hexagons that just do not exist
        } catch (IllegalArgumentException e) {
          view.append(" ");
        }
      }
      view.append("\n");
    }
    return view.toString();
  }
}

