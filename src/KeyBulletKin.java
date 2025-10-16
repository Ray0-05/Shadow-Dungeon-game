import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * A special enemy that patrols along a predefined path and drops a key when defeated.
 * Unlike other enemies, this one moves continuously between waypoints in a loop.
 * Takes damage from player bullets and damages the player on contact.
 */
public class KeyBulletKin extends Enemy{
    private boolean active = false; // only true when the Battle Room has been activated
    private ArrayList<Point> path;
    private int currentIndex;
    private Vector2 currVelocity;
    private Vector2 positionV;
    private static final int SPEED = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("keyBulletKinSpeed"));

    /**
     * Creates a new Key Bullet Kin enemy with a patrol path.
     * Parses the coordinate string where waypoints are separated by semicolons.
     * The enemy starts at the first waypoint and will patrol through all points in order.
     *
     * @param coords semicolon-separated string of coordinates defining the patrol path (e.g., "100,200;300,400;500,600")
     */
    public KeyBulletKin(String coords) {
        super(IOUtils.parseCoords(coords.split(";")[0]), EnemyCharacter.KEY_BULLET_KIN);
        path = new ArrayList<>();
        currentIndex = 0;
        positionV = new Vector2(getPosition().x, getPosition().y);

        for (String coord: coords.split(";")){
            this.path.add(IOUtils.parseCoords(coord));
        }
        currVelocity = getVelocity();
    }

    /**
     * Updates the enemy's patrol movement and collision detection.
     * Moves the enemy along its patrol path toward the next waypoint, checks for
     * contact with the player to apply damage, detects collisions with player bullets,
     * and deactivates the enemy when killed.
     *
     * @param player the player character to check collisions and interactions with
     * @param allProjectiles list of all active projectiles to check for bullet collisions
     */
    @Override
    public void update(Player player, ArrayList<Projectile> allProjectiles) {
        if (isAtEndPoint()){
            currVelocity = getVelocity();
        }

        goToNextLoc(currVelocity);

        if (hasContactWith(player)){
            super.OnContactWithPlayer(player);
        }

        for (Projectile p : allProjectiles){
            if ((p instanceof Bullet) && hasCollidedWith(p)){
                takeDamage(p.getDamage());
                p.setDestroyed(true);
            }
            if (isDead()){
                active = false;
                break;
            }
        }



    }

    /**
     * Moves the enemy toward the next waypoint by the given velocity.
     * Updates the internal position vector and the game object's position.
     *
     * @param velocity the movement vector for this frame
     */
    private void goToNextLoc(Vector2 velocity){
        positionV = positionV.add(velocity);
        setPosition(positionV.asPoint());
    }

    /**
     * Calculates the velocity vector needed to reach the next waypoint.
     * Determines the direction from the current waypoint to the next one in the path,
     * normalizes it, and scales it by the enemy's movement speed. Advances the
     * current waypoint index in a circular manner.
     *
     * @return velocity vector pointing toward the next waypoint at the configured speed
     */
    private Vector2 getVelocity() {
        Point startPosition = path.get(currentIndex);
        Vector2 currentVector = new Vector2(startPosition.x, startPosition.y);

        int nextIndex  = (currentIndex + 1) % path.size();

        Point target = path.get(nextIndex);
        Vector2 targetVector = new Vector2(target.x, target.y);

        Vector2 direction = targetVector.sub(currentVector).normalised();

        currentIndex = nextIndex;
        return direction.mul(SPEED);
    }

    /**
     * Checks if the enemy has reached the current target waypoint.
     * Uses distance threshold based on movement speed to determine arrival.
     *
     * @return true if the enemy is within one speed unit of the target waypoint
     */
    private boolean isAtEndPoint(){
        Point target = path.get(currentIndex);
        return getPosition().distanceTo(target) < SPEED;
    }

    /**
     * Checks if the enemy's health has been depleted.
     *
     * @return true if health is zero or below, false otherwise
     */
    public boolean isDead() {
        return getHealth() <= 0;
    }

    /**
     * Creates a key item at the enemy's current position.
     * Called when the enemy is defeated to drop a key for the player to collect.
     *
     * @return a new key object at this enemy's location
     */
    public Key dropKey(){
        return new Key(getPosition());
    }

}
