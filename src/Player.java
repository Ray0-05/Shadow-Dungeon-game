import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

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
            if (!door.getIsLocked() && this.isOverlappingWith(door.getBoundingBox())) {
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

    public void moveRight(){
        if(this.isWithinBound(this.coordinate.x + SPEED, this.coordinate.y)){
            updateCoordinateX(this.coordinate.x + SPEED);
        }
    }
    public void moveLeft(){
        if(this.isWithinBound(this.coordinate.x - SPEED, this.coordinate.y)){
            updateCoordinateX(this.coordinate.x - SPEED);
        }
    }
    public void moveUp(){
        if (this.isWithinBound(this.coordinate.x, this.coordinate.y - SPEED)){
            updateCoordinateY(this.coordinate.y - SPEED);
        }
    }
    public void moveDown(){
        if (this.isWithinBound(this.coordinate.x, this.coordinate.y + SPEED)){
            updateCoordinateY(this.coordinate.y + SPEED);
        }
    }

    /* ------------------helper functions for moving----------------*/
    private void updateCoordinateX(double x){
        this.coordinate = new Point(x, this.coordinate.y);
    }
    private void updateCoordinateY(double y){
        this.coordinate = new Point(this.coordinate.x, y);
    }
    private boolean isWithinBound(double xCoordinate, double yCoordinate){
        if (xCoordinate - SPEED < 0 || xCoordinate > MAX_XCOORDINATE ||
                yCoordinate < 0 || yCoordinate > MAX_YCOORDINATE) return false;
        return true;
    }

    /* ------------------------Method for checking if the player overlaps with the area of other objects----------*/
    public boolean isOverlappingWith(Rectangle object){
        if (boundingBox.intersects(object)) return true;
        return false;
    }
    /* Method for rendering the player */

    public void render(){
        super.render();
        updateBoundingBox();
        PLAYER_STATS_FONT.drawString(HEALTH_DISPLAY + " " + health, HEALTH_STAT_COORD.x, HEALTH_STAT_COORD.y);
        PLAYER_STATS_FONT.drawString(COIN_DISPLAY +  " " + coin, COIN_STAT_COORD.x, COIN_STAT_COORD.y);
    }

}
