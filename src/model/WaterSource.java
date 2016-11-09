package src.model;

import java.util.ArrayList;

public class WaterSource {
    private final String firstUser;
    private final String creationDate;
    private final String location;
    private final double lat;
    private final double longit;
    private final String type;
    private final String sourceCondition;
    private final ArrayList<Report> reports = new ArrayList<>();
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
