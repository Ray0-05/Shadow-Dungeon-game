/**
 * Represents a cooldown timer that controls when shooting can be performed again.
 */
public class CoolDownTimer {
    private int remaining;
    private final int cooldown;

    /**
     * Creates a new cooldown timer with the specified duration.
     *
     * @param cooldown The number of ticks required before the timer resets.
     */
    public CoolDownTimer(int cooldown) {
        this.remaining = cooldown;
        this.cooldown = cooldown;
    }

    /**
     * Checks if the cooldown period has ended and the action is ready.
     *
     * @return True if the timer has finished and the action can be performed, false otherwise.
     */
    public boolean readyToShoot() {
        return remaining <= 0;
    }

    /**
     * Resets the timer back to its full cooldown duration.
     */
    public void reset() {
        this.remaining = cooldown;
    }

    /**
     * Decreases the timer by one tick.
     * Should be called each frame or time step.
     */
    public void tick() {
        if (remaining > 0) remaining--;
    }
}
