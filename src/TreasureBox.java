import bagel.util.Point;

public class TreasureBox extends GameObject {
    private final int value;       // coins granted on pickup
    private boolean collected = false;

    public TreasureBox(Point coordinate, int value) {
        super(coordinate,"res/treasure_box.png", true); // overlappable
        this.value = Math.max(0, value);
    }

    public boolean isCollected() { return collected; }
    public int getValue() { return value; }

    /* Called when the player touches this chest. */
    public void collect(Player player) {
        if (collected) return;
        player.addCoins(value);
        collected = true;
    }

    @Override
    public void render() {
        if (!collected) {
            super.render();
        }
    }
}
