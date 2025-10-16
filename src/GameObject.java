import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents a general game object that has a position and an image.
 * All objects in the game inherit from this class.
 */
public abstract class GameObject {
    private Point position;
    private Image image;

    /**
     * Creates a new game object with a given position and image.
     *
     * @param position The position of the game object.
     * @param image    The image representing the game object.
     */
    public GameObject(Point position, Image image) {
        this.position = position;
        this.image = image;
    }

    /**
     * Draws the game object at its current position.
     */
    public void draw(){
        image.draw(position.x, position.y);
    }

    /**
     * Gets the bounding box of the game object at its current position.
     *
     * @return The rectangular area representing the game object's bounds.
     */
    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(position);
    }

    /**
     * Gets the current position of the game object.
     *
     * @return The position of the game object.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the image used to represent the game object.
     *
     * @return The image of the game object.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Updates the position of the game object.
     *
     * @param position The new position to set.
     */
    public void setPosition(Point position){
        this.position = position;
    }

    /**
     * Updates the image of the game object.
     *
     * @param image The new image to set.
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
