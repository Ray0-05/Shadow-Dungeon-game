import bagel.Image;
import bagel.util.Point;

/**
 * Represents a river that continuously damages the player
 * while they are in contact with it, unless the player is immune to water (marine in this case).
 */
public class River extends GameObject implements CollidableWithPlayer{
    private final double damagePerFrame;

    /**
     * Creates a river hazard at the specified position.
     *
     * @param position The position of the river on the map.
     */
    public River(Point position) {
        super(position, new Image("res/river.png"));
        damagePerFrame = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame"));
    }

    /**
     * Updates the river’s interaction with the player.
     * Applies continuous damage while the player stands on the river.
     *
     * @param player The player interacting with the river.
     */
    public void update(Player player) {
        if (hasContactWith(player) && !player.isImmuneToWater()) {
            player.takeDamage(damagePerFrame);
        }
    }
}