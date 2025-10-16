import bagel.Input;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
/**
 * Room where the game ends when the player either completes all rooms or dies.
 * Contains a door to exit and a restart area to reset the game.
 */
public class EndRoom extends Room{
    private Door door;
    private RestartArea restartArea;
    private boolean isGameOver = false;

    /**
     * Creates an EndRoom with the specified name.
     *
     * @param roomName The name of the room.
     */
    public EndRoom(String roomName){
        setRoomName(roomName);
    }

    /**
     * Initializes the entities in the EndRoom based on game properties.
     *
     * @param gameProperties The properties file containing game configuration values.
     */
    public void initEntities(Properties gameProperties) {
        super.setAllProjectiles(new ArrayList<>());
        // find the configuration of game objects for this room
        for (Map.Entry<Object, Object> entry: gameProperties.entrySet()) {
            String roomSuffix = String.format(".%s", ShadowDungeon.END_ROOM_NAME);
            if (entry.getKey().toString().contains(roomSuffix)) {
                String objectType = entry.getKey().toString().substring(0, entry.getKey().toString().length() - roomSuffix.length());
                String propertyValue = entry.getValue().toString();

                switch (objectType) {
                    case "door":
                        String[] coordinates = propertyValue.split(",");
                        door = new Door(IOUtils.parseCoords(propertyValue), coordinates[2]);
                        break;
                    case "restartarea":
                        restartArea = new RestartArea(IOUtils.parseCoords(propertyValue));
                        break;
                    default:
                }
            }
        }
    }

    /**
     * Updates and renders all entities in the room each frame.
     *
     * @param input The current input state.
     */
    public void updateAndRender(Input input) {
        UserInterface.drawEndMessage(!isGameOver);

        // door should be locked if player got to this room by dying
        if (isGameOver) {
            findDoor().lock();
        }

        // updateAndRender and draw all game objects in this room
        door.update(super.getPlayer(), getAllProjectiles());
        door.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }

        restartArea.update(input, super.getPlayer());
        restartArea.draw();

        // Update and renders player + bullets
        super.PlayerAndBulletsUpdate(input);
        getPlayer().draw();
        super.DeletionAndRenderingOfAllProjectiles();
    }

    /**
     * Renders all entities in the room without updating any state.
     */
    public void renderOnly(){
        UserInterface.drawEndMessage(!isGameOver);
        door.draw();
        restartArea.draw();
        for (Projectile p : getAllProjectiles()){
            p.draw();
        }
        getPlayer().draw();
    }

    /**
     * Returns the door in the EndRoom.
     *
     * @return The door object.
     */
    public Door findDoor() {
        return door;
    }

    /**
     * Marks the game as over, locking the door.
     */
    public void isGameOver() {
        isGameOver = true;
    }
}
