import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Obstacle that blocks the player from moving through it
 */
public class Wall extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles{

    public Wall(Point position) {
        super(position, new Image("res/wall.png"));
    }

    public void update(Player player, ArrayList<Projectile> projectiles) {
        if (hasContactWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
        for (Projectile p: projectiles){
            if(hasCollidedWith(p)) {
                p.setDestroyed(true);
            }
        }
    }

}