import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Area in Prep or End Room where the player can trigger a game reset
 */
public class RestartArea extends GameObject implements CollidableWithPlayer{

    public RestartArea(Point position) {
        super(position, new Image("res/restart_area.png"));
    }

    public void update(Input input, Player player) {
        if (hasCollidedWith(player) && input.wasPressed(Keys.ENTER)) {
            ShadowDungeon.resetGameState(ShadowDungeon.getGameProps());
        }
    }
}
