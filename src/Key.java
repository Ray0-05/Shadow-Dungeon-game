import bagel.Image;
import bagel.util.Point;

public class Key extends GameObject implements CollidableWithPlayer{
    private boolean isCollected = false;

    public Key(Point position){
        super(position, new Image("res/key.png"));
    }

    public void update(Player player){
        if (this.hasContactWith(player)){
            player.collectKey();
            isCollected = true;
        }
    }

    public boolean isCollected() {
        return isCollected;
    }
}
