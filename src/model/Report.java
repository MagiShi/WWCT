package src.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cassie on 10/12/2016.
 */
public class Report {
    //Location
    DateFormat df;
    Date dateobj;
    User thisUser;

    public Report(User newUser) {
        df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateobj = new Date();
        thisUser = newUser;
    }

    public String getUser() {
        return thisUser.getName();
    }

    public String getDate() {
        return df.format(dateobj);
    }
}
