import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class GameObject {
    protected Point coordinate;           // position
    protected Image sprite;               // image to render
    protected Rectangle boundingBox;      // collision area
    protected boolean overlappable;       // can the player walk through it?

    public GameObject(Point coordinate, String imagePath, boolean overlappable) {
        this.coordinate = coordinate;
        this.sprite = new Image(imagePath);
        this.boundingBox = sprite.getBoundingBoxAt(coordinate);
        this.overlappable = overlappable;
    }

    // ----- Getters -----
    public Point getCoordinate() { return coordinate; }
    public Rectangle getBoundingBox() { return boundingBox; }
    public boolean isOverlappable() { return overlappable; }

    // ----- Update bounding box when moving (only for player for this assignment)-----
    protected void updateBoundingBox() {
        this.boundingBox = sprite.getBoundingBoxAt(coordinate);
    }

    protected  void updateSpriteImage(Image sprite){
        this.sprite = sprite;
    }
    // ----- Render the object -----
    public void render() {
        sprite.draw(coordinate.x, coordinate.y);
    }

    // Allow subclasses to define special behaviour (optional)
    public void onPlayerCollision(Player player) { }
}
