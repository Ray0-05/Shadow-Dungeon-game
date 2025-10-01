import bagel.Image;
import bagel.util.Point;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class KeyBulletKin extends GameObject implements CollidableWithPlayer{
    private boolean active = false; // only true when the Battle Room has been activated
    private boolean dead = false;
    
    public KeyBulletKin(Point startPos) {
        super(startPos, new Image("res/key_bullet_kin.png"));
    }

    public void update(Player player) {
        if (hasCollidedWith(player)) {
            dead = true;
            active = false;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
