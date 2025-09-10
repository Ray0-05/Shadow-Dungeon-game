import java.util.Properties;
import bagel.Font;
import bagel.Image;
import bagel.util.Point;

public class PrepRoom extends Room {
    private final String TITLE;
    private final Font TITLE_FONT;
    private final double TITLE_YCOORD;
    private final double TITLE_XCOORD;

    private final String MOVE_MSG;
    private final Font MOVE_MSG_FONT;
    private final double MOVE_MSG_YCOORD;
    private final double MOVE_MSG_XCOORD;

    private final Image RESTART_AREA = new Image("res/restart_area.png");
    private final Point RESTART_AREA_COORD;


    public PrepRoom(Properties gameProps, Properties msgProps){
        super(gameProps, msgProps);

        TITLE = msgProps.getProperty("title");
        TITLE_FONT = super.getGameFontOfSize(gameProps.getProperty("title.fontSize"));
        TITLE_YCOORD = Double.parseDouble(gameProps.getProperty("title.y"));
        TITLE_XCOORD = 512 - TITLE_FONT.getWidth(TITLE)/2;

        MOVE_MSG = msgProps.getProperty("moveMessage");
        MOVE_MSG_FONT = super.getGameFontOfSize((gameProps.getProperty("prompt.fontSize")));
        MOVE_MSG_YCOORD = Double.parseDouble(gameProps.getProperty("moveMessage.y"));
        MOVE_MSG_XCOORD = 512 - MOVE_MSG_FONT.getWidth(MOVE_MSG)/2;

        RESTART_AREA_COORD = IOUtils.parseCoords(gameProps.getProperty("restartarea.prep"));

    }

    @Override
    public void render(){
        super.render();
        TITLE_FONT.drawString(TITLE, TITLE_XCOORD, TITLE_YCOORD);
        MOVE_MSG_FONT.drawString(MOVE_MSG, MOVE_MSG_XCOORD, MOVE_MSG_YCOORD);
        RESTART_AREA.draw(RESTART_AREA_COORD.x, RESTART_AREA_COORD.y);
    }
}
