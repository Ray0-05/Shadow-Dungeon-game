import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class KeyBulletKin extends Enemy{
    private boolean active = false; // only true when the Battle Room has been activated
    private ArrayList<Point> path;
    private int currentIndex;
    private Vector2 currVelocity;
    private Vector2 positionV;
    private static final int SPEED = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("keyBulletKinSpeed"));
    
    public KeyBulletKin(String coords) {
        super(IOUtils.parseCoords(coords.split(";")[0]), EnemyCharacter.KEY_BULLET_KIN);
        path = new ArrayList<>();
        currentIndex = 0;
        positionV = new Vector2(getPosition().x, getPosition().y);

        for (String coord: coords.split(";")){
            this.path.add(IOUtils.parseCoords(coord));
        }
    }


    @Override
    public void update(Player player, ArrayList<Projectile> allProjectiles) {
        if (isAtEndPoint()){
            currVelocity = getVelocity();
        }

        goToNextLoc(currVelocity);

        if (hasContactWith(player)){
            super.OnContactWithPlayer(player);
        }

        for (Projectile p : allProjectiles){
            if ((p instanceof Bullet) && hasCollidedWith(p)){
                takeDamage(p.getDamage());
                p.setDestroyed(true);
            }
            if (isDead()){
                active = false;
            }
        }



    }

    private void goToNextLoc(Vector2 velocity){
        positionV = positionV.add(velocity);
        setPosition(positionV.asPoint());
    }

    public Vector2 getVelocity() {
        Point startPosition = path.get(currentIndex);
        Vector2 currentVector = new Vector2(startPosition.x, startPosition.y);

        int nextIndex  = (currentIndex + 1) % path.size();

        Point target = path.get(nextIndex);
        Vector2 targetVector = new Vector2(target.x, target.y);

        Vector2 direction = targetVector.sub(currentVector).normalised();

        currentIndex = nextIndex;

        return direction.mul(SPEED);
    }

    private boolean isAtEndPoint(){
        Point target = path.get(currentIndex);
        return getPosition().distanceTo(target) < SPEED;
    }


    public boolean isDead() {
        return getHealth() <= 0;
    }

    public Key dropKey(){
        return new Key(getPosition());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
