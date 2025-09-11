import bagel.Image;
import bagel.Font;
import bagel.util.Point;

import java.util.Properties;

public abstract class Room {

    public static final Image BACKGROUND_IMAGE = new Image("res/background.png");

    private final String NAME_LABEL;

    protected final String GAME_FONT_FILEPATH;  // This Font is consistent for all the Rooms

    protected Door[] doors;  // Doors in this Room, defined in the child class

    /* Initialise the Room Standard Font*/
    public Room(Properties gameProps, String nameLabel){
        //Storing a Standard Font Style for all the Rooms
        GAME_FONT_FILEPATH = gameProps.getProperty("font");
        NAME_LABEL = nameLabel;
    }

    /* Returns a Font where its style is constant with all the Rooms,
     but with specified size (input) */
    protected Font getGameFontOfSize(String sizeStr){
        return new Font(GAME_FONT_FILEPATH, Integer.parseInt(sizeStr));
    }

    /* ------A method to get the desired Door Coordinates by specifying
                the room that the door have access to----------*/
    protected Point getDoorCoord(String destinationRoom){
        for (Door door : doors){
            if (door.getNextRoom().equals(destinationRoom)){
                return door.getCoordinate();
            }
        }
        return null;
    }

    public String getNAME_LABEL() {
        return NAME_LABEL;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return this.NAME_LABEL.equals(room.getNAME_LABEL());
    }

    public void unlockAllDoors(){
        for (Door door : doors){
            door.unlock();
        }
    }

    public void lockAllDoor(){
        for (Door door : doors){
            door.lock();
        }
    }

    /* Renders a general room display's and attribute */
    public void render(){
        BACKGROUND_IMAGE.drawFromTopLeft(0,0);
        for (Door door : doors){
            door.render();
        }
    }
}
