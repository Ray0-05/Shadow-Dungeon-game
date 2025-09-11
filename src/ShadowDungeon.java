import bagel.*;
import bagel.util.Point;

import java.util.Properties;

public class ShadowDungeon extends AbstractGame {
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;


    private GameMaster gameMaster;


    public ShadowDungeon(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("title"));

        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;

        gameMaster = new GameMaster(GAME_PROPS, MESSAGE_PROPS);
    }


    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        // 1) Quick exit
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // 2) Movement input → move player
        if (input.isDown(Keys.D)) {
            gameMaster.movePlayerRight();
        } else if (input.isDown(Keys.A)) {
            gameMaster.movePlayerLeft();
        } else if (input.isDown(Keys.W)) {
            gameMaster.movePlayerUp();
        } else if (input.isDown(Keys.S)) {
            gameMaster.movePlayerDown();
        }

        // 3) One-shot actions
        // Check if can manually open door
        if (input.wasPressed(Keys.R) && gameMaster.canTypeRToUnlockDoor()) {
            gameMaster.unlockPrepRoomDoor();
        }
        // Check if can manually restart game
        if (input.wasPressed(Keys.ENTER) && gameMaster.canRestart()) {
            gameMaster = new GameMaster(GAME_PROPS, MESSAGE_PROPS);
        }
        // Check if can collect coins
        if (input.wasPressed(Keys.K)){
            gameMaster.updateCollectibles();
        }

        // 4) Resolve room transitions after movement
        gameMaster.checkIfChangeRoom();

        // 5) render the current room status + player
        gameMaster.render();
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
