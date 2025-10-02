import bagel.Image;
import bagel.util.Point;

import java.lang.Character;
import java.util.ArrayList;

public class BulletKin extends Enemy{
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletKinCoin"));
    private static final EnemyCharacter CHARACTER = EnemyCharacter.BULLET_KIN;

    public BulletKin(Point position){
        super(position, EnemyCharacter.BULLET_KIN.getImage(), EnemyCharacter.BULLET_KIN.getInit_health());
    }

    @Override
    public void update(Player player, ArrayList<Projectiles> projectiles){
        if (hasCollidedWith(player)){
            super.damagePlayerOnContact(player);
        }

        // fake logic to kill on touch, no health reduction logic yetd
        for (Projectiles p : projectiles) {
            if (hasCollidedWith(p)) {
                super.setAlive(false);
                p.setDestroyed(true); // destroy both enemy and bullet
                if (!super.isAlive()){
                    player.earnCoins(COIN);
                }
            }
        }
    }

}
