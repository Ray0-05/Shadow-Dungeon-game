import bagel.util.Point;
import bagel.Image;
import bagel.util.Rectangle;
import java.util.Properties;

public class Door {
    /* Two types of Door Images with the same bounding box, same for anytype of door */
    private static final Image DOOR_UNLOCKED = new Image("res/unlocked_door.png");
    private static final Image DOOR_LOCKED = new Image("res/locked_door.png");
    private static final Rectangle BOUNDING_BOX = DOOR_UNLOCKED.getBoundingBox();


    private boolean isLocked = false;
    private Image currDoorStatus = DOOR_UNLOCKED;
    private Point coordinate;

    public Door(Point coordinate){
        this.coordinate = coordinate;
    }

    public void unlock(){
        this.isLocked = false;
        currDoorStatus = DOOR_UNLOCKED;
    }

    public void lock(){
        this.isLocked = true;
        currDoorStatus = DOOR_LOCKED;
    }

    public boolean inContact(Rectangle Player){
        if (!isLocked){
            if (BOUNDING_BOX.intersects(Player)){
                return true;
            }
        }
        return false;
    }

}
