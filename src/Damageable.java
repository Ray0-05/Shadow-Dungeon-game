/**
 * Represents an entity that can take damage and has health.
 */
public interface Damageable {
    /**
     * Gets the current health of the entity.
     *
     * @return The current health value.
     */
    double getHealth();

    /**
     * Sets the health of the entity.
     *
     * @param newHealth The new health value to set.
     */
    void setHealth(double newHealth);

    /**
     * Checks if the entity is dead (health is 0 or below).
     *
     * @return True if the entity's health is 0 or less, false otherwise.
     */
    public default boolean isDead(){
        return getHealth() <= 0;
    }

    /**
     * Applies damage to the entity by reducing its health.
     *
     * @param damage The amount of damage to apply.
     */
    public default void takeDamage(double damage){
        setHealth(getHealth() - damage);
    }



}
