package cs3500.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import cs3500.Reversi;
//import cs3500.controller.PlayerActionListener;
import cs3500.controller.PlayerActionListener;
import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;


/**
 * Attaches everything from the panel to the caption to the game. Displays the GUI of the game.
 */
public class ReversiFrame extends JFrame implements ReversiView<Hexagon> {
  private final AReversiPanel<Hexagon> panel;
  private final JLabel userTurn;
  private final JLabel instructions1;
  private final JLabel instructions2;
  // ending screen can change
  private JLabel theEnd;
  // can change, adds over time
  private PlayerActionListener<Hexagon> playerActionlistener;
  // changes due to constant switching of the board (changes turn)
  private PlayerPiece piece;

  // the model that is relied on by view.
  private final ROModel<Hexagon> model;

  /**
   * The model that will be passed into the frame will be ROModel and the caption will be "Reversi".
   *
   * @param model   the roModel that the user wants to place.
   * @param caption the caption.
   */
  public ReversiFrame(ROModel<Hexagon> model, String caption, AReversiPanel<Hexagon> panel) {
    super(caption);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.model = model;
    // this.panel = new ReversiPanel(model);
    this.panel = panel;
    this.add(panel);
    this.addClickListener(panel.getListener());
    this.addKeyListener(panel.getKeyListener());
    this.add(panel, BorderLayout.CENTER);

    instructions1 = new JLabel("LegalMoves are highlighted. " +
            "If you don't see any legalMoves for your turn, click the empty space.");
    instructions1.setOpaque(true);
    instructions1.setBackground(Color.BLACK);
    instructions1.setForeground(Color.WHITE);

    instructions2 = new JLabel("Click on a hexagon, press 'enter' to make your move. " +
            "Press 'p' to pass");
    instructions2.setBackground(Color.BLACK);
    instructions2.setForeground(Color.WHITE);

    JPanel instructionPanel = new JPanel();
    instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
    instructionPanel.add(instructions1);
    instructionPanel.add(instructions2);
    instructionPanel.setBackground(Color.BLACK);
    this.add(instructionPanel, BorderLayout.SOUTH);

    userTurn = new JLabel(" ");
    this.add(userTurn, BorderLayout.NORTH);
    userTurn.setOpaque(true);
    userTurn.setBackground(Color.BLACK);
    userTurn.setForeground(Color.WHITE);
    userTurn.setFont(new Font("Arial", Font.PLAIN, 35));
    userTurn.setVisible(true);

    this.pack();
    display();
  }

  @Override
  public void addClickListener(MouseListener listener) {
    panel.addClickListener(listener);
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void putDisc(ITile<Hexagon> hexagonTile) {
    playerActionlistener.onPlayerPutDisc(hexagonTile);
    panel.repaint();
  }

  // DESIGN DECISION TO HAVE THE PLAYER PRESS "OK" FOR THE AI PLAYER BECAUSE WE WANT TO LET THEM
  // KNOW THAT THEY PASS
  @Override
  public void pass() {
    if (piece != model.getCurrentPlayer()) {
      notYourTurnYet();
      return;
    }
    JOptionPane.showMessageDialog(this, "Well, you did pass. ",
            "Strategic pass?", JOptionPane.INFORMATION_MESSAGE);
    playerActionlistener.onPlayerPass();
    panel.repaint();
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener<Hexagon> listener) {
    this.playerActionlistener = listener;
  }

  @Override
  public void clickHex(ITile<Hexagon> hexagonTile) {
    if (piece != model.getCurrentPlayer()) {
      notYourTurnYet();
    }
    playerActionlistener.onPlayerClick(hexagonTile);
    panel.repaint();
  }

  //  enable them to move, i was thinking we can let the
  //  player know what color they are and their current score
  @Override
  public void isYourTurn() {
    this.userTurn.setText("Your turn.   Color: " + colorToString() + ". " +
            "   Score: " + model.getPlayerScore(piece));
    panel.repaint();
  }

  /**
   * Gets the color of the string.
   * @return the color in string format.
   */
  private String colorToString() {
    if (this.piece == PlayerPiece.X) {
      return "Black";
    } else {
      return "Red";
    }
  }

  // enable them to not move
  @Override
  public void isNotYourTurn() {
    this.userTurn.setText("Not your turn.   Color: " + colorToString() + ". " +
            "   Score: " + model.getPlayerScore(piece));
    // i changed it here so that if it's
    panel.currentHexClicked = null;
    panel.repaint();
  }

  @Override
  public void notYourTurnYet() {
    JOptionPane.showMessageDialog(this, "It's not your turn!",
            "Just wait", JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void gottaPass() {
    JOptionPane.showMessageDialog(this, "No moves, forcing you to pass",
            "Strategic pass?", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void gameOver(PlayerPiece piece) {
    this.instructions1.setVisible(false);
    this.instructions2.setVisible(false);
    this.userTurn.setVisible(false);

    // ending screen can change
    JPanel endScene = new JPanel(new FlowLayout(FlowLayout.CENTER));
    theEnd = new JLabel(" ");
    theEnd.setBackground(Color.BLACK);
    theEnd.setForeground(Color.WHITE);
    theEnd.setFont(new Font("Arial", Font.PLAIN, 40));
    theEnd.setHorizontalAlignment(JLabel.CENTER);
    theEnd.setVerticalAlignment(JLabel.CENTER);
    endScene.add(theEnd);
    endScene.setBackground(Color.BLACK);
    this.add(endScene, BorderLayout.NORTH);

    helperGameOver(piece);
    endScene.setVisible(true);

  }

  /**
   * Helpers for game over.
   * @param piece that is being used for screen.
   */
  private void helperGameOver(PlayerPiece piece) {
    try {
      if (piece == model.getWinner()) {
        theEnd.setText("Game Over, You win!");
      } else {
        theEnd.setText("Game Over, You Lost!");
      }
    } catch (IllegalStateException e) {
      theEnd.setText("Game Over, Stalemate");
    }
  }

  @Override
  public void wrongMove() {
    JOptionPane.showMessageDialog(this, "Try again",
            "Not a valid move", JOptionPane.WARNING_MESSAGE);
    panel.repaint();
  }

  @Override
  public void refresh() {
    panel.repaint();
    this.repaint();
  }

  @Override
  public AReversiPanel<Hexagon> getPanel() {
    return panel;
  }

  @Override
  public void setLegalMoves(List<Hexagon> legalMoves) {
    // legal moves can change over time.
    this.panel.setLegalMoves(legalMoves);
  }

  public void setPlayerPiece(PlayerPiece piece) {
    this.piece = piece;
    panel.setPlayerPiece(piece);
  }


}