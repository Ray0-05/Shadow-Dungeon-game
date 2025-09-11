import bagel.util.Point;

public abstract class Enemy extends GameObject {
    private boolean defeated = false;

    protected Enemy(Point coordinate, String spritePath) {
        // Enemies are overlappable (player can walk through)
        super(coordinate, spritePath, true);
    }

    public final boolean isDefeated() { return defeated; }

    /** Called when the player touches the enemy. */
    public void kill() { defeated = true; }
}
