import bagel.Image;
import bagel.util.Vector2;

/**
 * Represents a base class for moving projectile in the game.
 * A projectile travels in a specific direction with a fixed speed
 * and can deal damage to some other GameObject upon contact.
 */
public abstract class Projectile extends GameObject implements CollidableWithPlayer{
    private final double speed;
    private int damage;
    private boolean destroyed = false;
    private Vector2 velocity;
    private Vector2 position;

    /**
     * Creates a new projectile starting from a position and moving toward a target.
     *
     * @param start  The starting position of the projectile.
     * @param target The target position that determines the projectile's direction.
     * @param image  The image representing the projectile.
     * @param speed  The movement speed of the projectile.
     * @param damage The amount of damage the projectile deals on impact.
     */
    public Projectile(Vector2 start, Vector2 target, Image image, double speed, int damage){
        super(start.asPoint(), image);
        this.speed = speed;
        this.damage = damage;

        // Direction = (target - start)
        Vector2 direction = target.sub(start).normalised();

        // Velocity = direction * speed
        this.velocity = direction.mul(speed);
        this.position = start;
    }

    /**
     * Updates the projectile's position each frame based on its velocity.
     */
    public void update() {
        // might wanna add deltaTime (helps keep motion smooth across framerates)
        position = (position.add(velocity));
        // Make sure it later draws the image at the current position(Game Object's position updated)
        super.setPosition(position.asPoint());
    }

    /**
     * Gets the amount of damage this projectile deals.
     *
     * @return The projectile's damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Checks if the projectile has been destroyed.
     *
     * @return True if destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Sets whether the projectile is destroyed.
     *
     * @param destroyed True to mark the projectile as destroyed, false otherwise.
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

}
