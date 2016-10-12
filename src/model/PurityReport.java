package src.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cassie on 10/12/2016.
 */
public class PurityReport extends Report {
    private float ppm;
    private PurityCondition condition;

    public PurityReport(float newPPM, PurityCondition newCondition, User newUser) {
        super(newUser);
        ppm = newPPM;
        condition = newCondition;
    }

    public float getPPM() {
        return ppm;
    }

    public PurityCondition getCondition() {
        return condition;
    }
}
