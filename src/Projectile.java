import bagel.Image;
import bagel.util.Vector2;

public class Projectile extends GameObject implements CollidableWithPlayer{
    private final double speed;
    private int damage;
    private boolean destroyed = false;
    private Vector2 velocity;
    private Vector2 position;

    public Projectile(Vector2 start, Vector2 target, Image image, double speed, int damage){
        super(start.asPoint(), image);
        this.speed = speed;
        this.damage = damage;

        // Direction = (target - start)
        Vector2 direction = target.sub(start).normalised();

        // Velocity = direction * speed
        this.velocity = direction.mul(speed);
        this.position = start;
    }

    // Update projectiles position each frame
    public void update() {
        // might wanna add deltaTime (helps keep motion smooth across framerates)
        position = (position.add(velocity));
        // Make sure it later draws the image at the current position(Game Object's position updated)
        super.setPosition(position.asPoint());
    }


    public int getDamage() {
        return damage;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

}
