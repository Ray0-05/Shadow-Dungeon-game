import java.util.Properties;
import bagel.Font;
import bagel.util.Point;
import bagel.util.Rectangle;

public class PrepRoom extends Room implements Restartable {
    // variables for the title attribute
    private final String TITLE;
    private final Font TITLE_FONT;
    private final double TITLE_YCOORD;
    private final double TITLE_XCOORD;

    // variables for the move message attribute
    private final String MOVE_MSG;
    private final Font MOVE_MSG_FONT;
    private final double MOVE_MSG_YCOORD;
    private final double MOVE_MSG_XCOORD;

    // variables for the restart_area attribute
    private final Point RESTART_AREA_COORD;
    private final Rectangle RESTART_AREA_BOX;



    public PrepRoom(Properties gameProps, Properties msgProps, String nameLabel){
        // Initialise the basic Display and attributes of a room
        super(gameProps, nameLabel);

        // Initialise PrepRoom specific displays and attributes
        TITLE = msgProps.getProperty("title");
        TITLE_FONT = super.getGameFontOfSize(gameProps.getProperty("title.fontSize"));
        TITLE_YCOORD = Double.parseDouble(gameProps.getProperty("title.y"));
        TITLE_XCOORD = CENTRE_OF_ROOM.x - (TITLE_FONT.getWidth(TITLE)/2);

        MOVE_MSG = msgProps.getProperty("moveMessage");
        MOVE_MSG_FONT = super.getGameFontOfSize((gameProps.getProperty("prompt.fontSize")));
        MOVE_MSG_YCOORD = Double.parseDouble(gameProps.getProperty("moveMessage.y"));
        MOVE_MSG_XCOORD = CENTRE_OF_ROOM.x - (MOVE_MSG_FONT.getWidth(MOVE_MSG)/2);

        RESTART_AREA_COORD = getRestartAreaCoordinate(gameProps, "res/restartarea.prep");
        RESTART_AREA_BOX = getRESTART_AREA_BOX(RESTART_AREA_COORD);

        setDoors(new Door[] {new Door(gameProps.getProperty("door.prep"))});

    }

    public Rectangle retrieveRestartAreaBox(){
        return RESTART_AREA_BOX;
    }

    /* A method to render all the displays and attributes of a general room,
    and also PrepRoom specific's displays */
    @Override
    public void render(){
        super.render();
        TITLE_FONT.drawString(TITLE, TITLE_XCOORD, TITLE_YCOORD);
        MOVE_MSG_FONT.drawString(MOVE_MSG, MOVE_MSG_XCOORD, MOVE_MSG_YCOORD);
        RESTART_AREA.draw(RESTART_AREA_COORD.x, RESTART_AREA_COORD.y);
    }
}
