import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Represents a wall obstacle that blocks the player from moving through it.
 * Projectiles that collide with it are destroyed.
 */
public class Wall extends Obstacle{

    /**
     * Creates a new wall at the specified position.
     *
     * @param position The position of the wall on the map.
     */
    public Wall(Point position) {
        super(position, new Image("res/wall.png"));
    }

    /**
     * Updates the wall's interaction with the player and projectiles.
     * Prevents the player from moving through it and destroys any colliding projectiles.
     *
     * @param player      The player interacting with the wall.
     * @param projectiles The list of all active projectiles in the game.
     */
    public void update(Player player, ArrayList<Projectile> projectiles) {
        if (hasContactWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
        for (Projectile p: projectiles){
            if(hasCollidedWith(p)) {
                p.setDestroyed(true);
                // not destroyed here
            }
        }
    }

}