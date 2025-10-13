import bagel.util.Point;

import java.util.ArrayList;

public abstract class Enemy extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles{
    private int health;
    private static final double DMG_ON_CONTACT = Double.parseDouble(
            ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame")
    );
    private boolean isAlive = true;

    public Enemy(Point position, EnemyCharacter character){
        super(position, character.getImage());
        this.health = character.getInit_health();
    }

    public abstract void update(Player player, ArrayList<Projectile> allProjectiles);

    public void damagePlayerOnContact(Player player){
        player.receiveDamage(DMG_ON_CONTACT);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean aive) {
        isAlive = aive;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
