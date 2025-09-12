import bagel.util.Point;
import bagel.Image;
import bagel.util.Rectangle;

public class Door extends GameObject{
    /* Two types of Door Images, same for anytype of door */
    private static final Image DOOR_UNLOCKED = new Image("res/unlocked_door.png");
    private static final Image DOOR_LOCKED = new Image("res/locked_door.png");

    /* A door's attribute */
    private boolean isLocked = true;
    private String nextRoom;
    // Entry-flow control
    private boolean ignorePlayerOverlap = false; // suppress teleport while player is still overlapping right after spawn
    private boolean persistentOpen = false; // stays open after stepping away (for rooms without a boss)


    public Door(String attribute){
        // build coordinate string directly
        super(IOUtils.parseCoordinateFromDoorAttribute(attribute),
                "res/locked_door.png",
                false);
        String[] attributeSplited = attribute.split(",");
        this.nextRoom = attributeSplited[2];
    }

    public boolean isPlayerInside(Player player) {
        return this.getBoundingBox().intersects(player.getBoundingBox());
    }

    public void unlock(){
        this.isLocked = false;
        updateSpriteImage(DOOR_UNLOCKED, true);
    }

    public void lock(){
        this.isLocked = true;
        updateSpriteImage(DOOR_LOCKED, false);
    }

    public String getNextRoom(){
        return nextRoom;
    }

    public boolean getIsLocked(){
        return isLocked;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    /* Called on room entry for the door the player just came through.
     * Opens the door, prevents instant back-teleport until the player steps away once,
     * and sets whether the door should remain open afterwards (no-boss rooms).
     */
    public void markAsEntryDoor(boolean roomHasBoss) {
        unlock();                       // show unlocked sprite + overlappable true
        ignorePlayerOverlap = true;     // do not allow teleport until player steps off once
        persistentOpen = !roomHasBoss;  // safe rooms: door stays open after stepping away
    }

    /*
     * Single source of truth for "should this door teleport now?"
     * - If ignoring overlap: wait until the player has stepped away once;
     *   then either close (boss room) or remain open (no-boss).
     * - Otherwise: only teleport if unlocked and overlapping.
     */
    public boolean canTeleport(Player player) {
        if (ignorePlayerOverlap) {
            if (!isPlayerInside(player)) {
                // player has moved away once; clear guard and apply post-step behavior
                ignorePlayerOverlap = false;
                if (!persistentOpen) {
                    lock(); // close behind for boss rooms
                }
            }
            return false; // never teleport while ignoring
        }
        return !getIsLocked() && isPlayerInside(player);
    }



}
