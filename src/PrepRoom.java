import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Room where the game starts
 */
public class PrepRoom extends Room {
    private Door door;
    private RestartArea restartArea;

    public PrepRoom(String roomName){
        setRoomName(roomName);
    }

    public void initEntities(Properties gameProperties) {
        // find the configuration of game objects for this room
        super.setAllProjectiles(new ArrayList<>());
        for (Map.Entry<Object, Object> entry: gameProperties.entrySet()) {
            String roomSuffix = String.format(".%s", ShadowDungeon.PREP_ROOM_NAME);
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

    public void update(Input input) {
        UserInterface.drawStartMessages();
        UserInterface.drawCharacterSelectionImage();
        UserInterface.drawCharacterDescMessage();

        // update and draw all game objects in this room
        door.update(super.getPlayer(), getAllProjectiles());
        door.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }

        restartArea.update(input, super.getPlayer());
        restartArea.draw();


        // character changing logic && door unlocking mechanism
        if (input.wasPressed(Keys.R)){
            super.getPlayer().changeCharacter(Character.ROBOT);
            if (!findDoor().isUnlocked()){
                findDoor().unlock(false);
            }
        }
        else if (input.wasPressed(Keys.M)){
            super.getPlayer().changeCharacter(Character.MARINE);
            if (!findDoor().isUnlocked()){
                findDoor().unlock(false);
            }
        }

        // Update and renders player + bullets
        super.PlayerAndBulletsUpdate(input);
        getPlayer().draw();
        super.DeletionAndRenderingOfAllProjectiles();


    }

    public Door findDoor() {
        return door;
    }

}
