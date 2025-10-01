import bagel.Input;
import bagel.Window;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public abstract class Room {
    private Player player;
    private boolean stopCurrentUpdateCall = false; // this determines whether to prematurely stop the update execution
    private ArrayList<Projectiles> projectiles;

    public abstract void initEntities(Properties gameProperties);

    public void update(Input input) {
        if (player != null) {
            player.update(input);
            player.draw();
            Projectiles newBullet = player.shoot(input);
            if (newBullet != null) {
                projectiles.add(newBullet);
            }
        }

        Iterator<Projectiles> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectiles p = it.next();
            p.update();

            Point topLeft = p.getBoundingBox().topLeft();
            Point bottomRight = p.getBoundingBox().bottomRight();

            if (!(topLeft.x >= 0 && bottomRight.x <= Window.getWidth()
                    && topLeft.y >= 0 && bottomRight.y <= Window.getHeight())) {
                p.setDestroyed(true); // remove if its out of bounds
            }

            if (p.isDestroyed()){
                it.remove(); // remove all bullets that is set destroyed (etc crash with walls, enemy, tables, border)
            }else {
                p.draw();
            }
        }



    }

    protected boolean stopUpdatingEarlyIfNeeded() {
        if (stopCurrentUpdateCall) {
            player = null;
            stopCurrentUpdateCall = false;
            projectiles = new ArrayList<>();
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


    public ArrayList<Projectiles> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectiles> projectiles) {
        this.projectiles = projectiles;
    }

    public Player getPlayer() {
        return player;
    }

}
