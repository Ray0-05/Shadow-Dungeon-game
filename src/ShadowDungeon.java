import bagel.*;
import bagel.util.Point;

import java.util.Properties;

/**
 * Main game class that manages initialising the rooms and moving the player between rooms
 */
public class ShadowDungeon extends AbstractGame {
    private static Properties gameProps;
    private static Properties messageProps;
    private static double screenWidth;
    private static double screenHeight;

    private static Room currRoom;
    private static PrepRoom prepRoom;
    private static BattleRoom battleRoomA;
    private static BattleRoom battleRoomB;
    private static EndRoom endRoom;
    private static Player player;
    private static Store store;
    private final Image BACKGROUND;
    
    public static final String PREP_ROOM_NAME = "prep";
    public static final String BATTLE_ROOM_A_NAME = "A";
    public static final String BATTLE_ROOM_B_NAME = "B";
    public static final String END_ROOM_NAME = "end";

    public ShadowDungeon(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                "Shadow Dungeon");

        ShadowDungeon.gameProps = gameProps;
        ShadowDungeon.messageProps = messageProps;
        screenWidth = Integer.parseInt(gameProps.getProperty("window.width"));
        screenHeight = Integer.parseInt(gameProps.getProperty("window.height"));
        this.BACKGROUND = new Image("res/background.png");

        resetGameState(gameProps);
    }

    public static void resetGameState(Properties gameProps) {
        prepRoom = new PrepRoom(PREP_ROOM_NAME);
        battleRoomA = new BattleRoom(BATTLE_ROOM_A_NAME, BATTLE_ROOM_B_NAME);
        battleRoomB = new BattleRoom(BATTLE_ROOM_B_NAME, END_ROOM_NAME);
        endRoom = new EndRoom(END_ROOM_NAME);
        store = new Store();

        prepRoom.initEntities(gameProps);
        battleRoomA.initEntities(gameProps);
        battleRoomB.initEntities(gameProps);
        endRoom.initEntities(gameProps);

        currRoom = prepRoom;

        ShadowDungeon.player = new Player(IOUtils.parseCoords(gameProps.getProperty("player.start")));
        prepRoom.setPlayer(player);
    }

    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        //always the background
        BACKGROUND.draw((double) Window.getWidth() / 2, (double) Window.getHeight() / 2);


        if (input.wasPressed(Keys.SPACE)){
            store.setActive(!store.isActive());
        }

        if (store.isActive()){
            currRoom.renderOnly();
            store.update(input, player);
        }
        else{
            currRoom.update(input);
        }

        // always visible and updated
        UserInterface.drawStats(this.player);

    }


    public static void changeRoom(String destRoomName) {
        Door nextDoor;
        currRoom.stopCurrentUpdateCall();
        switch (destRoomName) {
            case PREP_ROOM_NAME:
                nextDoor = prepRoom.findDoor();
                currRoom = prepRoom;

                break;
            case BATTLE_ROOM_A_NAME:
                nextDoor = battleRoomA.findDoorByDestination(currRoom.getRoomName());
                currRoom = battleRoomA;
                // prepare the door to be able to activate the Battle Room
                if (!((BattleRoom) currRoom).isComplete()) {
                    nextDoor.setShouldLockAgain();
                }
                break;
            case BATTLE_ROOM_B_NAME:
                nextDoor = battleRoomB.findDoorByDestination(currRoom.getRoomName());
                currRoom = battleRoomB;

                // prepare the door to be able to activate the Battle Room
                if (!((BattleRoom) currRoom).isComplete()) {
                    nextDoor.setShouldLockAgain();
                }

                break;
            default:
                nextDoor = endRoom.findDoor();
                currRoom = endRoom;
        }

        // move the player to the center of the next room's door
        nextDoor.unlock(true);
        player.move(nextDoor.getPosition().x, nextDoor.getPosition().y);
        currRoom.setPlayer(player);

    }

    public static void changeToGameOverRoom() {
        currRoom.stopCurrentUpdateCall();

        endRoom.isGameOver();
        currRoom = endRoom;

        Point startPos = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("player.start"));
        player.move(startPos.x, startPos.y);
        currRoom.setPlayer(player);
    }


    public static Properties getGameProps() {
        return gameProps;
    }
    public static Properties getMessageProps() {
        return messageProps;
    }

    public static void main(String[] args) {
        Properties gameProps = IOUtils.readPropertiesFile("res/app.properties");
        Properties messageProps = IOUtils.readPropertiesFile("res/message.properties");
        ShadowDungeon game = new ShadowDungeon(gameProps, messageProps);
        game.run();
    }
}
