package src.model;

/**
 * Created by Cassie on 10/12/2016.
 */
public class Report {
    private String thisDate;
    private String thisUser;
    private float virusPPM;
    private float contaminantPPM;
    private String condition;
    private int month;
    private int year;
    public Report(String user, String date, float thisPPM1, float thisPPM2, String thisCondit) {
        thisDate = date;
        thisUser = user;
        virusPPM = thisPPM1;
        contaminantPPM = thisPPM2;
        condition = thisCondit;
        String[] dateArray = thisDate.split(" ");
        String[] dateData = dateArray[1].split("/");
        month = Integer.parseInt(dateData[0]);
        year = Integer.parseInt(dateData[2]) + 2000;
    }
    public String getUser() {
        return thisUser;
    }

    public String getDate() {
        return thisDate;
    }
    public float getVirusPPM() {
        return virusPPM;
    }
    public float getContaminantPPM() {
        return contaminantPPM;
    }
    public String getPurityCondition() {
        return condition;
    }
    public int getYear() {
        return  year;
    }
    public int getMonth() {
        return month;
    }
}
