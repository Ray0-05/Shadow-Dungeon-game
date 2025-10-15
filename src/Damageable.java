public interface Damageable {
    double getHealth();
    void setHealth(double newHealth);

    public default boolean isDead(){
        return getHealth() <= 0;
    }
    public default void takeDamage(double damage){
        setHealth(getHealth() - damage);
    }



}
