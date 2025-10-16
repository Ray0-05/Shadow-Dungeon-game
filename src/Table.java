import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * A solid obstacle that blocks player movement and interacts with projectiles.
 * Tables block the player from passing through and are destroyed by player bullets,
 * while also stopping enemy fireballs without being destroyed.
 */
public class Table extends Obstacle {

    /**
     * Creates a new table obstacle at the specified position.
     * Loads the table image from the resources folder.
     *
     * @param position the coordinates where this table should be placed
     */
    public Table(Point position){
        super(position, new Image("res/table.png"));
    }

    /**
     * Updates the table's collision behavior with the player and projectiles.
     * Prevents the player from moving through by resetting their position to the
     * previous frame. Destroys any projectile that hits it, and marks the table
     * itself as destroyed if hit by a player bullet.
     *
     * @param player the player character to check collisions with
     * @param allProjectiles list of all active projectiles to check for impacts
     */
    public void update(Player player, ArrayList<Projectile> allProjectiles){
        if (hasContactWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
        for (Projectile p: allProjectiles){
            if(hasCollidedWith(p)) {
                p.setDestroyed(true);
                if (p instanceof Bullet){
                    setDestroyed(true);
                    break;
                }
            }
        }
    }
}
