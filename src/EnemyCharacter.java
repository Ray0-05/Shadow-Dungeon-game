import bagel.Image;

public enum EnemyCharacter {
    BULLET_KIN(new Image("res/bullet_kin.png"), Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletKinHealth"))),
    ASHEN_BULLET_KIN(new Image("res/ashen_bullet_kin.png"), Integer.parseInt(ShadowDungeon.getGameProps().getProperty("ashenBulletKinHealth"))),
    KEY_BULLET_KIN(new Image("res/key_bullet_kin.png"), Integer.parseInt(ShadowDungeon.getGameProps().getProperty("keyBulletKinHealth")));

    private final Image image;
    private final int init_health;

    EnemyCharacter(Image image, int health){
        this.image = image;
        this.init_health = health;
    }

    public Image getImage() {
        return image;
    }

    public int getInit_health() {
        return init_health;
    }
}
