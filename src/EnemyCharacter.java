import bagel.Image;

public enum EnemyCharacter {
    BULLET_KIN(new Image("res/bullet_kin.png"), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinHealth"))),
    ASHEN_BULLET_KIN(new Image("res/ashen_bullet_kin.png"), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinHealth"))),
    KEY_BULLET_KIN(new Image("res/key_bullet_kin.png"), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinHealth")));

    private final Image image;
    private final double init_health;

    EnemyCharacter(Image image, double health){
        this.image = image;
        this.init_health = health;
    }

    public Image getImage() {
        return image;
    }

    public double getInit_health() {
        return init_health;
    }
}
