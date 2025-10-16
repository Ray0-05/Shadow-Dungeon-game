import bagel.Image;

/**
 * Represents the different playable character types in the game.
 * Each character has a right-facing and left-facing sprite image.
 */
public enum Character {
    ORIGINAL(new Image("res/player_right.png"), new Image("res/player_left.png")),
    ROBOT(new Image("res/robot_right.png"),new Image("res/robot_left.png")),
    MARINE(new Image("res/marine_right.png"), new Image("res/marine_left.png"));

    private final Image rightImage;
    private final Image leftImage;

    /**
     * Creates a character type with specified images for facing right and left.
     *
     * @param rightImage The sprite image when the character is facing right.
     * @param leftImage  The sprite image when the character is facing left.
     */
    Character(Image rightImage, Image leftImage){
        this.rightImage = rightImage;
        this.leftImage = leftImage;
    }

    /**
     * Gets the image of the character facing right.
     *
     * @return The right-facing sprite image.
     */
    public Image getRightImage() {
        return rightImage;
    }

    /**
     * Gets the image of the character facing left.
     *
     * @return The left-facing sprite image.
     */
    public Image getLeftImage() {
        return leftImage;
    }
}
