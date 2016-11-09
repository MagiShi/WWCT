package src.model;

public class Report {
    private final String thisDate;
    private final String thisUser;
    private final float virusPPM;
    private final float contaminantPPM;
    private final String condition;
    private final int month;
    private final int year;
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
