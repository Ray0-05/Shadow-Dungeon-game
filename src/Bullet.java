import bagel.Image;
import bagel.util.Vector2;

/**
 * Represents a bullet projectile that moves toward a target.
 * The bullet's speed is defined in the game properties, and its damage is set when created.
 */
public class Bullet extends Projectile {

    /**
     * Creates a new bullet that travels from a starting position toward a target.
     *
     * @param startPosition The starting position of the bullet.
     * @param target        The target position the bullet moves toward.
     * @param damage        The amount of damage this bullet deals on impact.
     */
    public Bullet(Vector2 startPosition, Vector2 target, int damage){
        super(startPosition, target, new Image("res/bullet.png"),
              Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletSpeed")),
              damage
        );

    }

}
