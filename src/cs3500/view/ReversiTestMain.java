package cs3500.view;

import cs3500.controller.ReversiController;
import cs3500.controller.players.HumanPlayer;
import cs3500.controller.players.IPlayer;
import cs3500.model.BasicReversi;
import cs3500.model.Hexagon;
import cs3500.model.MutableModel;
import cs3500.model.PlayerPiece;
import cs3500.strategy.IMoveStrategy;

import cs3500.strategy.PromptUser;
import cs3500.view.AReversiPanel;
import cs3500.view.HexagonPanel;
import cs3500.view.ReversiFrame;
import cs3500.view.ReversiView;

/**
 * Runs Reversi and the user can click on the board to alternate colors if they so wish. They
 * can also play the board game, if they wish. They must choose two players first,
 * they can be one of "human", "strategy1", "strategy2", "strategy3", and "strategy4".
 * Note that strategy4 is arguable the hardest one to play against.
 */
public class ReversiTestMain {

  /**
   * Runs the game of reversi. The logic is explained above.
   *
   * @param args of what you need to start the game. So far, you don't need any arguments.
   */
  public static void main(String[] args) {


    // mutable model so that we can make board + startgame
    // but in our actual main we wont be doing this... what should we do?
    // thinking if we stick with this, then in the controller,
    // we call mutable start game -> force it back to be RO

    MutableModel<Hexagon> mutableModel = new BasicReversi();
    mutableModel.startGame(mutableModel.makeBoard(3));
    AReversiPanel<Hexagon> panel = new HexagonPanel(mutableModel);
    AReversiPanel<Hexagon> panel2 = new HintDecorator(mutableModel);

    ReversiView<Hexagon> view1 = new ReversiFrame(mutableModel, "Player 1", panel);
    ReversiView<Hexagon> view2 = new ReversiFrame(mutableModel, "Player 2", panel2);

    IMoveStrategy<Hexagon> strat1 = new PromptUser(view1);
    IMoveStrategy<Hexagon> strat2 = new PromptUser(view2);

    IPlayer<Hexagon> player1 = new HumanPlayer(PlayerPiece.X, strat1);
    IPlayer<Hexagon> player2 = new HumanPlayer(PlayerPiece.O, strat2);

    ReversiController controller = new ReversiController(mutableModel, player1, view1);
    ReversiController controller1 = new ReversiController(mutableModel, player2, view2);
  }

}



