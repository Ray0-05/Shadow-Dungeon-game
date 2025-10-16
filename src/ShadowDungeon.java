import bagel.*;
import bagel.util.Point;

import java.util.Properties;

/**
 * Main game class that manages initializing the rooms and moving the player between rooms.
 * Handles the game loop, room transitions, and maintains the overall game state including
 * all rooms, the player, and the store.
 */
public class ShadowDungeon extends AbstractGame {
    private static Properties gameProps;
    private static Properties messageProps;

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

    /**
     * Creates a new Shadow Dungeon game instance and initializes all game components.
     * Sets up the window dimensions, loads the background image, and creates all rooms
     * and game entities from the provided properties files.
     *
     * @param gameProps contains game configuration like window size, entity positions, and stats
     * @param messageProps contains all text messages displayed during gameplay
     */
    public ShadowDungeon(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                "Shadow Dungeon");

        ShadowDungeon.gameProps = gameProps;
        ShadowDungeon.messageProps = messageProps;
        this.BACKGROUND = new Image("res/background.png");

        resetGameState(gameProps);
    }

    /**
     * Resets the entire game state to its initial configuration.
     * Recreates all rooms, reinitializes entities, resets the player position,
     * and sets the current room back to the prep room. Used for starting a new game.
     *
     * @param gameProps contains the configuration data needed to initialize all game entities
     */
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
     * Main game loop that renders the screen and handles all user input.
     * Draws the background, manages the store toggle, updates the current room,
     * and displays the player stats. Handles ESC key to close the game and
     * SPACE key to open/close the store.
     *
     * @param input captures the current keyboard and mouse state for this frame
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
            currRoom.updateAndRender(input);
        }

        // always visible and updated
        UserInterface.drawStats(this.player);

    }

    /**
     * Transitions the player from the current room to the specified destination room.
     * Handles door locking/unlocking logic, positions the player at the destination door,
     * and sets up battle room states if necessary. For battle rooms, doors will lock
     * again if the room hasn't been completed yet.
     *
     * @param destRoomName the name identifier of the room to transition to (prep, A, B, or end)
     */
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

    /**
     * Transitions the game to the end room in game over state.
     * Stops the current room's updateAndRender cycle, marks the end room as a game over scenario,
     * and repositions the player to their starting position.
     */
    public static void changeToGameOverRoom() {
        currRoom.stopCurrentUpdateCall();

        endRoom.isGameOver();
        currRoom = endRoom;

        Point startPos = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("player.start"));
        player.move(startPos.x, startPos.y);
        currRoom.setPlayer(player);
    }

    /**
     * Gets the game configuration properties.
     *
     * @return properties object containing game settings like dimensions, entity stats, and positions
     */
    public static Properties getGameProps() {
        return gameProps;
    }

    /**
     * Gets the message properties for UI text.
     *
     * @return properties object containing all display messages used throughout the game
     */
    public static Properties getMessageProps() {
        return messageProps;
    }

    /**
     * Entry point for the Shadow Dungeon game.
     * Loads configuration files, creates the game instance, and starts the game loop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Properties gameProps = IOUtils.readPropertiesFile("res/app.properties");
        Properties messageProps = IOUtils.readPropertiesFile("res/message.properties");
        ShadowDungeon game = new ShadowDungeon(gameProps, messageProps);
        game.run();
    }
}
