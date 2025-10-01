import bagel.util.Point;
import bagel.Image;
import bagel.util.Vector2;

public class Bullet extends Projectiles{

    public Bullet(Vector2 startPosition, Vector2 target){
        super(startPosition, target, new Image("res/bullet.png"),
              Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletSpeed")),
              Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletFreq"))
        );

    }

}
