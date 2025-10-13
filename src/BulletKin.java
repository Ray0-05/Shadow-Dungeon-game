import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class BulletKin extends Enemy{
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletKinCoin"));
    private static final int SHOT_FREQ = Integer.parseInt(ShadowDungeon.getGameProps().getProperty(
                                            "bulletKinShootFrequency"));
    private int shotRemainingCoolDown;
    private boolean canShoot;

    public BulletKin(Point position){
        super(position, EnemyCharacter.BULLET_KIN);
        this.shotRemainingCoolDown = 0;
        this.canShoot = true;
    }

    @Override
    public void update(Player player, ArrayList<Projectile> allProjectiles){
        if (hasCollidedWith(player)){
            super.damagePlayerOnContact(player);
        }

        for (Projectile p : allProjectiles){
            // only interacts with bullets
            if (p instanceof Bullet){
                if (hasCollidedWith(p)) {
                    super.setHealth(super.getHealth() - p.getDamage());
                    // destroy both enemy (if health < 0) and bullet

                    if (super.getHealth() <= 0){
                        super.setAlive(false);
                        player.earnCoins(COIN);
                    }

                    p.setDestroyed(true);
                }
            }
        }

        if (shotRemainingCoolDown > 0){
            shotRemainingCoolDown--;
        }else{
            canShoot = true;
        }
    }

    public Fireball shoot(Player player) {
        if (canShoot){
            shotRemainingCoolDown = SHOT_FREQ; // reset the shot timer
            canShoot = false;
            Vector2 target = new Vector2(player.getPosition().x, player.getPosition().y);
            Vector2 start = new Vector2(super.getPosition().x, super.getPosition().y);
            return new Fireball(start, target);
        }
        return null; // no fireball fired
    }


}
