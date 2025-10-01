import bagel.Image;
import bagel.util.Point;

// A very simple class to draw out robot sprite, might want to demote it to just an attribute in prep room
public class RobotSprite extends GameObject{

    public RobotSprite(Point position) {
        super(position, new Image("res/robot_sprite.png"));
    }

}
