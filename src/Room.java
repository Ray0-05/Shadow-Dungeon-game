import bagel.Image;
import bagel.util.Point;
import bagel.Font;
import java.util.Properties;

public class Room {
    public static final Image BACKGROUND_IMAGE = new Image("res/background.png");

    protected final Point HEALTH_STAT_COORD;
    protected final Point COIN_STAT_COORD;
    protected final String HEALTH_DISPLAY;
    protected final String COIN_DISPLAY;
    protected final Font PLAYER_STATS_FONT;
//    private Room currentRoom = new PrepRoom();

    /* Initialise the Room with the specified Stats coordinates and font size in app.properties */
    public Room(Properties gameProps, Properties msgProps){
        int statsFontSize = Integer.parseInt(gameProps.getProperty("playerStats.fontSize"));
        String statsFontFilePath = gameProps.getProperty("font");


        /* Initialising the Font, Coordinates, Display Names for player stats display */
        PLAYER_STATS_FONT = new Font(statsFontFilePath,statsFontSize);
        HEALTH_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("healthStat"));
        COIN_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("coinStat"));
        HEALTH_DISPLAY = msgProps.getProperty("healthDisplay");
        COIN_DISPLAY = msgProps.getProperty("coinDisplay");
    }

    public void render(){
        BACKGROUND_IMAGE.drawFromTopLeft(0,0);
        PLAYER_STATS_FONT.drawString(HEALTH_DISPLAY, HEALTH_STAT_COORD.x, HEALTH_STAT_COORD.y);
        PLAYER_STATS_FONT.drawString(COIN_DISPLAY, COIN_STAT_COORD.x, COIN_STAT_COORD.y);
    }
}
