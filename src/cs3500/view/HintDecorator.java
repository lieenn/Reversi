package cs3500.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;

/**
 * We decided to make the design decision to get rid of hints for tiles that are already
 * have a disc. Check Piazza Post @2005.A HintDecorator decorates the Hexagon Panel with hints,
 * and is enabled with the key 'h' and also disabled with the key 'h.'
 */
public class HintDecorator extends HexagonPanel {
  private boolean hintMode = false;
  private final ROModel<Hexagon> model;
  private final KeyEventsListener keyHListener;


  /**
   * Constructs the panel for reversi so it can render the visual representation
   * of the game state and handles user interactions w/ clicks.
   *
   * @param model that is being displayed
   */
  public HintDecorator(ROModel<Hexagon> model) {
    super(model);
    this.model = model;
    keyHListener = new HintKeyAdapter();

  }

  public void setHintMode(boolean enable) {
    hintMode = enable;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // also has to be on player's turn
    if (hintMode && currentHexClicked != null && pShapeIsSelected
            && model.getCurrentPlayer() == piece
            && model.getHexamap().get(currentHexClicked.getPShape()) == PlayerPiece.EMPTY) {
      int numberSize = currentHexClicked.getSide();

      Color oldColor = currentHexClicked.getColor();
      Color numberColor = Color.BLACK;

      // position of number
      int numberX = currentHexClicked.getWindowsX() - numberSize/4;
      int numberY = currentHexClicked.getWindowsY() + numberSize/4;

      Hexagon currentHex = currentHexClicked.getPShape();
      int boardQ = currentHex.getCoord("q") + model.getSize();
      int boardR = currentHex.getCoord("r") + model.getSize();
      g.setColor(numberColor);
      g.setFont(new Font("Arial", Font.PLAIN, numberSize));

      try {
        g.drawString(String.valueOf(model.getDiscsToFlip(boardQ, boardR).size()), numberX, numberY);
      } catch (IllegalArgumentException e) {
        g.drawString("0", numberX, numberY);
      }
      g.setColor(oldColor);

      g.dispose();
    }
  }


  /**
   * Adding a ClickListener for to the super class
   *
   * @param listener The mouse click listener to be added.
   */
  @Override
  public void addClickListener(MouseListener listener) {
    super.addClickListener(listener);
  }

  /**
   * Get the KeyListener that only listens to the 'h' key
   *
   * @return key listener
   */
  @Override
  public KeyListener getKeyListener() {
    return this.keyHListener;
  }


  private void drawLegalMove(HexagonTile tile, Graphics g, String number) {
    Graphics2D g2d = (Graphics2D) g.create();
    int numberSize = tile.side;

    Color oldColor = tile.color;
    Color numberColor = Color.BLACK;

    // position of number
    int numberX = tile.windowsX - numberSize / 2;
    int numberY = tile.windowsY - numberSize / 2;

    g.drawString(number, numberX, numberY);

    g2d.setColor(oldColor);

    g2d.dispose();
  }

  private class HintKeyAdapter extends KeyEventsListener {
    HintKeyAdapter() {
      super();
    }

    @Override
    public void keyTyped(KeyEvent e) {
      super.keyTyped(e);
      if (e.getKeyChar() == 'h' && model.getCurrentPlayer() == piece) {
        System.out.println("Hints abled");
        setHintMode(!hintMode);
      }

      // if hintMode is disabled -> every hex should NOT have a number
      if (!hintMode) {
        pShapeIsSelected = false;
        for (ITile<Hexagon> tile : pShapeList) {
          tile.setColor(defaultColor);
        }
      }
    }
  }
}
