import bagel.Font;
import bagel.Image;
import bagel.util.Point;

import java.util.Properties;

public class Player {
    /*-----------------------------------CONSTANTS--------------------------*/
    
    // Defining left and right facing image of the player
    private final Image leftDirection = new Image("res/player_left.png");
    private final Image rightDirection = new Image("res/player_right.png");
    // Player's Speed
    private final double SPEED;

    /*----------------------------------ATTRIBUTES ---------------------------*/
    
    /* Player starts by facing right and the starting coordinate
     * is initialised by calling the constructor */
    private Image currDirection = rightDirection;
    private Point coordinate;
    private double xCoordinate;
    private double yCoordinate;
    
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
        this.SPEED = Double.parseDouble(gameProps.getProperty("movingSpeed"));
        this.coordinate = IOUtils.parseCoords(gameProps.getProperty("player.start"));
        this.xCoordinate = coordinate.x;
        this.yCoordinate = coordinate.y;

        /* Initialising the Font, Coordinates, Display Names for player stats display */
        PLAYER_STATS_FONT = new Font(gameProps.getProperty("font"),Integer.parseInt(gameProps.getProperty("playerStats.fontSize")));
        HEALTH_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("healthStat"));
        COIN_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("coinStat"));
        HEALTH_DISPLAY = msgProps.getProperty("healthDisplay");
        COIN_DISPLAY = msgProps.getProperty("coinDisplay");
    }

    public Image getCurrDirection() {
        return currDirection;
    }

    public void setCurrDirection(Image currDirection) {
        this.currDirection = currDirection;
    }

    /* ----------------Different methods for getting different coordinates------------ */

    public Point getCoordinate() {
        return coordinate;
    }

    public double getCoordinateX(){
        return xCoordinate;
    }

    public double getCoordinateY(){
        return yCoordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
        this.xCoordinate = coordinate.x;
        this.yCoordinate = coordinate.y;
    }

    /* ------------Methods for moving RIGHT, LEFT, UP, DOWN --------------- */

    public void moveRight(){
        xCoordinate += SPEED;
    }
    public void moveLeft(){
        xCoordinate -= SPEED;
    }
    public void moveUp(){
        yCoordinate -= SPEED;
    }
    public void moveDown(){
        yCoordinate += SPEED;
    }
    /* Method for rendering the player (called in Shadowdungeon update() */

    public void render(){
        currDirection.draw(xCoordinate, yCoordinate);
        PLAYER_STATS_FONT.drawString(HEALTH_DISPLAY, HEALTH_STAT_COORD.x, HEALTH_STAT_COORD.y);
        PLAYER_STATS_FONT.drawString(COIN_DISPLAY, COIN_STAT_COORD.x, COIN_STAT_COORD.y);
    }

}
