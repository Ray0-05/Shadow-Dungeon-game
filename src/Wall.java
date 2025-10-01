import bagel.Image;
import bagel.util.Point;

/**
 * Obstacle that blocks the player from moving through it
 */
public class Wall extends GameObject implements CollidableWithPlayer{

    public Wall(Point position) {
        super(position, new Image("res/wall.png"));
    }

    public void update(Player player) {
        if (hasCollidedWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
    }

}