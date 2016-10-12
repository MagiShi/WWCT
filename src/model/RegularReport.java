package src.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cassie on 10/12/2016.
 */
public class RegularReport extends Report {
    private WaterCondition condition;
    private SourceType type;

    public RegularReport(SourceType newType, WaterCondition newCondition, User newUser) {
        super(newUser);
        condition = newCondition;
        type = newType;
    }

    public SourceType getType() {
        return type;
    }

    public WaterCondition getCondition() {
        return condition;
    }
}
