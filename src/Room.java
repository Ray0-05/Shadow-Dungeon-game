import bagel.Image;
import bagel.util.Point;
import bagel.Font;
import java.util.Properties;

public abstract class Room {
    public static final Image BACKGROUND_IMAGE = new Image("res/background.png");

    protected final Point HEALTH_STAT_COORD;
    protected final Point COIN_STAT_COORD;
    protected final String HEALTH_DISPLAY;
    protected final String COIN_DISPLAY;

    protected final String GAME_FONT_FILEPATH;  // This Font is consistent for all the Rooms
    protected final Font PLAYER_STATS_FONT;

    protected Door[] doors;

    /* Initialise the Room with the specified Stats coordinates and font size in app.properties */
    public Room(Properties gameProps, Properties msgProps){
        //Storing a Standard Font Style for all the Rooms
        GAME_FONT_FILEPATH = gameProps.getProperty("font");

        /* Initialising the Font, Coordinates, Display Names for player stats display */
        PLAYER_STATS_FONT = getGameFontOfSize(gameProps.getProperty("playerStats.fontSize"));
        HEALTH_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("healthStat"));
        COIN_STAT_COORD = IOUtils.parseCoords(gameProps.getProperty("coinStat"));
        HEALTH_DISPLAY = msgProps.getProperty("healthDisplay");
        COIN_DISPLAY = msgProps.getProperty("coinDisplay");
    }

    /* Returns a Font where its style is constant with all the Rooms,
     but with specified size (input) */
    protected Font getGameFontOfSize(String sizeStr){
        return new Font(GAME_FONT_FILEPATH, Integer.parseInt(sizeStr));
    }

    /* Renders a general room display's and attribute */
    public void render(){
        BACKGROUND_IMAGE.drawFromTopLeft(0,0);
        PLAYER_STATS_FONT.drawString(HEALTH_DISPLAY, HEALTH_STAT_COORD.x, HEALTH_STAT_COORD.y);
        PLAYER_STATS_FONT.drawString(COIN_DISPLAY, COIN_STAT_COORD.x, COIN_STAT_COORD.y);
        for (Door door : doors){
            door.render();
        }
    }
}
