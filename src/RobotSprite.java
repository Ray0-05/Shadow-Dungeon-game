import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

public class RobotSprite {
    private static final Character robot = Character.ROBOT;
    private final Point position;
    private final Image image;

    public RobotSprite(Point position) {
        this.position = position;
        this.image = new Image("res/robot_sprite.png");
    }

    public void update(Input input, Player player) {
        if (hasCollidedWith(player) && input.wasPressed(Keys.R)) {
            player.changeCharacter(robot);
        }
    }

    public void draw() {
        image.draw(position.x, position.y);
    }

    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }
}
