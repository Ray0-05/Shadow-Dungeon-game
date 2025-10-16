import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public abstract class Obstacle extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles{
    private boolean destroyed = false;

    public Obstacle(Point position, Image image){
        super(position, image);
    }
    public abstract void update(Player player, ArrayList<Projectile> allProjectiles );

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
