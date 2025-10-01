import bagel.Input;

import java.util.ArrayList;
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

        for (Projectiles p: projectiles) {
            p.update();
            p.draw();
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
