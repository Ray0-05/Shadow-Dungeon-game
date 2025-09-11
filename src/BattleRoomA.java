import bagel.Font;
import bagel.Image;
import bagel.util.Point;

import java.util.Properties;

public class BattleRoomA extends Room{

    public BattleRoomA(Properties gameProps, String nameLabel){
        // Initialise the basic Display and attributes of a room
        super(gameProps, nameLabel);

        // Initialise PrepRoom specific displays and attributes
        super.doors = new Door[] {new Door(gameProps.getProperty("primarydoor.A")),
                                  new Door(gameProps.getProperty("secondarydoor.A"))};

    }


    /* A method to render all the displays and attributes of a general room,
    and also PrepRoom specific's displays (called in the update method) */
    @Override
    public void render(){
        super.render();

    }
}
