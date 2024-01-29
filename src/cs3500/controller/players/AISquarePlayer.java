package cs3500.controller.players;


import cs3500.model.Square;
import cs3500.model.PlayerPiece;
import cs3500.strategy.IMoveStrategy;
import cs3500.view.ITile;


/**
 * The AI Player that will be playing against the player in solitaire mode, or another
 * AI Player if the player would like a pleasurable viewing experience of two AI Players
 * battling it out.The AI player will be making decisions to make moves against the other player.
 */
public class AISquarePlayer extends AbstractSquarePlayer {

  public AISquarePlayer(PlayerPiece piece, IMoveStrategy<Square> strategy) {
    super(piece, strategy);
  }


  @Override
  public void playPShape() {
    Square hex = pertainPShape(model);
    if (hex == null) {
      view.pass();
    } else {
      ITile<Square> squareITile = panel.pshapeToTile(hex);
      System.out.println("gets notified");
      System.out.println(squareITile);
      view.clickHex(squareITile);
      view.putDisc(squareITile);
    }
  }

}