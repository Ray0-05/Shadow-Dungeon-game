/**
 * Represents the different weapon types in the game.
 * Each weapon has a damage value, a level, and a cooldown between shots.
 */
public enum Weapon {
    STANDARD(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponStandardDamage")), 0),
    ADVANCE(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponAdvanceDamage")), 1),
    ELITE(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponEliteDamage")), 2);


    private final int damage;
    private final int level;
    private final static int MAX_Level = 2;
    private final static int SHOT_COOLDOWN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletFreq"));

    /**
     * Creates a weapon with the specified damage and level.
     *
     * @param damage The damage dealt by the weapon.
     * @param level  The level of the weapon.
     */
    Weapon(int damage, int level){
        this.damage = damage;
        this.level = level;
    }

    /**
     * Gets the damage value of the weapon.
     *
     * @return The weapon's damage.
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Gets the cooldown between shots for the weapon.
     *
     * @return The number of ticks between shots.
     */
    public int getShotCooldown(){
        return SHOT_COOLDOWN;
    }

    /**
     * Gets the level of the weapon.
     *
     * @return The weapon's level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Checks whether the weapon can be upgraded to a higher level.
     *
     * @return True if the weapon can be upgraded, false if it is at max level.
     */
    public boolean canUpgrade(){
        return this.level < MAX_Level;
    }
}
