package cs3500.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;
import cs3500.strategy.UserChoiceListener;

/**
 * ReversiPanel represents the graphical user interface for the Reversi game board.
 * It provides a visual representation of the game state and handles user interactions w/ clicks.
 * Invariant: Throughout the entire game, there are always 1 or 0 white PShapes
 * (meaning it's clicked).
 */
public abstract class AReversiPanel<PShape> extends JPanel {
  protected static int pShapeSize;

//  protected static int hexRadius;
  protected static int horizontalSpacing;
  protected static int verticalSpacing;
  protected static int widthOfWindow;
  protected static int heightOfWindow;
  protected final MouseEventsListener listener;
  protected final KeyEventsListener keyListener;
  protected final Color defaultColor = Color.PINK;
  protected final Color changedColor = Color.WHITE;
  protected final Color highlight = new Color(255, 200, 200);
  protected final Color xPlayer = Color.BLACK;
  protected final Color oPlayer = Color.RED;
  protected final Color empty = new Color(0, 0, 0, 0);
  protected final ROModel<PShape> model;
  protected final List<ITile<PShape>> pShapeList;
  // changes over the game
  protected ITile<PShape> currentHexClicked;
  // changes over the game

  protected List<PShape> legalMoves;
  // changes over the game

  protected PlayerPiece piece;
  // changes over the game

  protected boolean pShapeIsSelected;
  // changes as we initialize it later.

  protected UserChoiceListener<PShape> userListener;

  /**
   * Constructs the panel for reversi so it can render the visual representation
   * of the game state and handles user interactions w/ clicks.
   *
   * @param model that is being displayed
   */
  public AReversiPanel(ROModel<PShape> model) {
    this.model = Objects.requireNonNull(model);

//    hexRadius = (int) Math.floor(heightOfWindow / (1.5 * (model.getSize() * 2 + 1) + 0.5));
//    setPreferredSize(new Dimension(widthOfWindow, heightOfWindow));
//
//    horizontalSpacing = (int) Math.ceil(Math.sqrt(3) * hexRadius);
//    verticalSpacing = (int) Math.ceil((double) 3 / 2 * hexRadius);
    setBackground(Color.BLACK);
    this.pShapeList = new ArrayList<>();
    listener = new MouseEventsListener();
    keyListener = new KeyEventsListener();
    //PShape currentHexClicked = new PShape(0,0,0);

    //setting playerX as default since no player exists yet until controller is instantiated
    this.piece = PlayerPiece.X;
    this.legalMoves = model.getLegalMoves(piece);

  }


  public void setUserChoiceListener(UserChoiceListener<PShape> userListener) {
    this.userListener = userListener;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(widthOfWindow, heightOfWindow);
  }


