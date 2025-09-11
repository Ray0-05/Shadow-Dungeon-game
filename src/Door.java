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

    public Door(String attribute){
        // build coordinate string directly
        super(IOUtils.parseCoordinateFromDoorAttribute(attribute),
                "res/locked_door.png",
                false);
        String[] attributeSplited = attribute.split(",");
        this.nextRoom = attributeSplited[2];
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


}
