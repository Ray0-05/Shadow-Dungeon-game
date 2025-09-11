import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class GameObject {
    protected final Point INITIAL_POSITION;
    protected Point coordinate;           // position
    protected Image sprite;               // image to render
    protected Rectangle boundingBox;      // collision area
    protected boolean overlappable;       // can the player walk through it?

    public GameObject(Point coordinate, String imagePath, boolean overlappable) {
        this.coordinate = coordinate;
        this.INITIAL_POSITION = coordinate;
        this.sprite = new Image(imagePath);
        this.boundingBox = sprite.getBoundingBoxAt(coordinate);
        this.overlappable = overlappable;
    }

    // ----- Getters -----
    public Point getCoordinate() { return coordinate; }
    public Rectangle getBoundingBox() { return boundingBox; }
    public boolean isOverlappable() { return overlappable; }
    public Point getINITIAL_POSITION(){ return INITIAL_POSITION;}

    // ----- Update bounding box when moving (only for player for this assignment)-----
    protected void updateBoundingBox() {
        this.boundingBox = sprite.getBoundingBoxAt(coordinate);
    }

    protected  void updateSpriteImage(Image sprite){
        this.sprite = sprite;
    }
    // ------Perhaps different image will represent different overlapping state ---
    protected  void updateSpriteImage(Image sprite, boolean overlappable){
        this.sprite = sprite;
        this.overlappable = overlappable;
    }
    // ----- Render the object -----
    public void render() {
        sprite.draw(coordinate.x, coordinate.y);
    }

    // Allow subclasses to define special behaviour (optional)
    public void onPlayerCollision(Player player) { }
}
