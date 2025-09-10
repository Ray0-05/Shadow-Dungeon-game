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
    
    /* Predefining player's starting properties */ 
    private double health = 100;
    private boolean isAlive = true;
    private int coin = 0;



    /* Construct the player with its starting 
    * location and movement speed gathered from gameProps*/
    public Player(Properties gameProps){
        this.SPEED = Double.parseDouble(gameProps.getProperty("movingSpeed"));
        this.coordinate = IOUtils.parseCoords(gameProps.getProperty("player.start"));
        this.xCoordinate = coordinate.x;
        this.yCoordinate = coordinate.y;
    }

    public Image getCurrDirection() {
        return currDirection;
    }

    public void setCurrDirection(Image currDirection) {
        this.currDirection = currDirection;
    }

    // Different methods for getting different coordinates
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

    /* Methods for moving RIGHT, LEFT, UP, DOWN */
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

}
