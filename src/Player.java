import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Locale;
import java.util.Properties;

public class Player extends GameObject{
    /*-----------------------------------CONSTANTS--------------------------*/
    
    // Defining left and right facing image of the player, and also its bounding box
    private final Image LEFT_DIRECTION = new Image("res/player_left.png");
    private final Image RIGHT_DIRECTION = new Image("res/player_right.png");
    // Player's Speed
    private final double SPEED;
    // Player's maximum Xand Y coordinates
    private final double MAX_XCOORDINATE;
    private final double MAX_YCOORDINATE;

    /*----------------------------------ATTRIBUTES ---------------------------*/
    
    /* Player starts by facing right and the starting coordinate
     * is initialised by calling the constructor */
    
    /* Predefining player's starting stats properties */
    private double health = 100;
    private boolean isAlive = true;
    private int coin = 0;

    /* Player's Health and Coin display attributes */
    protected final Point HEALTH_STAT_COORD;
    protected final Point COIN_STAT_COORD;
    protected final String HEALTH_DISPLAY;
    protected final String COIN_DISPLAY;
    protected final Font PLAYER_STATS_FONT;



    /* Construct the player with its starting 
    * location, movement speed, and Health and Coin Display gathered from gameProps*/
    public Player(Properties gameProps, Properties msgProps){
        super(IOUtils.parseCoords(gameProps.getProperty("player.start")),
                "res/player_right.png", false);

        this.MAX_XCOORDINATE = Double.parseDouble(gameProps.getProperty("window.width"));
        this.MAX_YCOORDINATE = Double.parseDouble(gameProps.getProperty("window.height"));
        this.SPEED = Double.parseDouble(gameProps.getProperty("movingSpeed"));

        /* Initialising the Font, Coordinates, Display Names for player stats display */
        PLAYER_STATS_FONT = new Font(gameProps.getProperty("font"),Integer.parseInt(gameProps.getProperty("playerStats.fontSize")));
        HEALTH_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("healthStat"));
        COIN_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("coinStat"));
        HEALTH_DISPLAY = msgProps.getProperty("healthDisplay");
        COIN_DISPLAY = msgProps.getProperty("coinDisplay");
    }

    /* ------------Method for checking if the player is entering a new room ---------*/
    /* returns the specific room string its entering if there's any,
    and returns null if its not entering a new room */
    public String isEnteringNewRoom(Door[] doors){
        for (Door door: doors){
            if (door.canTeleport(this)) {
                return door.getNextRoom();
            }
        }
        return null;
    }

    /* -------------Method for teleporting the player to another location -------------*/

    public void teleportTo(Point coordinate){
        this.coordinate = coordinate;
    }

    /* ------------Methods for moving RIGHT, LEFT, UP, DOWN --------------- */

    public void moveRight(Room room) {
        Point newPos = new Point(this.coordinate.x + SPEED, this.coordinate.y);
        Rectangle newBox = sprite.getBoundingBoxAt(newPos);

        if (room.canMoveTo(newBox) && isWithinBound(newPos.x, newPos.y)) {
            this.coordinate = newPos;
            updateBoundingBox();
        }
    }

    public void moveLeft(Room room){
        Point newPos = new Point(this.coordinate.x - SPEED, this.coordinate.y);
        Rectangle newBox = sprite.getBoundingBoxAt(newPos);

        if (room.canMoveTo(newBox) && isWithinBound(newPos.x, newPos.y)) {
            this.coordinate = newPos;
            updateBoundingBox();
        }
    }
    public void moveUp(Room room){
        Point newPos = new Point(this.coordinate.x, this.coordinate.y - SPEED);
        Rectangle newBox = sprite.getBoundingBoxAt(newPos);

        if (room.canMoveTo(newBox) && isWithinBound(newPos.x, newPos.y)) {
            this.coordinate = newPos;
            updateBoundingBox();
        }
    }
    public void moveDown(Room room){
        Point newPos = new Point(this.coordinate.x, this.coordinate.y + SPEED);
        Rectangle newBox = sprite.getBoundingBoxAt(newPos);

        if (room.canMoveTo(newBox) && isWithinBound(newPos.x, newPos.y)) {
            this.coordinate = newPos;
            updateBoundingBox();
        }
    }

    /* ------------------helper functions for moving----------------*/
    private void updateCoordinateX(double x){
        this.coordinate = new Point(x, this.coordinate.y);
    }
    private void updateCoordinateY(double y){
        this.coordinate = new Point(this.coordinate.x, y);
    }

    private boolean isWithinBound(double x, double y){
        return x >= 0 && x <= MAX_XCOORDINATE
                && y >= 0 && y <= MAX_YCOORDINATE;
    }


    /* ------------------------Method for checking if the player overlaps with the area of other objects----------*/
    public boolean isOverlappingWith(Rectangle object){
        if (boundingBox.intersects(object)) return true;
        return false;
    }

    public void takeDamage(double amount) {
        if (!isAlive) return;
        this.health -= amount;
        if (this.health <= 0) {
            this.isAlive = false;
        }
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public int getCoins() { return coin; }

    public void addCoins(int amount) {
        if (amount <= 0) return;
        this.coin += amount;
    }


    /* Method for rendering the player */
    public void render(){
        updateBoundingBox();
        super.render();
        String healthText = String.format(Locale.US, "%.1f", this.health); // Format to one decimal place
        PLAYER_STATS_FONT.drawString(HEALTH_DISPLAY + " " + healthText, HEALTH_STAT_COORD.x, HEALTH_STAT_COORD.y);
        PLAYER_STATS_FONT.drawString(COIN_DISPLAY +  " " + coin, COIN_STAT_COORD.x, COIN_STAT_COORD.y);
    }

}
