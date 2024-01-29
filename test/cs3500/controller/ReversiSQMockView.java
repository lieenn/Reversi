package cs3500.controller;

import java.awt.event.MouseListener;
import java.util.List;

import cs3500.model.Square;
import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;
import cs3500.view.SquareTile;
import cs3500.view.SquarePanel;
import cs3500.view.ITile;
import cs3500.view.ReversiView;

/**
 * A mock view for testing purposes of the controller. We make sure that the methods are
 * work with the tests.
 */
public class ReversiSQMockView implements ReversiView<Square> {
  ROModel<Square> model;

  /**
   * Constructor for the MockView such that it takes in the model, that is necessary for a panel.
   *
   * @param model that the panel in the mock view needs.
   */
  public ReversiSQMockView(ROModel<Square> model) {
    this.model = model;
  }

  /**
   * Empty because of mock reasons.
   * @param SquareTile where we place the disc on the screen.
   */
  @Override
  public void putDisc(ITile<Square> SquareTile) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void pass() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @param SquareTile where we place the disc on the screen.
   */
  @Override
  public void clickHex(ITile<Square> SquareTile) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @param listener where is listening.
   */
  @Override
  public void addPlayerActionListener(PlayerActionListener<Square> listener) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @param listener where is listening.
   */
  @Override
  public void addClickListener(MouseListener listener) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void display() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void isYourTurn() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void isNotYourTurn() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void notYourTurnYet() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @param piece that is gameOver for.
   */
  @Override
  public void gameOver(PlayerPiece piece) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void wrongMove() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void refresh() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @return the panel.
   */
  @Override
  public SquarePanel getPanel() {
    return new SquarePanel(model);
  }

  /**
   * Empty because of mock reasons.
   * @param legalMoves that the player can make
   */
  @Override
  public void setLegalMoves(List<Square> legalMoves) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   */
  @Override
  public void gottaPass() {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @param piece player that the panel is showing view for
   */
  @Override
  public void setPlayerPiece(PlayerPiece piece) {
    // empty because it's a mock

  }
}