  /**
   * Paints the graphical representation of the Reversi game board.
   *
   * @param g the Graphics object that draws.
   */

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    for (ITile<PShape> tile : pShapeList) {
      PShape pShape = tile.getPShape();
      PlayerPiece playerPiece = model.getPlayerAt(pShape);

      tile.draw(g);
      if (playerPiece == PlayerPiece.X) {
        g.setColor(xPlayer);
      }
      if (playerPiece == PlayerPiece.O) {
        g.setColor(oPlayer);
      }
      if (playerPiece == PlayerPiece.EMPTY) {
        g.setColor(empty);
      }
      tile.drawDisc(g);
      // if hintsOnScreen = true;
      // tile.drawLegalMoves(g);
      // else return;
    }
    if (piece == model.getCurrentPlayer()) {
      resetColor();
    }
  }

  /**
   * Converts a PShape into a PShape tile.
   *
   * @param pshapeJennifer that is being converted.
   * @return the PShapes (which is in the list of ITile<PShape>)
   */

  public ITile<PShape> pshapeToTile(PShape pshapeJennifer) {
    ITile<PShape> tile = null;
    for (ITile<PShape> ptile : pShapeList) {
      if (ptile.getPShape().equals(pshapeJennifer)) {
        tile = ptile;
      }
    }
    return tile;
  }

  public abstract void setReversiBoard();






    public void setLegalMoves(List<PShape> legalMoves) {
    this.legalMoves = legalMoves;
  }

  /**
   * Set non legalMoves as default color and highlight legalMoves.
   */
  protected void resetColor() {
    for (ITile<PShape> tile : pShapeList) {
      resetHex(tile);
    }
  }

  /**
   * Set non legalMoves as default color and highlight legalMoves.
   */
  protected void resetHex(ITile<PShape> tile) {
    PShape PShape = tile.getPShape();
    legalMoves = model.getLegalMoves(piece);
    if (legalMoves.contains(PShape)) {
      tile.setColor(highlight);
    }
    if (legalMoves.contains(tile.getPShape())) {
      tile.setColor(highlight);
    }
  }


  /**
   * Adds a click listener to the ReversiPanel.
   *
   * @param listener The mouse click listener to be added.
   */


  public void addClickListener(MouseListener listener) {
    addMouseListener(listener);
  }

  /**
   * Returns the MouseEventsListener associated with this ReversiPanel.
   *
   * @return The MouseEventsListener instance.
   */
  public MouseEventsListener getListener() {
    return this.listener;
  }

  /**
   * Set the PlayerPiece for the panel so the panel can use to check with the
   * model about the player.
   *
   * @param piece player that the panel is showing view for
   */
  public void setPlayerPiece(PlayerPiece piece) {
    this.piece = piece;
  }

  public KeyListener getKeyListener() {
    return this.keyListener;
  }



    /**
     * The MouseEventListener handles what goes on in the GUI when a mouse clicks on the board.
     */
  protected class MouseEventsListener extends MouseInputAdapter {
    MouseEventsListener() {
      super();
    }

    /**
     * Handles mouse clicks on the ReversiPanel.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      Point pointClicked = e.getPoint();
      ITile<PShape> clickedPShape = findClickedPShape(pointClicked);
      if (!model.isGameContinuing()) {
        return;
      }
      if (piece != model.getCurrentPlayer()) {
        userListener.onUserClicking(clickedPShape);
        return;
      }
      if (clickedPShape != null) {
        handlePShapeClick(clickedPShape);
        System.out.println(clickedPShape);
        currentHexClicked = clickedPShape;
        userListener.onUserClicking(clickedPShape);
        repaint();
      } else {
        // change state -> repaint the clicked PShape
        unselectPShape();

        repaint();
      }
    }

    /**
     * Finds the ITile<PShape> that was clicked based on the given point.
     *
     * @param point The point where the mouse click occurred.
     * @return The ITile<PShape> that was clicked, or null if no PShape was clicked.
     */

    protected ITile<PShape> findClickedPShape(Point point) {
      for (ITile<PShape> tile : pShapeList) {
        if (tile.containsPoint(point)) {
          return tile;
        }
      }
      return null;
    }

    /**
     * Handles the logic for a click on a PShape - toggling its color
     * and if it's clicked or not.
     *
     * @param clickedPShape The ITile<PShape> that was clicked.
     */
    protected void handlePShapeClick(ITile<PShape> clickedPShape) {
      if (pShapeIsSelected) {
        ITile<PShape> selectedPShape = getSelectedPShape();
        // check if the clicked PShape is the currently selected one
        if (clickedPShape.equals(selectedPShape)) {
          // if yes, reset the color
          if (!legalMoves.contains(clickedPShape.getPShape())) {
            clickedPShape.setColor(defaultColor);
          }
          if (legalMoves.contains(clickedPShape.getPShape())) {
            clickedPShape.setColor(highlight);
          }
          clickedPShape.setSelected(false);
          pShapeIsSelected = false;
        } else {
          // deselect the  selected PShape and select the clicked one
          unselectPreviousPShape();
          changeHexColor(clickedPShape);
        }
      } else {
        // if no PShape is currently selected select the clicked one
        changeHexColor(clickedPShape);
      }
      //highlightLegalMoves();
      repaint();
    }

    /**
     * Deselects the previously selected PShape by setting its color to pink
     * and updating the selection state.
     */
    protected void unselectPreviousPShape() {
      for (ITile<PShape> tile : pShapeList) {
        if (tile.isSelected()) {
          if (!legalMoves.contains(tile.getPShape())) {
            tile.setColor(defaultColor);
          }
          if (legalMoves.contains(tile.getPShape())) {
            tile.setColor(highlight);
          }
          tile.setSelected(false);
          break;
        }
      }
    }

    /**
     * Toggles the color of the specified PShape between pink and white,
     * updating the selection state accordingly.
     *
     * @param pShapeTileBob The ITile<PShape> to toggle the color of.
     */
    protected void changeHexColor(ITile<PShape> pShapeTileBob) {
      if (pShapeTileBob.getColor() == defaultColor || pShapeTileBob.getColor() == highlight) {
        pShapeTileBob.setColor(changedColor);
        pShapeTileBob.setSelected(true);
        pShapeIsSelected = true;
      } else {
        pShapeTileBob.setColor(defaultColor);
        pShapeTileBob.setSelected(false);
        pShapeIsSelected = false;
        resetColor();
      }
    }

    /**
     * Unselects the currently selected PShape by setting its color to pink
     * and updating the selection state.
     */
    protected void unselectPShape() {
      for (ITile<PShape> tile : pShapeList) {
        if (tile.isSelected()) {
          if (!legalMoves.contains(tile.getPShape())) {
            tile.setColor(defaultColor);
          }
          if (legalMoves.contains(tile.getPShape())) {
            tile.setColor(highlight);
          }
          tile.setSelected(false);
          pShapeIsSelected = false;

          break;
        }
      }
    }

    /**
     * Gets the currently selected PShape.
     *
     * @return The currently selected ITile<PShape>, or null if none is selected.
     */
    protected ITile<PShape> getSelectedPShape() {
      for (ITile<PShape> tile : pShapeList) {
        if (tile.isSelected()) {
          return tile;
        }
      }
      return null;
    }
  }


  protected class KeyEventsListener extends KeyAdapter {
    KeyEventsListener() {
      super();
    }

    /**
     * Processes the key input, activates the event.
     *
     * @param e the event to be processed when you press one of 'enter', 'p', or 'h'.
     */
    @Override
    public void keyTyped(KeyEvent e) {
      if (e.getKeyChar() == '\n') {
        // if not clicked -> dont do stuff
        if (piece != model.getCurrentPlayer()) {
          userListener.onUserPassing();
          return;
        }
        if (currentHexClicked == null || !pShapeIsSelected) {
          return;
        } else {
          // else do stuff
          System.out.println("Player chose " + currentHexClicked);
          userListener.onUserChoosing(currentHexClicked);
        }
      }
      if (e.getKeyChar() == 'p') {
        System.out.println("Player passed");
        userListener.onUserPassing();
      }

      for (ITile<PShape> tile : pShapeList) {
        tile.setColor(defaultColor);
      }

      // boolean hintsOnScreen
      //if (e.getKeyChar() == 'h') {
        // if hintsOnScreen = false
        //System.out.println("Player enabled hints");
        // hintsOnScreen = true
        // else System.out.println("Player disabled hints");
        //
      }
    }



}
