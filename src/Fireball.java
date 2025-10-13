import bagel.Image;
import bagel.util.Vector2;

public class Fireball extends Projectile {

    public Fireball(Vector2 start, Vector2 target){
        super(start, target, new Image("res/fireball.png"),
              Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballSpeed")),
                Integer.parseInt(ShadowDungeon.getGameProps().getProperty("fireballDamage")));
    }
}
