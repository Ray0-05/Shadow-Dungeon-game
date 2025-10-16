import bagel.Image;
import bagel.Input;
import bagel.util.Point;

public class Store {
    private final static Image IMAGE = new Image("res/store.png");
    private final static Point position = IOUtils.parseCoords(ShadowDungeon.getGameProps().getProperty("store"));

    private boolean active = false;

    public void update(Input input){
        IMAGE.draw(position.x, position.y);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
