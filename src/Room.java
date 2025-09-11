import bagel.Image;
import bagel.Font;
import java.util.Properties;

public abstract class Room {
    public static final Image BACKGROUND_IMAGE = new Image("res/background.png");

    protected final String GAME_FONT_FILEPATH;  // This Font is consistent for all the Rooms

    protected Door[] doors;  // Doors in this Room, defined in the child class

    /* Initialise the Room Standard Font*/
    public Room(Properties gameProps){
        //Storing a Standard Font Style for all the Rooms
        GAME_FONT_FILEPATH = gameProps.getProperty("font");
    }

    /* Returns a Font where its style is constant with all the Rooms,
     but with specified size (input) */
    protected Font getGameFontOfSize(String sizeStr){
        return new Font(GAME_FONT_FILEPATH, Integer.parseInt(sizeStr));
    }

    /* Renders a general room display's and attribute */
    public void render(){
        BACKGROUND_IMAGE.drawFromTopLeft(0,0);
        for (Door door : doors){
            door.render();
        }
    }
}
