import bagel.Input;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Room with doors that are locked until the plaer collect the key from keybulletkin
 */
public class BattleRoom extends Room{
    private Door primaryDoor;
    private Door secondaryDoor;
    private KeyBulletKin keyBulletKin;
    private Key key = null;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<BulletKin> bulletKins;
    private ArrayList<AshenBulletKin> ashenBulletKins;
    private ArrayList<TreasureBox> treasureBoxes;
    private ArrayList<River> rivers;
    private boolean isComplete = false;
    private final String nextRoomName;


    public BattleRoom(String roomName, String nextRoomName) {
        rivers = new ArrayList<>();
        treasureBoxes = new ArrayList<>();
        bulletKins = new ArrayList<>();
        ashenBulletKins = new ArrayList<>();
        obstacles = new ArrayList<>();
        setRoomName(roomName);
        this.nextRoomName = nextRoomName;
        super.setAllProjectiles(new ArrayList<>());
    }

    public void initEntities(Properties gameProperties) {
        // find the configuration of game objects for this room
        for (Map.Entry<Object, Object> entry: gameProperties.entrySet()) {
            String roomSuffix = String.format(".%s", this.getRoomName());

            if (entry.getKey().toString().contains(roomSuffix)) {
                String objectType = entry.getKey().toString()
                        .substring(0, entry.getKey().toString().length() - roomSuffix.length());
                String propertyValue = entry.getValue().toString();

                // ignore if the value is 0
                if (propertyValue.equals("0")) {
                    continue;
                }

                String[] coordinates;
                for (String coords: propertyValue.split(";")) {
                    switch (objectType) {
                        case "primarydoor":
                            coordinates = propertyValue.split(",");
                            primaryDoor = new Door(IOUtils.parseCoords(propertyValue), coordinates[2], this);
                            break;
                        case "secondarydoor":
                            coordinates = propertyValue.split(",");
                            secondaryDoor = new Door(IOUtils.parseCoords(propertyValue), coordinates[2], this);
                            break;
                        case "bulletKin":
                            BulletKin bk = new BulletKin(IOUtils.parseCoords(coords));
                            bulletKins.add(bk);
                            break;
                        case "ashenBulletKin":
                            AshenBulletKin abk = new AshenBulletKin(IOUtils.parseCoords(coords));
                            ashenBulletKins.add(abk);
                            break;
                        case "wall":
                            Wall wall = new Wall(IOUtils.parseCoords(coords));
                            obstacles.add(wall);
                            break;
                        case "treasurebox":
                            TreasureBox treasureBox = new TreasureBox(IOUtils.parseCoords(coords),
                                    Double.parseDouble(coords.split(",")[2]));
                            treasureBoxes.add(treasureBox);
                            break;
                        case "table":
                            Table table = new Table(IOUtils.parseCoords(coords));
                            obstacles.add(table);
                            break;
                        case "basket":
                            Basket basket = new Basket(IOUtils.parseCoords(coords));
                            obstacles.add(basket);
                            break;
                        case "river":
                            River river = new River(IOUtils.parseCoords(coords));
                            rivers.add(river);
                            break;
                        default:
                    }
                }

                if (objectType.equals("keyBulletKin")){
                    keyBulletKin = new KeyBulletKin(propertyValue);
                }
            }
        }
    }

    public void update(Input input) {
        // update and draw all active game objects in this room
        primaryDoor.update(super.getPlayer(), getAllProjectiles());
        primaryDoor.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }

        secondaryDoor.update(super.getPlayer(), getAllProjectiles());
        secondaryDoor.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }


        for (BulletKin bk: bulletKins){
            if (bk.isActive() && !bk.isDead()) {
                bk.update(getPlayer(), super.getAllProjectiles());
                bk.draw();
                Fireball fireball = bk.shoot(super.getPlayer());
                if (fireball != null){
                    super.getAllProjectiles().add(fireball);
                }
            }
        }
        bulletKins.removeIf(Damageable::isDead);

        for (AshenBulletKin abk: ashenBulletKins){
            if (abk.isActive() && !abk.isDead()){
                abk.update(super.getPlayer(), super.getAllProjectiles());
                abk.draw();
                Fireball fireball = abk.shoot(super.getPlayer());
                if (fireball != null){
                    super.getAllProjectiles().add(fireball);
                }
            }
        }
        ashenBulletKins.removeIf(Damageable::isDead);

        for (River river: rivers) {
            river.update(super.getPlayer());
            river.draw();
        }

        for (TreasureBox treasureBox: treasureBoxes) {
            if (treasureBox.isActive()) {
                treasureBox.update(input, super.getPlayer());
                treasureBox.draw();
            }
        }

        for (Obstacle o: obstacles){
            if (!o.isDestroyed()){
                o.update(getPlayer(), getAllProjectiles());
                o.draw();
            }
        }

        if (keyBulletKin.isActive() && !keyBulletKin.isDead()) {
            keyBulletKin.update(super.getPlayer(), getAllProjectiles());
            keyBulletKin.draw();
        }else if (key == null && keyBulletKin.isDead()){
            this.key = keyBulletKin.dropKey();
        }

        if (key != null && !key.isCollected()){
            key.update(getPlayer());
            key.draw();
        }


        if (noMoreEnemies() && !isComplete()) {
            setComplete(true);
            unlockAllDoors();
        }

        // Update and renders player + bullets
        super.PlayerAndBulletsUpdate(input);
        getPlayer().draw();
        super.DeletionAndRenderingOfAllProjectiles();
    }

    public Door findDoorByDestination(String roomName) {
        if (primaryDoor.toRoomName.equals(roomName)) {
            return primaryDoor;
        } else {
            return secondaryDoor;
        }
    }

    private void unlockAllDoors() {
        primaryDoor.unlock(false);
        secondaryDoor.unlock(false);
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void activateEnemies() {
        keyBulletKin.setActive(true);
        for (AshenBulletKin abk: ashenBulletKins){
            abk.setActive(true);
        }
        for (BulletKin bk: bulletKins){
            bk.setActive(true);
        }

    }

    public boolean noMoreEnemies() {
        return keyBulletKin.isDead();
    }
}
