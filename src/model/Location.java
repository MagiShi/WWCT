package src.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that represents a location on the map,
 * containing a longitude, latitude, and description of the location.
 */
public class Location implements Serializable{
    private static final Logger LOGGER = Logger.getLogger("Location");

    static {
        LOGGER.setLevel(Level.FINER);
        try {
            FileHandler logFileHandler = new FileHandler("LogFile");
            logFileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to create log file", ex);
        }
    }

    private final double longitude;
    private final double latitude;
    private final String description;
    private final String title;

    /**
     * Constructor for a Location object.
     * @param lat the double representation of the latitude of the location
     * @param lg the double representation of the longitude of the location
     * @param ti the String title of the location
     * @param desc the String description of the location
     */
    public Location(double lat, double lg, String ti, String desc) {
        LOGGER.entering("Location", "Constructor");
        longitude = lg;
        latitude = lat;
        description = desc;
        title = ti;
        LOGGER.exiting("Location", "Constructor");
    }

    /**
     * Getter method for the Location's longitude.
     * @return a double representation of the longitude
     */
    public double getLongitude() { return longitude; }

    /**
     * Getter method for the Location's latitude.
     * @return a double representation of the latitude
     */
    public double getLatitude() {return latitude; }
    /**
     * Getter method for the Location's description.
     * @return a String representation of the Location's description
     */
    public String getDescription() {return description;}
    /**
     * Getter method for the Location's title.
     * @return a String representation of the Location's title
     */
    public String getTitle() { return title; }


    /**
     * Log the Location
     * @param pw the PrintWriter object used to write to the log
     */
    public void saveToText(PrintWriter pw) {
        LOGGER.setLevel(Level.FINEST);
        LOGGER.entering("Location", "saveToText");
        pw.println(longitude + "\t" + latitude + "\t" +  description + "\t" + title);
        LOGGER.exiting("Location", "saveToText");
    }

    /**
     * Make a Location out of the string by parsing it
     * @param str the String with which we make the Location
     * @return the Location object created from the string
     * @throws FileFormatException if the String is not in
     * the right format (i.e. missing a component for the location)
     */
    public static Location makeFromFileString(String str) throws FileFormatException {
        String[] tokens = str.split("\t");

        if (tokens.length < 3) {
            throw(new FileFormatException(str));
        }

        double longit;
        double lat;
        try {
            longit = Double.parseDouble(tokens[0]);
            lat = Double.parseDouble(tokens[1]);
        } catch (NumberFormatException e) {
            throw(new FileFormatException(str));
        }

        return new Location(lat, longit, tokens[3], tokens[2]);

    }
}