import bagel.Image;
import bagel.util.Point;
import jdk.jshell.execution.LocalExecutionControl;

import java.util.ArrayList;

/**
 * Door which can be locked or unlocked, allows the player to move to the room it's connected to
 */
public class Door extends GameObject implements CollidableWithPlayer, CollidableWithProjectiles{
    public final String toRoomName;
    public BattleRoom battleRoom; // only set if this door is inside a Battle Room
    private boolean unlocked = false;
    private boolean justEntered = false; // when the player only just entered this door's room
    private boolean shouldLockAgain = false;

    private static final Image LOCKED = new Image("res/locked_door.png");
    private static final Image UNLOCKED = new Image("res/unlocked_door.png");

    public Door(Point position, String toRoomName) {
        super(position, LOCKED);
        this.toRoomName = toRoomName;
    }

    public Door(Point position, String toRoomName, BattleRoom battleRoom) {
        super(position, LOCKED);
        this.toRoomName = toRoomName;
        this.battleRoom = battleRoom;
    }

    public void update(Player player, ArrayList<Projectiles> projectiles) {
        if (hasCollidedWith(player)) {
            onCollideWith(player);
        } else {
            onNoLongerCollide();
        }

        for (Projectiles p: projectiles){
            if(hasCollidedWith(p)) {
                p.setDestroyed(true);
            }
        }
    }

    public void unlock(boolean justEntered) {
        unlocked = true;
        super.setImage(UNLOCKED);
        this.justEntered = justEntered;
    }


    private void onCollideWith(Player player) {
        // when the player only just entered this door's room, overlapping with the unlocked door shouldn't trigger room transition
        if (unlocked && !justEntered) {
            ShadowDungeon.changeRoom(toRoomName);
        }
        if (!unlocked) {
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
    }

    private void onNoLongerCollide() {
        // when the player only just moved away from the unlocked door after walking through it
        if (unlocked && justEntered) {
            justEntered = false;

            // Battle Room activation conditions
            if (shouldLockAgain && battleRoom != null && !battleRoom.isComplete()) {
                unlocked = false;
                super.setImage(LOCKED);
                battleRoom.activateEnemies();
            }
        }
    }

    public void lock() {
        unlocked = false;
        super.setImage(LOCKED);
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setShouldLockAgain() {
        this.shouldLockAgain = true;
    }

    public Point getPosition() {
        return super.getPosition();
    }
}
