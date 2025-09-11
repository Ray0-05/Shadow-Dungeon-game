import bagel.util.Point;

public class River extends GameObject {
    private final double damagePerFrame;

    /*
     * River is overlappable (true), but damages the player each frame while overlapping.
     */
    public River(Point coordinate, double damagePerFrame) {
        super(coordinate, "res/river.png", true);
        this.damagePerFrame = damagePerFrame;
    }

    public double getDamagePerFrame() {
        return damagePerFrame;
    }
}
