import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class Table extends Obstacle {

    public Table(Point position){
        super(position, new Image("res/table.png"));
    }

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
                }
            }
        }
    }
}
