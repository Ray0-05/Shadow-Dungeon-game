import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * Player character that can move around and between rooms, defeat enemies, collect coins
 */
public class Player extends GameObject implements Damageable, CollidableWithProjectiles {
    private Point prevPosition;
    private Character character;
    private double health;
    private final double speed;
    private double coins = 0;
    private boolean faceLeft = false;
    private Weapon weapon;
    private int keyNum = 0;
    private final CoolDownTimer coolDownTimer;

    private static final int EXTRA_COIN_PER_KILL = Integer.parseInt(
            ShadowDungeon.getGameProps().getProperty("robotExtraCoin"));

    public Player(Point position) {
        super(position, Character.ORIGINAL.getRightImage());
        this.character = Character.ORIGINAL;
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("initialHealth"));
        this.weapon = Weapon.STANDARD;
        this.coolDownTimer = new CoolDownTimer(weapon.getShotCooldown());
    }

    public void update(Input input) {
        // check movement keys and mouse cursor
        double currX = getPosition().x;
        double currY = getPosition().y;

        if (input.isDown(Keys.A)) {
            currX -= speed;
        }
        if (input.isDown(Keys.D)) {
            currX += speed;
        }
        if (input.isDown(Keys.W)) {
            currY -= speed;
        }
        if (input.isDown(Keys.S)) {
            currY += speed;
        }

        faceLeft = input.getMouseX() < currX;

        // update the player getPosition() accordingly and ensure it can't move past the game window
        Rectangle rect = getImage().getBoundingBoxAt(new Point(currX, currY));;
        Point topLeft = rect.topLeft();
        Point bottomRight = rect.bottomRight();
        if (topLeft.x >= 0 && bottomRight.x <= Window.getWidth() && topLeft.y >= 0 && bottomRight.y <= Window.getHeight()) {
            move(currX, currY);
        }

        // Reduce the shooting cooldown time
        coolDownTimer.tick();
    }

    public void update(Input input, ArrayList<Projectile> allProjectiles){
        // Check and deal when in contact with enemies fireball
        for (Projectile p : allProjectiles){
            if (p instanceof Fireball){
                if (this.hasCollidedWith(p)){
                    p.setDestroyed(true);
                    takeDamage(p.getDamage());
                }
            }
        }

        this.update(input); // movement logic done here

    }
    
    public void move(double x, double y) {
        prevPosition = getPosition();
        setPosition(new Point(x, y));
    }

    @Override
    public void draw() {
        setImage(faceLeft ? character.getLeftImage() : character.getRightImage());
        getImage().draw(getPosition().x, getPosition().y);
    }

    public void earnCoins(double coins) {
        this.coins += coins;
    }

    public void onDeath(){
        ShadowDungeon.changeToGameOverRoom();
    }

    @Override
    public void takeDamage(double damage) {
        setHealth((getHealth() - damage));
        if (isDead()) {
            ShadowDungeon.changeToGameOverRoom();
        }
    }

    public Point getPrevPosition() {
        return prevPosition;
    }

    public void changeCharacter(Character newCharacter){
        character = newCharacter;
        setImage(newCharacter.getRightImage());
    }

    public Bullet shoot(Input input) {
        if (input.wasPressed(MouseButtons.LEFT) && character != Character.ORIGINAL && coolDownTimer.readyToShoot()) {
            coolDownTimer.reset(); // reset the shot timer
            Vector2 target = new Vector2(input.getMouseX(), input.getMouseY());
            return new Bullet(new Vector2(getPosition().x, getPosition().y), target, weapon.getDamage());
        }
        return null; // no bullet fired
    }

    public boolean isImmuneToWater(){
        return character == Character.MARINE;
    }

    public int getExtraCoinPerKillIfEligible(){
        if (character == Character.ROBOT){
            return EXTRA_COIN_PER_KILL;
        }
        return 0;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double newHealth) {
        this.health = newHealth;
    }

    public void collectKey() {
        this.keyNum++;
    }

    public void useKey(){
        this.keyNum--;
    }

    public int getKeyNum() {
        return keyNum;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }
}
