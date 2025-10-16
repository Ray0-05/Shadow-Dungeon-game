import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Represents a treasure chest that can be unlocked by the player
 * to earn coins if they have a key.
 */
public class TreasureBox extends GameObject implements CollidableWithPlayer{
    private final double coinValue;
    private boolean active = true;

    /**
     * Creates a new treasure box at the specified position with a set coin value.
     *
     * @param position  The position of the treasure box on the map.
     * @param coinValue The amount of coins the player earns when unlocking the box.
     */
    public TreasureBox(Point position, double coinValue) {
        super(position, new Image("res/treasure_box.png"));
        this.coinValue = coinValue;
    }

    /**
     * Updates the treasure box’s interaction with the player.
     * If the player is in contact, presses K, and has a key, they earn coins.
     *
     * @param input  The current keyboard input.
     * @param player The player interacting with the treasure box.
     */
    public void update(Input input, Player player) {
        if (hasContactWith(player) && input.wasPressed(Keys.K) && player.getKeyNum() > 0) {
            player.earnCoins(coinValue);
            player.useKey();
            active = false;
        }
    }

    /**
     * Checks whether the treasure box is still active (unopened).
     *
     * @return True if the treasure box can still be opened, false otherwise.
     */
    public boolean isActive() {
        return active;
    }
}