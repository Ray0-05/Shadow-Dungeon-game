// These are the existing types of characters in the game

import bagel.Image;

public enum Character {
    ORIGINAL(new Image("res/player_right.png"), new Image("res/player_left.png")),
    ROBOT(new Image("res/robot_right.png"),new Image("res/robot_left.png")),
    MARINE(new Image("res/marine_right.png"), new Image("res/marine_left.png"));

    private final Image rightImage;
    private final Image leftImage;

    Character(Image rightImage, Image leftImage){
        this.rightImage = rightImage;
        this.leftImage = leftImage;
    }

    public Image getRightImage() {
        return rightImage;
    }

    public Image getLeftImage() {
        return leftImage;
    }
}
