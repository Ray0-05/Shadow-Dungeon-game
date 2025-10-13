import bagel.util.Point;

import java.util.ArrayList;

public class AshenBulletKin extends Enemy implements CollidableWithPlayer, CollidableWithProjectiles{
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("ashenBulletKinCoin"));

    public AshenBulletKin(Point position){
        super(position, EnemyCharacter.ASHEN_BULLET_KIN);
    }

    @Override
    public void update(Player player, ArrayList<Projectiles> projectiles){
        if (hasCollidedWith(player)){
            super.damagePlayerOnContact(player);
        }

        for (Projectiles p : projectiles) {
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
}
