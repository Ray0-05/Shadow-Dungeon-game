import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Chest that can be unlocked by the player to earn coins
 */
public class TreasureBox extends GameObject implements CollidableWithPlayer{
    private final double coinValue;
    private boolean active = true;

    public TreasureBox(Point position, double coinValue) {
        super(position, new Image("res/treasure_box.png"));
        this.coinValue = coinValue;
    }

    public void update(Input input, Player player) {
        if (hasContactWith(player) && input.wasPressed(Keys.K) && player.getKeyNum() > 0) {
            player.earnCoins(coinValue);
            player.useKey();
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }
}