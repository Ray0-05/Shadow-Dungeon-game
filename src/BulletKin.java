import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class BulletKin extends Enemy{
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletKinCoin"));
    private final CoolDownTimer coolDownTimer;

    public BulletKin(Point position){
        super(position, EnemyCharacter.BULLET_KIN);
        coolDownTimer = new CoolDownTimer(Integer.parseInt(ShadowDungeon.getGameProps().getProperty(
                "bulletKinShootFrequency")));
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

        coolDownTimer.tick();
    }

    public Fireball shoot(Player player) {
        if (coolDownTimer.readyToShoot()){
            coolDownTimer.reset();
            Vector2 target = new Vector2(player.getPosition().x, player.getPosition().y);
            Vector2 start = new Vector2(super.getPosition().x, super.getPosition().y);
            return new Fireball(start, target);
        }
        return null; // no fireball fired
    }


}
