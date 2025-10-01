import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Player character that can move around and between rooms, defeat enemies, collect coins
 */
public class Player {
    private Point prevPosition;
    private Point position;
    private Character character;
    private Image currImage;
    private double health;
    private final double speed;
    private double coins = 0;
    private boolean faceLeft = false;
    private boolean canShoot = false;
    private final static int shootCooldown = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("bulletFreq"));
    private int lastShotInterval = shootCooldown; // assuming the player can immediately shoot when spawn

    public Player(Point position) {
        this.position = position;
        this.character = Character.ORIGINAL;
        this.currImage = character.getRightImage();
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("initialHealth"));
    }

    public void update(Input input) {
        // check movement keys and mouse cursor
        double currX = position.x;
        double currY = position.y;

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

        // update the player position accordingly and ensure it can't move past the game window
        Rectangle rect = currImage.getBoundingBoxAt(new Point(currX, currY));
        Point topLeft = rect.topLeft();
        Point bottomRight = rect.bottomRight();
        if (topLeft.x >= 0 && bottomRight.x <= Window.getWidth() && topLeft.y >= 0 && bottomRight.y <= Window.getHeight()) {
            move(currX, currY);
        }

        // Keep track of the last shoot interval
        if  (lastShotInterval < shootCooldown){
            lastShotInterval++;
        }
    }
    
    public void move(double x, double y) {
        prevPosition = position;
        position = new Point(x, y);
    }

    public void draw() {
        currImage = faceLeft ? character.getLeftImage() : character.getRightImage(); // NOTE: this is an example of using the ternary operator
        currImage.draw(position.x, position.y);
        UserInterface.drawStats(health, coins);
    }

    public void earnCoins(double coins) {
        this.coins += coins;
    }

    public void receiveDamage(double damage) {
        health -= damage;
        if (health <= 0) {
            ShadowDungeon.changeToGameOverRoom();
        }
    }

    public Point getPosition() {
        return position;
    }

    public Image getCurrImage() {
        return currImage;
    }

    public Point getPrevPosition() {
        return prevPosition;
    }

    public Character getCharacter() {
        return character;
    }

    public void changeCharacter(Character newCharacter){
        character = newCharacter;
        currImage = newCharacter.getRightImage();

        // allow shooting
        if (!canShoot) canShoot = true;
    }

    public Bullet shoot(Input input) {
        if (input.wasPressed(MouseButtons.LEFT) && canShoot && lastShotInterval == shootCooldown) {
            lastShotInterval = 0; // reset the shot timer
            Vector2 target = new Vector2(input.getMouseX(), input.getMouseY());
            return new Bullet(new Vector2(position.x, position.y), target);
        }
        return null; // no bullet fired
    }

}
