import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class KeyBulletKin extends Enemy{
    private boolean active = false; // only true when the Battle Room has been activated
    
    public KeyBulletKin(Point startPos) {
        super(startPos, EnemyCharacter.KEY_BULLET_KIN);
    }

    @Override
    public void update(Player player, ArrayList<Projectile> allProjectiles) {
        if (hasContactWith(player)){
            super.OnContactWithPlayer(player);
        }

        for (Projectile p : allProjectiles){
            if ((p instanceof Bullet) && hasCollidedWith(p)){
                takeDamage(p.getDamage());
                p.setDestroyed(true);
            }
            if (isDead()){
                active = false;
            }
        }

    }


    public boolean isDead() {
        return getHealth() <= 0;
    }

    public Key dropKey(){
        return new Key(getPosition());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
