import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * A door that connects rooms in the game.
 * A door can be locked or unlocked, and when unlocked,
 * it allows the player to transition to the connected room.
 */
public class Door extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles{
    public final String toRoomName;
    public BattleRoom battleRoom; // only set if this door is inside a Battle Room
    private boolean unlocked = false;
    private boolean justEntered = false; // when the player only just entered this door's room
    private boolean shouldLockAgain = false;

    private static final Image LOCKED = new Image("res/locked_door.png");
    private static final Image UNLOCKED = new Image("res/unlocked_door.png");

    /**
     * Creates a new door that connects to a specified room.
     *
     * @param position   The position of the door on the map.
     * @param toRoomName The name of the room this door connects to.
     */
    public Door(Point position, String toRoomName) {
        super(position, LOCKED);
        this.toRoomName = toRoomName;
    }

    /**
     * Creates a new door inside a battle room that connects to another room.
     *
     * @param position    The position of the door on the map.
     * @param toRoomName  The name of the connected room.
     * @param battleRoom  The battle room this door belongs to.
     */
    public Door(Point position, String toRoomName, BattleRoom battleRoom) {
        super(position, LOCKED);
        this.toRoomName = toRoomName;
        this.battleRoom = battleRoom;
    }

    /**
     * Updates the door’s interaction with the player and projectiles.
     * Checks for collisions, handles room transitions, and reacts to projectiles.
     *
     * @param player          The player interacting with the door.
     * @param allProjectiles  The list of all active projectiles in the game.
     */
    public void update(Player player, ArrayList<Projectile> allProjectiles) {
        if (hasContactWith(player)) {
            onContactWithPlayer(player);
        } else {
            onNoLongerCollide();
        }

        for (Projectile p: allProjectiles){
            if(hasCollidedWith(p)) {
                p.setDestroyed(true);
            }
        }
    }

    /**
     * Unlocks the door and changes its image to the unlocked state.
     *
     * @param justEntered Whether the player has just entered the room through this door.
     */
    public void unlock(boolean justEntered) {
        unlocked = true;
        super.setImage(UNLOCKED);
        this.justEntered = justEntered;
    }

    /**
     * Handles the event when the player comes into contact with the door.
     *
     * @param player The player who contacts the door.
     */
    @Override
    public void onContactWithPlayer(Player player) {
        // when the player only just entered this door's room, overlapping with the unlocked door shouldn't trigger room transition
        if (unlocked && !justEntered) {
            ShadowDungeon.changeRoom(toRoomName);
        }
        if (!unlocked) {
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
    }

    // Private helper for managing collision exit logic
    private void onNoLongerCollide() {
        // when the player only just moved away from the unlocked door after walking through it
        if (unlocked && justEntered) {
            justEntered = false;

            // Battle Room activation conditions
            if (shouldLockAgain && battleRoom != null && !battleRoom.isComplete()) {
                unlocked = false;
                super.setImage(LOCKED);
                battleRoom.activateEnemies();
            }
        }
    }

    /**
     * Locks the door and sets its image to the locked state.
     */
    public void lock() {
        unlocked = false;
        super.setImage(LOCKED);
    }

    /**
     * Checks whether the door is unlocked.
     *
     * @return True if the door is unlocked, false otherwise.
     */

    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Sets the door to lock again
     * (only after the player moves away and the battle roomis stillincomplete).
     */

    public void setShouldLockAgain() {
        this.shouldLockAgain = true;
    }


    /**
     * Gets the current position of the door.
     *
     * @return The position of the door.
     */
    public Point getPosition() {
        return super.getPosition();
    }
}
