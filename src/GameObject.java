import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class GameObject {
    private Point position;
    private Image image;

    public GameObject(Point position, Image image) {
        this.position = position;
        this.image = image;
    }

    public void draw(){
        image.draw(position.x, position.y);
    }

    // Returns the bounding box of the object at its current location
    public Rectangle getBoundingBox(){
        return image.getBoundingBoxAt(position);
    }

    public Point getPosition() {
        return position;
    }

    public Image getImage() {
        return image;
    }

    public void setPosition(Point position){
        this.position = position;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
