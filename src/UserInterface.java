import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;

import java.awt.*;
/**
 * Provides helper methods to display game information and messages
 * for the player, such as stats, start messages, end messages,
 * character descriptions, and character selection images.
 */
public class UserInterface {
    /**
     * Draws the player's stats on the screen, including health, coins, weapon level, and keys.
     *
     * @param player The player whose stats are displayed.
     */
    public static void drawStats(Player player) {
        int fontSize = Integer.parseInt(ShadowDungeon.getGameProps().getProperty("playerStats.fontSize"));
        drawData(String.format("%s %.1f", ShadowDungeon.getMessageProps().getProperty("healthDisplay"), player.getHealth()), fontSize,
                IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("healthStat")));
        drawData(String.format("%s %.0f", ShadowDungeon.getMessageProps().getProperty("coinDisplay"), player.getCoins()), fontSize,
                IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("coinStat")));
        drawData(String.format("%s %d", ShadowDungeon.getMessageProps().getProperty("weaponDisplay"), player.getWeapon().getLevel()), fontSize,
                IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("weaponStat")));
        drawData(String.format("%s %d", ShadowDungeon.getMessageProps().getProperty("keyDisplay"), player.getKeyNum()), fontSize,
                IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("keyStat")));
    }

    /**
     * Draws the start screen messages, such as title and movement instructions.
     */
    public static void drawStartMessages() {
        drawTextCentered("title", Integer.parseInt(ShadowDungeon.getGameProps().getProperty("title.fontSize")), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("title.y")));
        drawTextCentered("moveMessage", Integer.parseInt(ShadowDungeon.getGameProps().getProperty("prompt.fontSize")), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("moveMessage.y")));
        drawTextCentered("selectMessage", Integer.parseInt(ShadowDungeon.getGameProps().getProperty("prompt.fontSize")), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("selectMessage.y")));
    }

    /**
     * Draws the end game message depending on whether the player won or lost.
     *
     * @param win True if the player won, false if the player lost.
     */
    public static void drawEndMessage(boolean win) {
        drawTextCentered(win ? "gameEnd.won" : "gameEnd.lost", Integer.parseInt(ShadowDungeon.getGameProps().getProperty("title.fontSize")), Double.parseDouble(ShadowDungeon.getGameProps().getProperty("title.y")));
    }

    /**
     * Draws character description messages for the player to read.
     */
    public static void drawCharacterDescMessage() {
        Font font = new Font("res/wheaton.otf", Integer.parseInt(ShadowDungeon.getGameProps().getProperty("playerStats.fontSize")));

        String robotDesc = ShadowDungeon.getMessageProps().getProperty("robotDescription");
        Point robotDescPoint = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("robotMessage"));

        String marineDesc = ShadowDungeon.getMessageProps().getProperty("marineDescription");
        Point marineDescPoint = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("marineMessage"));

        font.drawString(robotDesc, robotDescPoint.x, robotDescPoint.y);
        font.drawString(marineDesc, marineDescPoint.x, marineDescPoint.y);
    }

    /**
     * Draws the character selection images on the screen.
     */
    public static void drawCharacterSelectionImage(){
        Point robotCoord = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("Robot"));
        Image robotImg = new Image("res/robot_sprite.png");
        Point marineCoord = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("Marine"));
        Image marineImg = new Image("res/marine_sprite.png");

        robotImg.draw(robotCoord.x, robotCoord.y);
        marineImg.draw(marineCoord.x, marineCoord.y);
    }

    /**
     * Draws a text string centered horizontally at the given vertical position.
     *
     * @param textPath The key for the text to display from message properties.
     * @param fontSize The font size of the text.
     * @param posY     The vertical position to draw the text.
     */
    private static void drawTextCentered(String textPath, int fontSize, double posY) {
        Font font = new Font("res/wheaton.otf", fontSize);
        String text = ShadowDungeon.getMessageProps().getProperty(textPath);
        double posX = (Window.getWidth() - font.getWidth(text)) / 2;
        font.drawString(text, posX, posY);
    }

    /**
     * Draws a string at a specified location on the screen.
     *
     * @param data     The text to draw.
     * @param fontSize The font size of the text.
     * @param location The position on screen where the text should be drawn.
     */
    private static void drawData(String data, int fontSize, Point location) {
        Font font = new Font("res/wheaton.otf", fontSize);
        font.drawString(data, location.x, location.y);
    }
}
