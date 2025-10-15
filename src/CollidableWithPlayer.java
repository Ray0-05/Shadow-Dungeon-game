import bagel.util.Rectangle;

public interface CollidableWithPlayer {
    // Any class that implements this must provide getBoundingBox()
    Rectangle getBoundingBox();

    // Default collision check
    default boolean hasContactWith(Player player) {
        return this.getBoundingBox()
                .intersects(player.getImage()
                        .getBoundingBoxAt(player.getPosition()));
    }

    default void onContactWithPlayer(Player player){
        // nothing to be done by default
    };
}
