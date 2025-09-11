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
    private PrepRoom prepRoom;
    private BattleRoomA battleRoomA;
    private BattleRoomB battleRoomB;
    private EndRoom endRoom;

    private Player player;

    public GameMaster(Properties gameProps, Properties msgProps){
        // Assign the player and initialise all the rooms and signify the currRoom as PrepRoom
        this.player = new Player(gameProps, msgProps);

        this.prepRoom = new PrepRoom(gameProps, msgProps, PREPROOM_STR);
        this.battleRoomA = new BattleRoomA(gameProps, BATTLE_ROOM_A);
        this.battleRoomB = new BattleRoomB(gameProps, BATTLE_ROOM_B);

        this.currRoom = this.prepRoom;
        this.gameProps = gameProps;
        this.msgProps = msgProps;
    }
    /* -------------Methods below is for moving players around-------------*/
    public void movePlayerRight(){
//        if(player.isOverlappingWith())
        player.moveRight(currRoom);
    }
    public void movePlayerLeft(){
        player.moveLeft(currRoom);
    }
    public void movePlayerUp(){
        player.moveUp(currRoom);
    }
    public void movePlayerDown(){
        player.moveDown(currRoom);
    }

    public void checkIfChangeRoom(){
        String destinationRoomStr = player.isEnteringNewRoom(currRoom.getDoors());
        Room enteringRoom = null;

        // Check if we need to change room
        if (destinationRoomStr != null){

            if (destinationRoomStr.equals(PREPROOM_STR)){
                enteringRoom = prepRoom;
            }else if (destinationRoomStr.equals(BATTLE_ROOM_A)){
                enteringRoom = battleRoomA;
            }else if (destinationRoomStr.equals(BATTLE_ROOM_B)){
                enteringRoom = battleRoomB;
            }else if (destinationRoomStr.equals(END_ROOM)){
                enteringRoom = new EndRoom(gameProps, msgProps, END_ROOM,true);
            }else{
                System.out.println("When changing Room, the destinationRoomStr (defined in app.properties under doors) " +
                        "does not match the symbols of each room defined in the program");
                System.exit(1);
            }
            // Find the linking door in new room
            String prevRoomName = currRoom.getNAME_LABEL();
            Door newRoomLinkingDoor = enteringRoom.findDoorTo(prevRoomName);

            // Teleport to new room
            currRoom = enteringRoom;
            player.teleportTo(newRoomLinkingDoor.getCoordinate());

            // Identify and configure the entry door (the one that links back to prevRoomName)
            Door entryDoor = currRoom.findDoorTo(prevRoomName); // uses the helper we'll add in Room (Part 1 Edit 3)
            if (entryDoor != null) {
                entryDoor.markAsEntryDoor(currRoom.hasEnemy());  // open + ignore overlap; closes after stepping away if boss room
            }

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

    public boolean canRestart() {
        // Only two rooms that has the restart area
        if (currRoom instanceof Restartable) {
            if (player.isOverlappingWith(this.currRoom.retrieveRestartAreaBox())) {
                return true;
            }
        }
        return false;
    }

    public void updateHazards() {
        currRoom.resolveHazards(player);
        if (!player.getIsAlive()) {
            // go to defeat end room immediately when health reaches 0
            currRoom = new EndRoom(gameProps, msgProps, END_ROOM, false);
            player.teleportTo(player.getINITIAL_POSITION());
        }
    }

    public void updateCollectibles() {
        // Would check if the player is intersecting with the Treaure Box
        currRoom.resolveCollectibles(player);
    }



    public void render(){
        if (currRoom instanceof BattleRoom) {
            //only render this if its relevant
            currRoom.resolveEnemyTouches(player); // Touch-to-kill + unlock-all-doors-when-clear
            updateHazards();
        }
        currRoom.render();
        player.render();
    }

}
