import bagel.util.Rectangle;

/**
 * Represents an object that can be collided with by projectiles.
 */
public interface CollidableWithProjectiles {
    /**
     * Gets the bounding box of the object for collision detection.
     *
     * @return The bounding box of the object.
     */
    Rectangle getBoundingBox();

    /**
     * Checks whether the object has collided with a given projectile.
     *
     * @param projectile The projectile to check collision against.
     * @return True if this object intersects with the projectile, false otherwise.
     */
    default boolean hasCollidedWith(Projectile projectile) {
        return this.getBoundingBox()
                .intersects(projectile.getBoundingBox());
    }
}
