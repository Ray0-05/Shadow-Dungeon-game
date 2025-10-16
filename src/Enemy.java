import bagel.util.Point;

import java.util.ArrayList;

/**
 * Base class for all enemy types in the game.
 * Enemies can collide with the player and bullet, take damage, and have health.
 * Each enemy has an active state that determines whether it participates in gameplay.
 */
public abstract class Enemy extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles,
        Damageable {
    private double health;
    private boolean active = false;


    private static final double DMG_ON_CONTACT = Double.parseDouble(
            ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame")
    );
    /**
     * Creates a new enemy at the specified position with the given character type.
     * Initializes the enemy's health based on the character's default health value
     * and sets its visual appearance.
     *
     * @param position the starting coordinates for this enemy
     * @param character the enemy character type that defines appearance and initial stats
     */

    public Enemy(Point position, EnemyCharacter character){
        super(position, character.getImage());
        this.health = character.getInit_health();
    }

    /**
     * Updates the enemy's behavior for this frame.
     * Handles enemy AI logic, movement, shooting, and collision detection with projectiles.
     * Implementation varies by enemy type.
     *
     * @param player the player character to interact with
     * @param allProjectiles list of all active projectiles in the room for collision checking
     */
    public abstract void update(Player player, ArrayList<Projectile> allProjectiles);

    /**
     * Handles what happens when this enemy makes contact with the player.
     * Applies damage to the player based on the configured contact damage per frame.
     *
     * @param player the player to damage upon contact
     */
    public void OnContactWithPlayer(Player player){
        player.takeDamage(DMG_ON_CONTACT);
    }

    /**
     * Gets the enemy's current health value.
     *
     * @return the remaining health points
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the enemy's health to a specific value.
     *
     * @param newHealth the health value to set
     */
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }

    /**
     * Checks if the enemy is currently active in gameplay.
     * Inactive enemies don't updateAndRender or interact with the player.
     *
     * @return true if the enemy is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether the enemy is active in gameplay.
     * Used to enable or disable enemies based on game events like room activation.
     *
     * @param active true to activate the enemy, false to deactivate
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
