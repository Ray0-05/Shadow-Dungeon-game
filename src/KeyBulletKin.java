import bagel.util.Point;

public class KeyBulletKin extends Enemy {
    public KeyBulletKin(Point coordinate) {
        super(coordinate, "res/key_bullet_kin.png");
    }
    protected KeyBulletKin[] buildKeyBulletKinList(Point[] coordinates){
        KeyBulletKin[] KeyBulletKins = new KeyBulletKin[coordinates.length];
        int i = 0;
        for (Point coordinate : coordinates){
            KeyBulletKins[i++] = new KeyBulletKin(coordinate);
        }

        return KeyBulletKins;
    }
}
