import bagel.Input;
import bagel.Window;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public abstract class Room {
    private Player player;
    private boolean stopCurrentUpdateCall = false; // this determines whether to prematurely stop the update execution
    private ArrayList<Projectile> allProjectiles;
    private String roomName;

    public abstract void initEntities(Properties gameProperties);

    public abstract void update(Input input);
    public abstract void renderOnly();

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

    public boolean stopUpdatingEarlyIfNeeded() {
        if (stopCurrentUpdateCall) {
            player = null;
            stopCurrentUpdateCall = false;
            allProjectiles = new ArrayList<>();
            return true;
        }
        return false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void stopCurrentUpdateCall() {
        stopCurrentUpdateCall = true;
    }


    public ArrayList<Projectile> getAllProjectiles() {
        return allProjectiles;
    }

    public void setAllProjectiles(ArrayList<Projectile> allProjectiles) {
        this.allProjectiles = allProjectiles;
    }

    public Player getPlayer() {
        return player;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
