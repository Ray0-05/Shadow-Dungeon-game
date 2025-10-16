import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

public class Store {
    private final static Image IMAGE = new Image("res/store.png");
    private final static Point position = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("store"));
    private final static double EXTRA_HEALTH = 50;
    private final static int COST = 50;
    private boolean active = false;

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

    public void upgradeWeapon(Player player){
        if (player.getWeapon().getLevel() == 0){
            player.setWeapon(Weapon.ADVANCE);
        } else if (player.getWeapon().getLevel() == 1) {
            player.setWeapon(Weapon.ELITE);
        }
    }

    public void buyHealth(Player player){
        player.setHealth(player.getHealth() + EXTRA_HEALTH);
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
