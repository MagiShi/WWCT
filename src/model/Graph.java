package src.model;

import java.util.ArrayList;

/**
 * Created by Cassie on 11/9/2016.
 */
public class Graph {
    private int upperXNum;
    private int lowerXNum;
    private boolean allYears;
    private Integer currentYear;
    String xAxisLabel;
    private boolean virusDisplayed;
    private boolean contaminantDisplayed;
    private ArrayList<Float> allVirusNumList;
    private ArrayList<Float> currentVirusNumList;

    private ArrayList<Float> allContamNumList;
    private ArrayList<Float> currentContamNumList;

    private ArrayList<Integer> allMonthList;
    private ArrayList<Integer> currentMonthList;

    private ArrayList<Integer> allYearList;
    private ArrayList<Integer> currentYearList;


    public Graph(ArrayList<Report> reports) {
        allYearList = new ArrayList<>();
        allMonthList = new ArrayList<>();
        allContamNumList = new ArrayList<>();
        allVirusNumList = new ArrayList<>();
        for (int i = 0; i < reports.size(); i++) {
            Report r = reports.get(i);
            allVirusNumList.add(r.getVirusPPM());
            allContamNumList.add(r.getContaminantPPM());
            allYearList.add(r.getYear());
            allMonthList.add(r.getMonth());
        }
        currentYearList = new ArrayList<>();
        currentMonthList = new ArrayList<>();
        currentVirusNumList = new ArrayList<>();
        currentContamNumList = new ArrayList<>();

        allYears = true;
        upperXNum = 2020;
        lowerXNum = 2000;
        xAxisLabel = "Year";
        virusDisplayed = false;
        contaminantDisplayed = false;
    }

    public void setCurrentYear(int newYear) {
        if (newYear < 2000 || newYear > 2020) {
            throw new IllegalArgumentException("The year is not valid");
        }
        allYears = false;
        currentYear = newYear;
        currentMonthList.clear();
        currentYearList.clear();
        currentVirusNumList.clear();
        currentContamNumList.clear();
        xAxisLabel = "Month";
        upperXNum = 12;
        lowerXNum = 1;
        if (virusDisplayed) {
            for (int i = 0; i < allVirusNumList.size(); i++) {
                if (allYearList.get(i) == newYear) {
                    currentVirusNumList.add(allVirusNumList.get(i));
                    currentMonthList.add(allMonthList.get(i));
                }
            }
        }
        if (contaminantDisplayed) {
            for (int i = 0; i < allContamNumList.size(); i++) {
                if (allYearList.get(i) == newYear) {
                    currentContamNumList.add(allContamNumList.get(i));
                    currentMonthList.add(allMonthList.get(i));
                }
            }
        }
    }
    public void setShowAll() {
        allYears = true;
        currentYear = null;
        currentMonthList.clear();
        currentYearList.clear();
        currentVirusNumList.clear();
        currentContamNumList.clear();
        xAxisLabel = "Year";
        lowerXNum = 2000;
        upperXNum = 2020;
        if (virusDisplayed) {
            for (int i = 0; i < allVirusNumList.size(); i++) {
                currentVirusNumList.add(allVirusNumList.get(i));
                currentYearList.add(allYearList.get(i));
            }
        }
        if (contaminantDisplayed) {
            for (int i = 0; i < allContamNumList.size(); i++) {
                currentContamNumList.add(allContamNumList.get(i));
                currentYearList.add(allYearList.get(i));
            }
        }
    }
    public void setVirusDisplayed(Boolean b) {
        virusDisplayed = b;
        if (allYears) {
            setShowAll();
        } else {
            setCurrentYear(currentYear);
        }
    }
    public void setContaminantDisplayed(Boolean b) {
        contaminantDisplayed = b;
        if (allYears) {
            setShowAll();
        } else {
            setCurrentYear(currentYear);
        }
    }
    public ArrayList<Integer> getCurrentMonthList() {
        return currentMonthList;
    }
    public ArrayList<Integer> getCurrentYearList() {
        return currentYearList;
    }
    public ArrayList<Float> getCurrentVirusNumList() {
        return currentVirusNumList;
    }
    public ArrayList<Float> getCurrentContamNumList() {
        return currentContamNumList;
    }
    public boolean getAllYears() {
        return allYears;
    }
    public int getUpperXNum() {
        return upperXNum;
    }
    public int getLowerXNum() {
        return lowerXNum;
    }
    public String getxAxisLabel() {
        return xAxisLabel;
    }
    public boolean getVirusDisplayed() {
        return virusDisplayed;
    }
    public boolean getContaminantDisplayed() {
        return contaminantDisplayed;
    }
    public ArrayList<Float> getAllVirusNumList() {
        return allVirusNumList;
    }
    public ArrayList<Float> getAllContamNumList() {
        return allContamNumList;
    }
    public ArrayList<Integer> getAllMonthList() {
        return allMonthList;
    }
    public ArrayList<Integer> getAllYearList() {
        return allYearList;
    }

}
