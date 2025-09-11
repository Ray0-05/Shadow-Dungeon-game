import bagel.util.Point;
import bagel.Image;
import bagel.util.Rectangle;
import java.util.Properties;

public class Door {
    /* Two types of Door Images, same for anytype of door */
    private static final Image DOOR_UNLOCKED = new Image("res/unlocked_door.png");
    private static final Image DOOR_LOCKED = new Image("res/locked_door.png");

    /* A door's attribute */
    private boolean isLocked = true;
    private Image currDoorStatus = DOOR_LOCKED;
    private String nextRoom;
    private Point coordinate;
    private Rectangle doorBoundingBox;

    public Door(String attribute){
        String[] attributeSplited = attribute.split(",");
        String coordinateStr = attributeSplited[0] + ',' + attributeSplited[1];
        this.coordinate = IOUtils.parseCoords(coordinateStr);
        this.doorBoundingBox = DOOR_UNLOCKED.getBoundingBoxAt(coordinate);
        this.nextRoom = attributeSplited[2];
    }

    public void unlock(){
        this.isLocked = false;
        currDoorStatus = DOOR_UNLOCKED;
    }

    public void lock(){
        this.isLocked = true;
        currDoorStatus = DOOR_LOCKED;
    }
    public String accessToRoom(){
        return nextRoom;
    }

    public boolean getIsLocked(){
        return isLocked;
    }

    public Rectangle getBoundingBox(){
        return doorBoundingBox;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void render(){
        currDoorStatus.draw(coordinate.x, coordinate.y);
    }

}
