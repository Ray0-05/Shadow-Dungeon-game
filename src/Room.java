import bagel.Image;
import bagel.Font;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Properties;

public abstract class Room {

    protected static final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final String NAME_LABEL;
    protected final String GAME_FONT_FILEPATH;   // This Font is consistent for all the Rooms
    protected final Point CENTRE_OF_ROOM;


    private Door[] doors;  // Doors in this Room, defined in the child class
    private GameObject[] objects;  // walls, water, treasures, etc.
    protected boolean hasBoss = false;

    /* Initialise the Room Standard Font*/
    public Room(Properties gameProps, String nameLabel){
        //Storing a Standard Font Style for all the Rooms
        GAME_FONT_FILEPATH = gameProps.getProperty("font");
        NAME_LABEL = nameLabel;
        CENTRE_OF_ROOM = new Point(Double.parseDouble(gameProps.getProperty("window.width")) / 2,
                Double.parseDouble(gameProps.getProperty("window.height")) / 2);
    }

    /* ---------------------------Getters & Setters-------------------------*/
    public Door[] getDoors(){
        return doors;
    }

    public GameObject[] getObjects() {
        return objects;
    }

    public boolean hasBoss() {
        return hasBoss;
    }

    public String getNAME_LABEL() {
        return NAME_LABEL;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    public void setObjects(GameObject[] objects) {
        this.objects = objects;
    }

    public void setHasBoss(boolean hasBoss) {
        this.hasBoss = hasBoss;
    }

    /* Returns a Font where its style is constant with all the Rooms,
         but with specified size (input) */
    protected Font getGameFontOfSize(String sizeStr){
        return new Font(GAME_FONT_FILEPATH, Integer.parseInt(sizeStr));
    }

    /* ------A method to get the desired Door by specifying
                the room that the door have access to----------*/
    protected Door findDoorTo(String destinationRoom){
        for (Door door : doors){
            if (door.getNextRoom().equals(destinationRoom)){
                return door;
            }
        }
        return null;
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

    public boolean canMoveTo(Rectangle newBoundingBox) {
        // Check doors first
        for (Door door : doors) {
            if (newBoundingBox.intersects(door.getBoundingBox()) && !door.isOverlappable()) {
                return false; // blocked by closed/locked door
            }
        }
        // Check other objects
        if (objects != null) {
            for (GameObject obj : objects) {
                if (newBoundingBox.intersects(obj.getBoundingBox()) && !obj.isOverlappable()) {
                    return false; // blocked by wall or other solid object
                }
            }
        }
        return true; // free to move
    }


    /* Renders a general room display's and attribute */
    public void render(){
        BACKGROUND_IMAGE.drawFromTopLeft(0,0);
        if (objects != null) {
            for (GameObject obj : objects) {
                obj.render();
            }
        }
        for (Door door : doors){
            door.render();
        }
    }
}
