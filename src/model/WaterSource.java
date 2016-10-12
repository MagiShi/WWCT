package src.model;

import java.util.ArrayList;

/**
 * Created by Cassie on 10/12/2016.
 */
public class WaterSource {
    private ArrayList<Report> reportList;
    private ArrayList<PurityReport> purityReportList;
    private ArrayList<RegularReport> regularReportList;
    private PurityCondition purityCondit;
    private WaterCondition waterCondit;
    private float ppm;
    private String lastDate;

    public WaterSource(Report firstReport) {
        addReport(firstReport);
    }

    public void addReport(Report newReport) {
        reportList.add(newReport);
        if (newReport instanceof PurityReport) {
            purityReportList.add((PurityReport)newReport);
        } else {
            regularReportList.add((RegularReport) newReport);
        }
        changeSource(newReport);
    }

    private void changeSource(Report newReport) {
        float tempNum = 0;
        for (int i = 0; i < purityReportList.size(); i++) {
            tempNum = tempNum + purityReportList.get(i).getPPM();
        }
        ppm = tempNum/purityReportList.size();
        lastDate = newReport.getDate();
    }
    public ArrayList<Report> getReports() {
        return reportList;
    }
}
