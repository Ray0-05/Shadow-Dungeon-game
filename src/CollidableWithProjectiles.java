import bagel.util.Rectangle;

public interface CollidableWithProjectiles {
    // Any class that implements this must provide getBoundingBox()
    Rectangle getBoundingBox();

    // Default collision check
    default boolean hasCollidedWith(Projectiles projectile) {
        return this.getBoundingBox()
                .intersects(projectile.getBoundingBox());
    }
}
