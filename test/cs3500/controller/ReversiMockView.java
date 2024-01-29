package cs3500.controller;

import java.awt.event.MouseListener;
import java.util.List;

import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;
import cs3500.model.ROModel;
import cs3500.view.HexagonTile;
import cs3500.view.HexagonPanel;
import cs3500.view.ITile;
import cs3500.view.ReversiView;

/**
 * A mock view for testing purposes of the controller. We make sure that the methods are
 * work with the tests.
 */
public class ReversiMockView implements ReversiView<Hexagon> {
  ROModel<Hexagon> model;

  /**
   * Constructor for the MockView such that it takes in the model, that is necessary for a panel.
   *
   * @param model that the panel in the mock view needs.
   */
  public ReversiMockView(ROModel<Hexagon> model) {
    this.model = model;
  }

  /**
   * Empty because of mock reasons.
   * @param hexagonTile where we place the disc on the screen.
   */
  @Override
  public void putDisc(ITile<Hexagon> hexagonTile) {
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
   * @param hexagonTile where we place the disc on the screen.
   */
  @Override
  public void clickHex(ITile<Hexagon> hexagonTile) {
    // empty because it's a mock

  }

  /**
   * Empty because of mock reasons.
   * @param listener where is listening.
   */
  @Override
  public void addPlayerActionListener(PlayerActionListener<Hexagon> listener) {
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
  public HexagonPanel getPanel() {
    return new HexagonPanel(model);
  }

  /**
   * Empty because of mock reasons.
   * @param legalMoves that the player can make
   */
  @Override
  public void setLegalMoves(List<Hexagon> legalMoves) {
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
