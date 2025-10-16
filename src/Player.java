import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * Player character that can move around and between rooms, change characters in preproom, defeat enemies,
 * and collect coins.
 * Handles player movement via keyboard, shooting via mouse, collision with projectiles,
 * and manages player stats like health, coins, and keys.
 */
public class Player extends GameObject implements Damageable, CollidableWithProjectiles {
    private Point prevPosition;
    private Character character;
    private double health;
    private final double speed;
    private double coins = 0;
    private boolean faceLeft = false;
    private Weapon weapon;
    private int keyNum = 0;
    private final CoolDownTimer coolDownTimer;

    private static final int EXTRA_COIN_PER_KILL = Integer.parseInt(
            ShadowDungeon.getGameProps().getProperty("robotExtraCoin"));

    /**
     * Creates a new player at the specified starting position.
     * Initializes the player with default stats from game properties including health,
     * movement speed, and the standard weapon. Sets up the shooting cooldown timer.
     *
     * @param position the starting coordinates for the player in the game
     */
    public Player(Point position) {
        super(position, Character.ORIGINAL.getRightImage());
        this.character = Character.ORIGINAL;
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("initialHealth"));
        this.weapon = Weapon.STANDARD;
        this.coolDownTimer = new CoolDownTimer(weapon.getShotCooldown());
    }

    /**
     * Updates the player's position based on keyboard input and mouse cursor.
     * Handles WASD movement keys, ensures the player stays within window bounds,
     * updates the facing direction based on mouse position, and ticks down the
     * shooting cooldown timer.
     *
     * @param input captures keyboard and mouse state for this frame
     */
    public void update(Input input) {
        // check movement keys and mouse cursor
        double currX = getPosition().x;
        double currY = getPosition().y;

        if (input.isDown(Keys.A)) {
            currX -= speed;
        }
        if (input.isDown(Keys.D)) {
            currX += speed;
        }
        if (input.isDown(Keys.W)) {
            currY -= speed;
        }
        if (input.isDown(Keys.S)) {
            currY += speed;
        }

        faceLeft = input.getMouseX() < currX;

        // updateAndRender the player getPosition() accordingly and ensure it can't move past the game window
        Rectangle rect = getImage().getBoundingBoxAt(new Point(currX, currY));
        Point topLeft = rect.topLeft();
        Point bottomRight = rect.bottomRight();
        if (topLeft.x >= 0 && bottomRight.x <= Window.getWidth() && topLeft.y >= 0 && bottomRight.y <= Window.getHeight()) {
            move(currX, currY);
        }

        // Reduce the shooting cooldown time
        coolDownTimer.tick();
    }

    /**
     * Updates the player with collision detection for enemy projectiles.
     * Checks if the player has been hit by any fireballs, applies damage if hit,
     * destroys the colliding projectile, then performs the standard movement and timer updateAndRender.
     *
     * @param input captures keyboard and mouse state for this frame
     * @param allProjectiles list of all active projectiles in the current room to check collisions against
     */
    public void update(Input input, ArrayList<Projectile> allProjectiles){
        // Check and deal when in contact with enemies fireball
        for (Projectile p : allProjectiles){
            if (p instanceof Fireball){
                if (this.hasCollidedWith(p)){
                    p.setDestroyed(true);
                    takeDamage(p.getDamage());
                }
            }
        }

        this.update(input); // movement logic and timer done here

    }

    /**
     * Moves the player to the specified coordinates.
     * Stores the current position as the previous position before updating,
     * which is useful for collision resolution and movement tracking.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void move(double x, double y) {
        prevPosition = getPosition();
        setPosition(new Point(x, y));
    }

    /**
     * Renders the player on screen with the correct facing direction.
     * Selects the left or right facing sprite based on the player's current
     * facing direction and draws it at the player's position.
     */
    @Override
    public void draw() {
        setImage(faceLeft ? character.getLeftImage() : character.getRightImage());
        getImage().draw(getPosition().x, getPosition().y);
    }

    /**
     * Adds coins to the player's total.
     *
     * @param coins the amount of coins to add (can be fractional)
     */
    public void earnCoins(double coins) {
        this.coins += coins;
    }

    /**
     * Handles the player's death by transitioning to the game over screen.
     */
    public void onDeath(){
        ShadowDungeon.changeToGameOverRoom();
    }

    /**
     * Reduces the player's health by the specified damage amount.
     * If health drops to zero or below, triggers the game over transition.
     *
     * @param damage the amount of damage to subtract from current health
     */
    @Override
    public void takeDamage(double damage) {
        setHealth((getHealth() - damage));
        if (isDead()) {
            ShadowDungeon.changeToGameOverRoom();
        }
    }
    /**
     * Gets the player's position from the previous frame.
     * Useful for collision resolution when the player needs to be pushed back.
     *
     * @return the coordinates where the player was located in the last updateAndRender
     */
    public Point getPrevPosition() {
        return prevPosition;
    }

    /**
     * Changes the player's character skin (abilities are detected by callers: etc enemy, river).
     * Updates the sprite to match the new character and preserves the
     * current facing direction.
     *
     * @param newCharacter the character type to switch to
     */
    public void changeCharacter(Character newCharacter){
        character = newCharacter;
        setImage(faceLeft ? newCharacter.getLeftImage() : newCharacter.getRightImage());
    }

    /**
     * Attempts to fire a bullet toward the mouse cursor position.
     * Only fires if left mouse button was clicked, the player has a non-original character,
     * and the shooting cooldown has elapsed. Resets the cooldown timer when a shot is fired.
     *
     * @param input captures the mouse button state and cursor position
     * @return a new bullet traveling toward the cursor, or null if no shot was fired
     */
    public Bullet shoot(Input input) {
        if (input.wasPressed(MouseButtons.LEFT) && character != Character.ORIGINAL && coolDownTimer.readyToShoot()) {
            coolDownTimer.reset(); // reset the shot timer
            Vector2 target = new Vector2(input.getMouseX(), input.getMouseY());
            return new Bullet(new Vector2(getPosition().x, getPosition().y), target, weapon.getDamage());
        }
        return null; // no bullet fired
    }

    /**
     * Checks if the player is immune to water hazards.
     * Only the marine character has water immunity.
     *
     * @return true if the current character is marine, false otherwise
     */
    public boolean isImmuneToWater(){
        return character == Character.MARINE;
    }

    /**
     * Gets the extra coin bonus per enemy kill if the player qualifies.
     * Only the robot character receives bonus coins for kills.
     *
     * @return the extra coin amount if robot character, otherwise 0
     */
    public int getExtraCoinPerKillIfEligible(){
        if (character == Character.ROBOT){
            return EXTRA_COIN_PER_KILL;
        }
        return 0;
    }
    /**
     * Gets the player's current health value.
     *
     * @return the remaining health points
     */
    @Override
    public double getHealth() {
        return health;
    }

    /**
     * Sets the player's health to a specific value.
     *
     * @param newHealth the health value to set
     */
    @Override
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }

    /**
     * Adds one key to the player's inventory.
     * Keys are used to unlock treasurebox throughout the dungeon.
     */
    public void collectKey() {
        this.keyNum++;
    }

    /**
     * Removes one key from the player's inventory.
     * Called when the player open a treasurebox.
     */
    public void useKey(){
        this.keyNum--;
    }
    /**
     * Gets the number of keys the player currently has.
     *
     * @return the key count
     */
    public int getKeyNum() {
        return keyNum;
    }

    /**
     * Gets the player's currently equipped weapon.
     *
     * @return the weapon object with damage and cooldown properties
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Equips a new weapon for the player.
     *
     * @param weapon the weapon to equip
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the player's current coin balance.
     *
     * @return the total coins collected
     */
    public double getCoins() {
        return coins;
    }

    /**
     * Sets the player's coin balance to a specific amount.
     *
     * @param coins the new coin balance
     */
    public void setCoins(double coins) {
        this.coins = coins;
    }
}
