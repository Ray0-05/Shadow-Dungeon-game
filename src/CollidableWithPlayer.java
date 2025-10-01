import bagel.util.Rectangle;

public interface CollidableWithPlayer {
    // Any class that implements this must provide getBoundingBox()
    Rectangle getBoundingBox();

    // Default collision check
    default boolean hasCollidedWith(Player player) {
        return this.getBoundingBox()
                .intersects(player.getCurrImage()
                        .getBoundingBoxAt(player.getPosition()));
    }
}
