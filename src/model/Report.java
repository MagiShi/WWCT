package src.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cassie on 10/12/2016.
 */
public class Report {
    private String thisDate;
    private User thisUser;
    private float ppm;
    private String condition;

    public Report(User user, String date, float thisPPM, String thisCondit ) {
        thisDate = date;
        thisUser = user;
        ppm = thisPPM;
        condition = thisCondit;
    }

    public String getUser() {
        return thisUser.getName();
    }

    public String getDate() {
        return thisDate;
    }
    public float getPPM() {
        return ppm;
    }
    public String getPurityCondition() {
        return condition;
    }
}
