import bagel.util.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * A utility class that provides methods to read and write files.
 */
public class IOUtils {
    /***
     * Read a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return: Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }

    public static Point parseCoords(String coords) {
        String[] coordinates = coords.split(",");
        return new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
    public static Point parseCoordinateFromDoorAttribute(String attribute) {
        String[] parts = attribute.split(",");
        return parseCoords(parts[0] + "," + parts[1]);
    }
    public static Point[] parsePointList(String raw) {
        if (raw == null || raw.trim().isEmpty() || raw.equals("0")) return new Point[0];
        // Expecting: "x1,y1; x2,y2; x3,y3"
        String[] parts = raw.split(";");
        Point[] points = new Point[parts.length];
        for (int i = 0; i < parts.length; i++) {
            points[i] = parseCoords(parts[i].trim());
        }
        return points;
    }


}
