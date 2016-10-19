package src.model;

import java.util.ArrayList;

/**
 * Created by Cassie on 10/12/2016.
 */
public class WaterSource {
    private String firstUser;
    private String creationDate;
    private String location;
    private double lat;
    private double longit;
    private String type;
    private String sourceCondition;
    private ArrayList<Report> reports = new ArrayList<Report>();
    public WaterSource(String user, String date, String loc, double latIn, double longitIn, String newType, String condit) {
        firstUser = user;
        creationDate = date;
        location = loc;
        lat = latIn;
        longit = longitIn;
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
    public Double getLatitude() {
        return lat;
    }
    public Double getLongitude() {
        return longit;
    }
    public String getType() {
        return type;
    }
    public String getSourceCondition() {
        return sourceCondition;
    }
}
