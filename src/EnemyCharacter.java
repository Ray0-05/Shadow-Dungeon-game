import bagel.Image;

/**
 * Represents the different types of enemy characters in the game.
 * Each enemy has a sprite image and an initial health value.
 */
public enum EnemyCharacter {
    BULLET_KIN(new Image("res/bullet_kin.png"), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinHealth"))),
    ASHEN_BULLET_KIN(new Image("res/ashen_bullet_kin.png"), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinHealth"))),
    KEY_BULLET_KIN(new Image("res/key_bullet_kin.png"), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinHealth")));

    private final Image image;
    private final double init_health;

    /**
     * Creates an enemy type with a sprite image and initial health.
     *
     * @param image  The sprite image for the enemy.
     * @param health The initial health of the enemy.
     */
    EnemyCharacter(Image image, double health){
        this.image = image;
        this.init_health = health;
    }

    /**
     * Gets the sprite image of the enemy.
     *
     * @return The enemy's image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the initial health of the enemy.
     *
     * @return The initial health value.
     */
    public double getInit_health() {
        return init_health;
    }
}
