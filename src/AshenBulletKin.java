import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class AshenBulletKin extends Enemy implements CollidableWithPlayer, CollidableWithProjectiles{
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("ashenBulletKinCoin"));
    private final CoolDownTimer coolDownTimer;

    public AshenBulletKin(Point position){
        super(position, EnemyCharacter.ASHEN_BULLET_KIN);
        coolDownTimer = new CoolDownTimer(Integer.parseInt(ShadowDungeon.getGameProps().getProperty(
                "ashenBulletKinShootFrequency")));
    }

    @Override
    public void update(Player player, ArrayList<Projectile> allProjectiles){
        if (hasContactWith(player)){
            super.OnContactWithPlayer(player);
        }

        for (Projectile p : allProjectiles){
            // only interacts with bullets
            if (p instanceof Bullet){
                if (hasCollidedWith(p)) {
                    takeDamage(p.getDamage());
                    // destroy both enemy (if health < 0) and bullet

                    if (isDead()){
                        onDeath(player);
                    }

                    p.setDestroyed(true);
                }
            }
        }

        // Reduce the cooldown time
        coolDownTimer.tick();
    }

    public void onDeath(Player player){
        player.earnCoins(COIN + player.getExtraCoinPerKillIfEligible());
    }

    public Fireball shoot(Player player) {
        if (coolDownTimer.readyToShoot()){
            coolDownTimer.reset(); // reset the shot timer
            Vector2 target = new Vector2(player.getPosition().x, player.getPosition().y);
            Vector2 start = new Vector2(super.getPosition().x, super.getPosition().y);
            return new Fireball(start, target);
        }
        return null; // no fireball fired
    }
}
