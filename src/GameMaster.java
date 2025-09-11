import bagel.util.Point;

import java.util.Properties;

public class GameMaster {
    /* Strings denoting the specific room a door access to */
    private static final String PREPROOM_STR = "prep";
    private static final String BATTLE_ROOM_A = "A";
    private static final String BATTLE_ROOM_B = "B";
    private static final String END_ROOM = "end";

    private final Properties gameProps;
    private final Properties msgProps;
    private Room currRoom;
    private Room prepRoom;
    private Room batteRoomA;
    private Player player;

    public GameMaster(Properties gameProps, Properties msgProps, Player player){
        // Assign the player and initialise all the rooms and signify the currRoom as PrepRoom
        this.player = player;

        this.prepRoom = new PrepRoom(gameProps, msgProps, PREPROOM_STR);
        this.batteRoomA = new BattleRoomA(gameProps, BATTLE_ROOM_A);

        this.currRoom = this.prepRoom;
        this.gameProps = gameProps;
        this.msgProps = msgProps;
    }

    public void checkIfChangeRoom(){
        String destinationRoomStr = player.isEnteringNewRoom(currRoom.doors);
        Room enteringRoom = null;

        // Check if we need to change room
        if (destinationRoomStr != null){
            if (destinationRoomStr.equals(PREPROOM_STR)){
                enteringRoom = prepRoom;
            }else if (destinationRoomStr.equals(BATTLE_ROOM_A)){
                enteringRoom = batteRoomA;
            }else if (destinationRoomStr.equals(BATTLE_ROOM_B)){

            }else if (destinationRoomStr.equals(END_ROOM)){

            }else{
                System.out.println("When changing Room, the destinationRoomStr (defined in app.properties under doors) " +
                        "does not match the symbols of each room defined in the program");
                System.exit(1);
            }
            String prevRoomName = currRoom.getNAME_LABEL();
            Point newRoomLinkingDoor = enteringRoom.getDoorCoord(prevRoomName);
            currRoom = enteringRoom;
            player.teleportTo(newRoomLinkingDoor);
        }
    }

    public boolean canTypeRToUnlockDoor(){
        if (currRoom.equals(prepRoom)){
            return true;
        }
        return false;
    }

    public void unlockPrepRoomDoor(){
        this.prepRoom.unlockAllDoors();
    }

    public void render(){
        currRoom.render();
        player.render();
    }
}
