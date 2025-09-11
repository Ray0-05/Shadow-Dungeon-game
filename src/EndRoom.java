import java.util.Properties;
import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class EndRoom extends Room implements Restartable {
    // variables for the title attribute
    private final String TITLE;
    private final Font TITLE_FONT;
    private final double TITLE_YCOORD;private final double TITLE_XCOORD;


    // variables for the restart_area attribute
    private final Point RESTART_AREA_COORD;
    private final Rectangle RESTART_AREA_BOX;



    public EndRoom(Properties gameProps, Properties msgProps, String nameLabel, boolean win){
        // Initialise the basic Display and attributes of a room
        super(gameProps, nameLabel);
        setDoors(new Door[] {new Door(gameProps.getProperty("door.end"))});
        if (win){
            TITLE = msgProps.getProperty("gameEnd.won");
            unlockAllDoors();
        }else{
            TITLE = msgProps.getProperty("gameEnd.lost");
            lockAllDoor();
        }
        // Initialise EndRoom specific displays and attributes
        TITLE_FONT = super.getGameFontOfSize(gameProps.getProperty("title.fontSize"));
        TITLE_YCOORD = Double.parseDouble(gameProps.getProperty("title.y"));
        TITLE_XCOORD = CENTRE_OF_ROOM.x - (TITLE_FONT.getWidth(TITLE)/2);


        RESTART_AREA_COORD = getRestartAreaCoordinate(gameProps, "res/restartarea.end");
        RESTART_AREA_BOX = getRESTART_AREA_BOX(RESTART_AREA_COORD);
    }

    public Rectangle retrieveRestartAreaBox(){
        return RESTART_AREA_BOX;
    }

    /* A method to render all the displays and attributes of a general room,
        and also EndRoom specific's displays */
    @Override
    public void render(){
        super.render();
        TITLE_FONT.drawString(TITLE, TITLE_XCOORD, TITLE_YCOORD);
        RESTART_AREA.draw(RESTART_AREA_COORD.x, RESTART_AREA_COORD.y);
    }
}
