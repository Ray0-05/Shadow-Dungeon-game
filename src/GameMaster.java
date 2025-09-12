import bagel.util.Point;

import java.awt.*;
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
    private BattleRoom battleRoomA;
    private BattleRoom battleRoomB;

    private Player player;

    public GameMaster(Properties gameProps, Properties msgProps){
        // Assign the player and initialise all the rooms and signify the currRoom as PrepRoom
        this.player = new Player(gameProps, msgProps);

        this.prepRoom = new PrepRoom(gameProps, msgProps, PREPROOM_STR);
        this.battleRoomA = new BattleRoom(gameProps, BATTLE_ROOM_A);
        this.battleRoomB = new BattleRoom(gameProps, BATTLE_ROOM_B);

        this.currRoom = this.prepRoom;
        this.gameProps = gameProps;
        this.msgProps = msgProps;
    }
    /* -------------Methods below is for moving players around-------------*/
    public void movePlayerRight(){
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

    public void checkIfChangeRoom() {
        String destinationRoomStr = player.isEnteringNewRoom(currRoom.getDoors());
        if (destinationRoomStr == null) return;

        Room enteringRoom = switch (destinationRoomStr) {
            case PREPROOM_STR -> prepRoom;
            case BATTLE_ROOM_A -> battleRoomA;
            case BATTLE_ROOM_B -> battleRoomB;
            case END_ROOM      -> new EndRoom(gameProps, msgProps, END_ROOM, true); // win state
            default -> {
                System.out.println("Door destination doesn't match any known room symbol.");
                System.exit(1);
                yield null; // unreachable
            }
        };

        String prevRoomName = currRoom.getNAME_LABEL();

        // Find the linking door IN THE NEW ROOM
        Door entry = enteringRoom.findDoorTo(prevRoomName);
        if (entry == null) {
            System.out.println("No linking door back to " + prevRoomName + " in new room!");
            return;
        }

        // Switch to the new room and configure its entry door
        currRoom = enteringRoom;
        currRoom.setEntryDoor(entry);
        entry.markAsEntryDoor(currRoom instanceof BattleRoom && ((BattleRoom) currRoom).hasEnemy());

        // Spawn the player inside the door (centre so we truly overlap)
        player.teleportTo(entry.getBoundingBox().centre());

        // Battle rooms: enemies hidden/inactive until player step off once
        if (currRoom instanceof BattleRoom br) {
            br.setEncounterActive(false);
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

    public void updatePlayerFacingByMouseX(double mouseX){ this.player.updateFacingByMouseX(mouseX); };


    public void render(){
        if (currRoom instanceof BattleRoom) {
            //only render this if its relevant
            currRoom.updateEncounterActivation(player);
            currRoom.resolveEnemyTouches(player); // Touch-to-kill + unlock-all-doors-when-clear
            updateHazards();
        }
        currRoom.render();
        player.render();
    }

}
