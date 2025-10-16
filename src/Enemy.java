import bagel.util.Point;

import java.util.ArrayList;

public abstract class Enemy extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles,
        Damageable {
    private double health;
    private boolean active = false;

    private static final double DMG_ON_CONTACT = Double.parseDouble(
            ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame")
    );

    public Enemy(Point position, EnemyCharacter character){
        super(position, character.getImage());
        this.health = character.getInit_health();
    }

    public abstract void update(Player player, ArrayList<Projectile> allProjectiles);

    public void OnContactWithPlayer(Player player){
        player.takeDamage(DMG_ON_CONTACT);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double newHealth) {
        this.health = newHealth;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
