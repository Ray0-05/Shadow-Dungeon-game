public enum Weapon {
    STANDARD(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponStandardDamage")), 0),
    ADVANCE(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponAdvanceDamage")), 1),
    ELITE(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponEliteDamage")), 2);

    private final int damage;
    private final int level;
    private final static int SHOT_COOLDOWN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletFreq"));

    Weapon(int damage, int level){
        this.damage = damage;
        this.level = level;
    }

    public int getDamage(){
        return damage;
    }

    public int getShotCooldown(){
        return SHOT_COOLDOWN;
    }

    public int getLevel() {
        return level;
    }
}
