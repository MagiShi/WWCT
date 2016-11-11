package src.model;

import java.util.ArrayList;

/**
 * An object representing a water source for the reports
 */
public class WaterSource {
    private final String firstUser;
    private final String creationDate;
    private final String location;
    private final double lat;
    private final double longit;
    private final String type;
    private final String sourceCondition;
    private final ArrayList<Report> reports = new ArrayList<>();

    /**
     * A constructor to create a water source
     * @param user  The user who create the water source
     * @param date  The date that the water source is created
     * @param loc   The location name of the water source
     * @param latIn The latitude of the water source
     * @param longitIn  The longitude of the water source
     * @param newType   The type of water source
     * @param condit    The condition the water source is in
     */
    public WaterSource(String user, String date, String loc, double latIn,
                       double longitIn, String newType, String condit) {
        firstUser = user;
        creationDate = date;
        location = loc;
        lat = latIn;
        longit = longitIn;
        type = newType;
        sourceCondition = condit;
    }

    /**
     * adds a new report to the arrayList of reports that the water source contains
     * @param newReport     The report to be added
     */
    public void addReport(Report newReport) {
        reports.add(newReport);
    }

    /**
     * gives the reports for the water source
     * @return reports  The array list of reports for the specific source
     */
    public ArrayList<Report> getReports() {
        return reports;
    }

    /**
     * gets the creator of the water source
     * @return firstUser    The user object who created the water source
     */
    public String getUser() {
        return firstUser;
    }

    /**
     * gets the creation date of the water source
     * @return creationDate    A String date the water source was created
     */
    public String getDate() {
        return creationDate;
    }

    /**
     * gets the location of the water source
     * @return location    The String location of the water source
     */
    public String getLocation() {
        return location;
    }

    /**
     * gets the latitude of the water source
     * @return lat    A double of the latitude
     */
    public Double getLatitude() {
        return lat;
    }

    /**
     * gets the longitude of the water source
     * @return longit    A double of the longitude
     */
    public Double getLongitude() {
        return longit;
    }

    /**
     * gets the type of the water source
     * @return type    The String of the source type
     */
    public String getType() {
        return type;
    }

    /**
     * gets the condition of the water source
     * @return sourceCondition    The String of the source condition
     */
    public String getSourceCondition() {
        return sourceCondition;
    }
}
