import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * A basic enemy type that shoots fireballs at the player on a timed cooldown.
 * Similar to Ashen Bullet Kin but with different stats and coin rewards.
 * Takes damage from player bullets, damages the player on contact, and player earn
 * coins when defeated.
 */
public class BulletKin extends Enemy{
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletKinCoin"));
    private final CoolDownTimer coolDownTimer;

    /**
     * Creates a new Bullet Kin enemy at the specified position.
     * Initializes the enemy with stats from game properties and sets up
     * the shooting cooldown timer based on configured shoot frequency.
     *
     * @param position the starting coordinates for this enemy
     */
    public BulletKin(Point position){
        super(position, EnemyCharacter.BULLET_KIN);
        coolDownTimer = new CoolDownTimer(Integer.parseInt(ShadowDungeon.getGameProps().getProperty(
                "bulletKinShootFrequency")));
    }

    /**
     * Updates the enemy's behavior for this frame.
     * Checks for contact with the player to apply damage, detects collisions with
     * player bullets to take damage and potentially die, and ticks down the shooting
     * cooldown timer.
     *
     * @param player the player character to check collisions and interactions with
     * @param allProjectiles list of all active projectiles to check for bullet collisions
     */
    @Override
    public void update(Player player, ArrayList<Projectile> allProjectiles){
        if (hasContactWith(player)){
            super.OnContactWithPlayer(player);
        }

        for (Projectile p : allProjectiles){
            // only interacts with bullets
            if (p instanceof Bullet){
                if (hasCollidedWith(p)) {
                    p.setDestroyed(true);
                    this.takeDamage(p.getDamage());
                    // destroy both enemy (if health < 0) and bullet

                    if (this.isDead()){
                        this.onDeath(player);
                        break;
                    }


                }
            }
        }

        coolDownTimer.tick();
    }

    /**
     * Handles the enemy's death and rewards the player with coins.
     * Awards the base coin amount plus any character-specific bonus coins
     * the player is eligible for.
     *
     * @param player the player to award coins to
     */
    public void onDeath(Player player){
         player.earnCoins(COIN + player.getExtraCoinPerKillIfEligible());
    }

    /**
     * Attempts to shoot a fireball at the player's current position.
     * Only fires if the shooting cooldown has elapsed, then resets the cooldown.
     * The fireball travels from this enemy toward the player's location.
     *
     * @param player the player to target with the fireball
     * @return a new fireball projectile aimed at the player, or null if cooldown hasn't elapsed
     */

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
