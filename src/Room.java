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

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    public String getNAME_LABEL() {
        return NAME_LABEL;
    }

    /* -------Base Methods for subclasses (battle rooms override these) ----------- */

    // Subclasses can return collidable  objects (e.g., walls). Base room: none.
    protected GameObject[] getCollidableObjects() {
        return new GameObject[0];
    }

    // Subclasses can resolve enemy touches (touch-to-kill). Base room: no-op.
    public void resolveEnemyTouches(Player player) {
        // no enemies in non-battle rooms
    }

    // Are there any enemy gating doors? Base room: no.
    public boolean hasEnemy() {
        return false;
    }

    /* Non-battle rooms have no hazards by default. */
    public void resolveHazards(Player player) {
        // no-op
    }

    /* Renders a general room (non battle) display's and attribute */
    public void render(){
        BACKGROUND_IMAGE.drawFromTopLeft(0, 0);
        for (Door door : doors){
            door.render();
        }
    }

    public Rectangle retrieveRestartAreaBox(){
        return null;
    }

    /* -----------------Default Methods to be used by any Room ----------------*/

    /* Returns a Font where its style is constant with
    all the Rooms, but with specified size (input) */
    protected Font getGameFontOfSize(String sizeStr){
        return new Font(GAME_FONT_FILEPATH, Integer.parseInt(sizeStr));
    }
    /* A method to get the desired Door by specifying
       the room that the door have access to*/
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

    // A method to check if a new place can be moved to(whether its collidingwith concrete objects)
    public boolean canMoveTo(Rectangle newBoundingBox) {
        // Doors: block if non-overlappable
        if (doors != null) {
            for (Door door : doors) {
                if (newBoundingBox.intersects(door.getBoundingBox()) && !door.isOverlappable()) {
                    return false;
                }
            }
        }
        // Collidable objects come from subclass
        GameObject[] collidables = getCollidableObjects();
        if (collidables != null) {
            for (GameObject obj : collidables) {
                if (newBoundingBox.intersects(obj.getBoundingBox()) && !obj.isOverlappable()) {
                    return false;
                }
            }
        }
        return true;
    }



}
