import bagel.util.Rectangle;

/**
 * Represents an object that can be collided with by the player.
 */
public interface CollidableWithPlayer {
    /**
     * Gets the bounding box of the object for collision detection.
     *
     * @return The bounding box of the object.
     */
    Rectangle getBoundingBox();

    /**
     * Checks whether the object has contact with the player.
     *
     * @param player The player to check collision against.
     * @return True if this object intersects with the player, false otherwise.
     */
    default boolean hasContactWith(Player player) {
        return this.getBoundingBox()
                .intersects(player.getImage()
                        .getBoundingBoxAt(player.getPosition()));
    }

    /**
     * Triggered when the player makes contact with the object.
     * Default implementation does nothing; override in implementing classes if needed.
     *
     * @param player The player making contact with the object.
     */
    default void onContactWithPlayer(Player player){
        // nothing to be done by default
    };
}
