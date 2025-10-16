import bagel.Image;
import bagel.util.Point;

/**
 * Represents a collectible key object in the game.
 * When the player touches the key, it is collected and reflected in the player's stat
 * and marked as obtained.
 */
public class Key extends GameObject implements CollidableWithPlayer{
    private boolean isCollected = false;

    /**
     * Creates a new key at the specified position.
     *
     * @param position The position where the key is placed on the map.
     */
    public Key(Point position){
        super(position, new Image("res/key.png"));
    }

    /**
     * Updates the key’s state by checking for contact with the player.
     * If collected, the key is marked as obtained by the player.
     * And is reflected on the player stat.
     *
     * @param player The player interacting with the key.
     */
    public void update(Player player){
        if (this.hasContactWith(player)){
            player.collectKey();
            isCollected = true;
        }
    }

    /**
     * Checks whether the key has been collected by the player.
     *
     * @return True if the key has been collected, false otherwise.
     */
    public boolean isCollected() {
        return isCollected;
    }
}
