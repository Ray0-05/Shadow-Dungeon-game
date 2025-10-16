import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Base class for all obstacles in the game that can block movement of players.
 * Obstacles can collide with the player and projectiles, and can be potentially destroyed during gameplay.
 * Examples include walls(undestroyable), table, etc.
 */
public abstract class Obstacle extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles{
    private boolean destroyed = false;

    /**
     * Creates a new obstacle at the specified position with the given image.
     * The obstacle starts in a non-destroyed state.
     *
     * @param position the coordinates where this obstacle is located
     * @param image the visual representation of this obstacle
     */
    public Obstacle(Point position, Image image){
        super(position, image);
    }

    /**
     * Updates the obstacle's behavior for this frame.
     * Handles collision detection with the player and projectiles, and any
     * obstacle-specific logic. Implementation varies by obstacle type.
     *
     * @param player the player character to check collisions with
     * @param allProjectiles list of all active projectiles to check for interactions
     */
    public abstract void update(Player player, ArrayList<Projectile> allProjectiles );

    /**
     * Marks the obstacle as destroyed or restores it.
     * Destroyed obstacles typically stop being rendered and no longer collide with entities.
     *
     * @param destroyed true to mark as destroyed, false to restore
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * Checks if the obstacle has been destroyed.
     *
     * @return true if the obstacle is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
