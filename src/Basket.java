import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Represents a Basket object in the game that acts as an obstacle.
 * It can block player movement and can be destroyed when hit by a bullet,
 * rewarding the player with coins. destroy only bullet upon collision.
 */
public class Basket extends Obstacle {
    private static final int COIN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("basketCoin"));

    /**
     * Creates a Basket object at the specified position.
     *
     * @param position The position of the basket on the map.
     */
    public Basket(Point position){
        super(position, new Image("res/basket.png"));
    }

    /**
     * Updates the basket's interaction with the player and projectiles.
     * Prevents the player from moving through it and checks for collisions
     * with bullets to trigger coin collection.
     *
     * @param player The player interacting with the basket.
     * @param allProjectiles The list of all active projectiles in the game.
     */
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

    /**
     * Gets the number of coins rewarded when the basket is collected.
     *
     * @return The number of coins.
     */
    public int getCoin() {
        return COIN;
    }

    /**
     * Handles what happens when the player collects the basket.
     * The player earns coins, and the basket is destroyed.
     *
     * @param player The player who collects the basket.
     */
    public void onCollect(Player player){
        player.earnCoins(getCoin());
        setDestroyed(true);
    }


}
