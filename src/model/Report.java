package src.model;

/**
 * The Report class represents a water purity report,
 * which contains date and time of the report, an auto-generated report number,
 * name of the worker who submitted it, location of water, overall condition of water,
 * virus PPM, and contaminant PPM.
 */
public class Report {
    private final String thisDate;
    private final String thisUser;
    private final float virusPPM;
    private final float contaminantPPM;
    private final String condition;
    private final int month;
    private final int year;

    /**
     * Constructor for an instance of Report
     * Takes the String representation of date and splits it into ints representing month and year.
     *
     * @param user the String representation of the User who submitted the Report
     * @param date the String representation of the date/time the report was submitted
     * @param thisPPM1 the float representation of virus PPM
     * @param thisPPM2 the float representation of contaminant PPM
     * @param thisCondit the String representation of purity condition
     */
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

    /**
     * Getter method for the User who submitted the report
     *
     * @return a String representation of the user who submitted the report
     */
    public String getUser() {
        return thisUser;
    }

    /**
     * Getter method for the date of the report submission
     * @return a String representation of the date and time that the report was submitted
     */
    public String getDate() {
        return thisDate;
    }

    /**
     * Getter method for the virus PPM of the report
     * @return a float representation of the virus PPM in the report
     */
    public float getVirusPPM() {
        return virusPPM;
    }

    /**
     * Getter method for the contaminant PPM of the report
     * @return a float representation of the contaminant PPM in the report
     */
    public float getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Getter method for the purity condition in the report
     * @return a String representation of the enum Purity Condition
     */
    public String getPurityCondition() {
        return condition;
    }

    /**
     * Getter method for the year the report was submitted.
     * @return an int representation of the report's year
     */
    public int getYear() {
        return  year;
    }

    /**
     * Getter method for the month the report was submitted.
     * @return an int representation of the report's month
     */
    public int getMonth() {
        return month;
    }
}
