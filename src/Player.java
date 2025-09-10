import bagel.Image;
import bagel.util.Point;

public class Player {
    // Defining left and right facing image of the player
    private final Image leftDirection = new Image("res/player_left.png");
    private final Image rightDirection = new Image("res/player_right.png");
    // Player's Speed
    private final double SPEED;

    /* Player starts by facing right and the starting coordinate
     * is initialised by calling the constructor */
    private Image currDirection = rightDirection;
    private Point coordinate;
    private double xCoordinate;
    private double yCoordinate;



    /* Constructor for initialising a player
    - (facing left and in starting position) */
    public Player(String strCoordinate, double speed){
        this.coordinate = IOUtils.parseCoords(strCoordinate);
        xCoordinate = coordinate.x;
        yCoordinate = coordinate.y;
        SPEED = speed;
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
