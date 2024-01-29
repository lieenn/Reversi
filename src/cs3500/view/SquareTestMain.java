package cs3500.view;


import cs3500.controller.ReversiController;
import cs3500.controller.ReversiSquareController;
import cs3500.controller.players.AISquarePlayer;
import cs3500.controller.players.HumanPlayer;
import cs3500.controller.players.HumanSquarePlayer;
import cs3500.controller.players.IPlayer;
import cs3500.model.BasicReversi;
import cs3500.model.Square;
import cs3500.model.PlayerPiece;
import cs3500.model.Square;
import cs3500.model.MutableModel;
import cs3500.model.SquareReversi;
import cs3500.strategy.IMoveStrategy;

import cs3500.strategy.PromptUser;
import cs3500.strategy.SquareStrat1;
import cs3500.strategy.SquareStratHuman;
import cs3500.view.AReversiPanel;
import cs3500.view.SquarePanel;
import cs3500.view.ReversiFrame;
import cs3500.view.ReversiView;

/**
 * Runs Reversi and the user can click on the board to alternate colors if they so wish. They
 * can also play the board game, if they wish. They must choose two players first,
 * they can be one of "human", "strategy1", "strategy2", "strategy3", and "strategy4".
 * Note that strategy4 is arguable the hardest one to play against.
 */
public class SquareTestMain {

  /**
   * Runs the game of reversi. The logic is explained above.
   *
   * @param args of what you need to start the game. So far, you don't need any arguments.
   */
  public static void main(String[] args) {
    MutableModel<Square> mutableModel = new SquareReversi();
    mutableModel.startGame(mutableModel.makeBoard(6));

    AReversiPanel<Square> panel = new SquarePanel(mutableModel);
    AReversiPanel<Square> panel2 = new SquarePanel(mutableModel);

    ReversiView<Square> view1 = new SquareFrame(mutableModel, "Player 1", panel);
    ReversiView<Square> view2 = new SquareFrame(mutableModel, "Player 2", panel2);

    IMoveStrategy<Square> strat1 = new SquareStratHuman(view1);
    IMoveStrategy<Square> strat2 = new SquareStrat1(mutableModel);

    IPlayer<Square> player1 = new HumanSquarePlayer(PlayerPiece.X, strat2);
    IPlayer<Square> player2 = new AISquarePlayer(PlayerPiece.O, strat1);

    ReversiSquareController controller = new ReversiSquareController(mutableModel, player1, view1);
    ReversiSquareController controller1 = new ReversiSquareController(mutableModel, player2, view2);
    
  }

}



