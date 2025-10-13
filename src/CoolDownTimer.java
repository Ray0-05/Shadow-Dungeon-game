public class CoolDownTimer {
    private int remaining;
    private final int cooldown;

    public CoolDownTimer(int cooldown) {
        this.remaining = cooldown;
        this.cooldown = cooldown;
    }

    public boolean readyToShoot() {
        return remaining <= 0;
    }

    public void reset() {
        this.remaining = cooldown;
    }

    public void tick() {
        if (remaining > 0) remaining--;
    }
}
