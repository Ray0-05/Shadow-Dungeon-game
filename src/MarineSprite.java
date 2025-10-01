import bagel.Image;
import bagel.util.Point;


// A very simple class to print out the marine sprite for visualisation
public class MarineSprite extends GameObject{

    public MarineSprite(Point position) {
        super(position, new Image("res/marine_sprite.png"));
    }

}