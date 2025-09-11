import bagel.*;

import java.util.Properties;

public class ShadowDungeon extends AbstractGame {
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;

    private final GameMaster GAME_MASTER;
    private final Player PLAYER;


    public ShadowDungeon(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("title"));

        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;

        PLAYER = new Player(GAME_PROPS, MESSAGE_PROPS);
        GAME_MASTER = new GameMaster(GAME_PROPS, MESSAGE_PROPS, PLAYER);
    }


    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        GAME_MASTER.render();
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (input.isDown(Keys.D)) {
            PLAYER.moveRight();
        } else if (input.isDown(Keys.A)) {
            PLAYER.moveLeft();
        } else if (input.isDown(Keys.W)) {
            PLAYER.moveUp();
        } else if (input.isDown(Keys.S)) {
            PLAYER.moveDown();
        }

        if (input.wasPressed(Keys.R) && GAME_MASTER.canTypeRToUnlockDoor()){
            GAME_MASTER.unlockPrepRoomDoor();
        }

        GAME_MASTER.checkIfChangeRoom();
    }


    /**
     * The main entry point of the Shadow Dungeon game.
     *
     * This method loads the game properties and message files, initializes the game,
     * and starts the game loop.
     *
     * @param args Command-line arguments (not used in this game).
     */
    public static void main(String[] args) {
        Properties gameProps = IOUtils.readPropertiesFile("res/app.properties");
        Properties messageProps = IOUtils.readPropertiesFile("res/message.properties");
        ShadowDungeon game = new ShadowDungeon(gameProps, messageProps);
        game.run();
    }
}
