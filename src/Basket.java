import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class Basket extends Obstacle {
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("basketCoin"));

    public Basket(Point position){
        super(position, new Image("res/basket.png"));
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
                    onCollect(player);
                }
            }
        }
    }

    public int getCoin() {
        return COIN;
    }

    public void onCollect(Player player){
        player.earnCoins(getCoin());
        setDestroyed(true);
    }


}
