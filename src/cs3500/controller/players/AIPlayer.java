package cs3500.controller.players;


import cs3500.model.Hexagon;
import cs3500.model.PlayerPiece;
import cs3500.strategy.IMoveStrategy;
import cs3500.view.ITile;


/**
 * The AI Player that will be playing against the player in solitaire mode, or another
 * AI Player if the player would like a pleasurable viewing experience of two AI Players
 * battling it out.The AI player will be making decisions to make moves against the other player.
 */
public class AIPlayer extends AbstractPlayer {

  public AIPlayer(PlayerPiece piece, IMoveStrategy<Hexagon> strategy) {
    super(piece, strategy);
  }


  @Override
  public void playPShape() {
    Hexagon hex = pertainPShape(model);
    if (hex == null) {
      view.pass();
    } else {
      ITile<Hexagon> hexagonTile = panel.pshapeToTile(hex);
      System.out.println("gets notified");
      System.out.println(hexagonTile);
      view.clickHex(hexagonTile);
      view.putDisc(hexagonTile);
    }
  }

}