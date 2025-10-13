public enum Weapon {
    STANDARD(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponStandardDamage"))),
    ADVANCE(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponAdvanceDamage"))),
    ELITE(Integer.parseInt(ShadowDungeon.getGameProps().getProperty("weaponEliteDamage")));

    private final int damage;
    private final static int SHOT_COOLDOWN = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletFreq"));

    Weapon(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return damage;
    }

    public int getShotCooldown(){
        return SHOT_COOLDOWN;
    }

}
