package cs3500.textview;

import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;

/**
 * Textual rendering of model, so we can visualize the data
 * Comments that TA should use code to produce visualization.
 * Using the visualization: run model.toString()?
 */

public class ReversiSquareTextualView implements TextView {
  private final MutableModel<Square> model;

  public ReversiSquareTextualView(MutableModel<Square> model) {
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

    int size = model.getSize();

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        try {
          // put the Player.EMPTY...
          if (model.getPlayerAt(col, row) == PlayerPiece.EMPTY) {
            view.append(" _"); //placeholder
          } else if (model.getPlayerAt(col, row) == PlayerPiece.X) {
            view.append(" X");
          } else if (model.getPlayerAt(col, row) == PlayerPiece.O) {
            view.append(" O");
          }
          // null spaces, or Squares that just do not exist
        } catch (IllegalArgumentException e) {
          view.append(" ");
        }
      }
      view.append("\n");
    }
    return view.toString();
  }
}

