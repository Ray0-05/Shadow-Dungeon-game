import bagel.Font;
import bagel.Image;
import bagel.util.Point;

import java.util.Properties;

public class BattleRoomA extends Room{

    public BattleRoomA(Properties gameProps, String nameLabel){
        // Initialise the basic Display and attributes of a room
        super(gameProps, nameLabel);

        // Read optional walls list from properties (semicolon-separated x,y pairs)
        String wallsCoordRaw = gameProps.getProperty("wall.A");
        Point[] wallCoords = IOUtils.parsePointList(wallsCoordRaw);

        GameObject[] objs = new GameObject[wallCoords.length];
        // Add each wall into the Room object list
        for (int i = 0; i < wallCoords.length; i++) {
            objs[i] = new Wall(wallCoords[i]);
        }

        // Initialise PrepRoom specific displays and attributes
        setDoors(new Door[] {new Door(gameProps.getProperty("primarydoor.A")),
                new Door(gameProps.getProperty("secondarydoor.A"))});
        setHasBoss(true);
        setObjects(objs);
    }


    /* A method to render all the displays and attributes of a general room,
    and also PrepRoom specific's displays (called in the update method) */
    @Override
    public void render(){
        super.render();

    }
}
