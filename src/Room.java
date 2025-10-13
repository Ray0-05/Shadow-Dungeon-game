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

    public abstract void initEntities(Properties gameProperties);

    public void update(Input input) {
        if (player != null) {
            player.update(input);
            player.draw();
            Bullet newBullet = player.shoot(input);
            if (newBullet != null) {
                allProjectiles.add(newBullet);
            }
        }


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
            if (p.isDestroyed()){
                it.remove(); // remove all allProjectiles that is set destroyed (etc crash with walls, enemy, tables, border)
            }else {
                p.draw();
            }
        }



    }

    protected boolean stopUpdatingEarlyIfNeeded() {
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

}
