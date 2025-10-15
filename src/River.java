import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class River extends GameObject implements CollidableWithPlayer{
    private final double damagePerFrame;

    public River(Point position) {
        super(position, new Image("res/river.png"));
        damagePerFrame = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame"));
    }

    public void update(Player player) {
        if (hasContactWith(player)) {
            player.takeDamage(damagePerFrame);
        }
    }
}