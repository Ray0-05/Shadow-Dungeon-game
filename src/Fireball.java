import bagel.Image;
import bagel.util.Vector2;

/**
 * Represents a fireball projectile that moves toward a target.
 * The fireball has a specific speed and damage value defined in the game properties.
 */
public class Fireball extends Projectile {

    /**
     * Creates a new fireball that travels from a starting position toward a target.
     *
     * @param start  The starting position of the fireball.
     * @param target The target position the fireball moves toward.
     */
    public Fireball(Vector2 start, Vector2 target){
        super(start, target, new Image("res/fireball.png"),
              Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballSpeed")),
                Integer.parseInt(ShadowDungeon.getGameProps().getProperty("fireballDamage")));
    }
}
