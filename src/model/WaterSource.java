package src.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Cassie on 10/12/2016.
 */
public class WaterSource {
    private String firstUser;
    private String creationDate;
    private String location;
    private String type;
    private String sourceCondition;
    private ArrayList<Report> reports = new ArrayList<Report>();

    private double longitude;
    private double latitude;
    private String description;
    private String title;

    public WaterSource(String user, String date, String loc, String newType, String condit) {
        firstUser = user;
        creationDate = date;
        location = loc;
        type = newType;
        sourceCondition = condit;
    }
    public void addReport(Report newReport) {
        reports.add(newReport);
    }
    public ArrayList<Report> getReports() {
        return reports;
    }
    public String getUser() {
        return firstUser;
    }
    public String getDate() {
        return creationDate;
    }
    public String getLocation() {
        return location;
    }
    public String getType() {
        return type;
    }
    public String getSourceCondition() {
        return sourceCondition;
    }

    //from Location
    private static Logger LOGGER = Logger.getLogger("Location");
    private static FileHandler logFileHandler;
    static {
        LOGGER.setLevel(Level.FINER);
        try {
            logFileHandler = new FileHandler("LogFile");
            logFileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(logFileHandler);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to create log file", ex);
        }
    }


    public WaterSource(double lat, double lg, String ti, String desc) {
        LOGGER.entering("Location", "Constructor");
        longitude = lg;
        latitude = lat;
        description = desc;
        title = ti;
        LOGGER.exiting("Location", "Constructor");
    }

    public double getLongitude() { return longitude; }
    public double getLatitude() {return latitude; }
    public String getDescription() {return description;}
    public String getTitle() { return title; }


    public void saveToText(PrintWriter pw) {
        LOGGER.setLevel(Level.FINEST);
        LOGGER.entering("Location", "saveToText");
        pw.println(longitude + "\t" + latitude + "\t" +  description + "\t" + title);
        LOGGER.exiting("Location", "saveToText");
    }

    public static WaterSource makeFromFileString(String str) throws FileFormatException {
        String[] tokens = str.split("\t");


        if (tokens.length < 3) {
            throw(new src.model.FileFormatException(str));
        }

        double longit;
        double lat;
        try {
            longit = Double.parseDouble(tokens[0]);
            lat = Double.parseDouble(tokens[1]);
        } catch (NumberFormatException e) {
            throw(new FileFormatException(str));
        }

        return new WaterSource(lat, longit, tokens[3], tokens[2]);

    }
}
