import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Represents a store in the game where the player can upgrade weapons,
 * buy extra health, or reset the game state.
 */
public class Store {
    private final static Image IMAGE = new Image("res/store.png");
    private final static Point position = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("store"));
    private final static double EXTRA_HEALTH = 50;
    private final static int COST = 50;
    private boolean active = false;

    /**
     * Updates the store's interaction with the player.
     * Handles buying health, upgrading weapons, or resetting the game.
     *
     * @param input  The current keyboard input.
     * @param player The player interacting with the store.
     */
    public void update(Input input, Player player){

        if (input.wasPressed(Keys.L) && player.getCoins() >= COST && player.getWeapon().canUpgrade()){
            player.setCoins(player.getCoins() - COST);
            upgradeWeapon(player);
        }else if ( input.wasPressed(Keys.E) && player.getCoins() >= COST){
            player.setCoins(player.getCoins() - COST);
            buyHealth(player);
        } else if (input.wasPressed(Keys.P)) {
            ShadowDungeon.resetGameState(ShadowDungeon.getGameProps());
        }
        IMAGE.draw(position.x, position.y);
    }

    /**
     * Upgrades the player's weapon based on its current level.
     *
     * @param player The player whose weapon will be upgraded.
     */
    public void upgradeWeapon(Player player){
        if (player.getWeapon().getLevel() == 0){
            player.setWeapon(Weapon.ADVANCE);
        } else if (player.getWeapon().getLevel() == 1) {
            player.setWeapon(Weapon.ELITE);
        }
    }

    /**
     * Buys extra health for the player.
     *
     * @param player The player whose health will be increased.
     */
    public void buyHealth(Player player){
        player.setHealth(player.getHealth() + EXTRA_HEALTH);
    }

    /**
     * Checks whether the store is currently active.
     *
     * @return True if the store is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the store's active state.
     *
     * @param active True to make the store active, false to deactivate it.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
