import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Represents an area where the player can restart the game.
 * When the player stands inside this area and presses ENTER,
 * the game state is reset.
 */
public class RestartArea extends GameObject implements CollidableWithPlayer{

    /**
     * Creates a new restart area at the specified position.
     *
     * @param position The position of the restart area on the map.
     */
    public RestartArea(Point position) {
        super(position, new Image("res/restart_area.png"));
    }

    /**
     * Updates the restart area's interaction with the player.
     * Resets the game when the player is inside the area and presses ENTER.
     *
     * @param input  The current keyboard input.
     * @param player The player interacting with the restart area.
     */
    public void update(Input input, Player player) {
        if (hasContactWith(player) && input.wasPressed(Keys.ENTER)) {
            ShadowDungeon.resetGameState(ShadowDungeon.getGameProps());
        }
    }
}
