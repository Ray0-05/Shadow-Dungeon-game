import bagel.util.Point;

public class Wall extends GameObject {
    public Wall(Point coordinate) {
        super(coordinate, "res/wall.png", false); // false → blocks movement
    }
}
