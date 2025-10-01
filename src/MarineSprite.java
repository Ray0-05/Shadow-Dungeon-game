import bagel.Image;
import bagel.util.Point;

public class MarineSprite{
    private final Point position;
    private final Image image;

    public MarineSprite(Point position) {
        this.position = position;
        this.image = new Image("res/marine_sprite.png");
    }

    public void draw() {
        image.draw(position.x, position.y);
    }
}