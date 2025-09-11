import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Properties;

public interface Restartable {
    public final Image RESTART_AREA = new Image("res/restart_area.png");

    public default Rectangle getRESTART_AREA_BOX(Point coordinate){
        return RESTART_AREA.getBoundingBoxAt(coordinate);
    }

    public default Point getRestartAreaCoordinate(Properties gameProps, String key){
        return IOUtils.parseCoords(gameProps.getProperty("restartarea.prep"));
    }
}
