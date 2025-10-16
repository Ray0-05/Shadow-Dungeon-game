import bagel.Input;
import bagel.Window;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * Represents a game room, managing entities, player, and projectiles.
 * Concrete subclasses must implement initialization, updateAndRender, and rendering logic.
 */
public abstract class Room {
    private Player player;
    private boolean stopCurrentUpdateCall = false; // this determines whether to prematurely stop the updateAndRender execution
    private ArrayList<Projectile> allProjectiles;
    private String roomName;

    /**
     * Initialize all entities in the room based on game properties.
     *
     * @param gameProperties The properties file containing game configuration values.
     */
    public abstract void initEntities(Properties gameProperties);

    /**
     * Updates and render the room state each frame.
     *
     * @param input The current input state.
     */
    public abstract void updateAndRender(Input input);

    /**
     * Renders the room without updating any entities.
     */
    public abstract void renderOnly();

    /**
     * Updates the player and any bullets they have fired.
     *
     * @param input The current input state.
     */
    public void PlayerAndBulletsUpdate(Input input) {
        if (player != null) {
            if (this instanceof BattleRoom) {
                player.update(input, allProjectiles); // checks if hit by fireball too
            } else {
                player.update(input);
            }
            Bullet newBullet = player.shoot(input);
            if (newBullet != null) {
                allProjectiles.add(newBullet);
            }
        }
    }

    /**
     * Updates, removes, and renders all projectiles in the room.
     */
    public void DeletionAndRenderingOfAllProjectiles(){
        Iterator<Projectile> it = allProjectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update();

            Point topLeft = p.getBoundingBox().topLeft();
            Point bottomRight = p.getBoundingBox().bottomRight();

            if (!(topLeft.x >= 0 && bottomRight.x <= Window.getWidth()
                    && topLeft.y >= 0 && bottomRight.y <= Window.getHeight())) {
                p.setDestroyed(true); // remove if its out of bounds
            }

            // Double confirm with this
            if (p.isDestroyed()) {
                it.remove(); // remove all allProjectiles that is set destroyed (etc crash with walls, enemy, tables, border)
            } else {
                p.draw();
            }
        }
    }

    /**
     * Checks if the current update call should be stopped early.
     *
     * @return True if updating should stop early, false otherwise.
     */
    public boolean stopUpdatingEarlyIfNeeded() {
        if (stopCurrentUpdateCall) {
            player = null;
            stopCurrentUpdateCall = false;
            allProjectiles = new ArrayList<>();
            return true;
        }
        return false;
    }

    /**
     * Sets the player in the room.
     *
     * @param player The player object.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Flags the room to stop the current update call prematurely.
     */
    public void stopCurrentUpdateCall() {
        stopCurrentUpdateCall = true;
    }

    /**
     * Gets all projectiles currently in the room.
     *
     * @return A list of all projectiles.
     */
    public ArrayList<Projectile> getAllProjectiles() {
        return allProjectiles;
    }

    /**
     * Sets the list of projectiles in the room.
     *
     * @param allProjectiles The list of projectiles to set.
     */
    public void setAllProjectiles(ArrayList<Projectile> allProjectiles) {
        this.allProjectiles = allProjectiles;
    }

    /**
     * Gets the player in the room.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the name of the room.
     *
     * @return The room name.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets the name of the room.
     *
     * @param roomName The name to set for the room.
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
