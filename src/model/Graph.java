package src.model;

import java.util.ArrayList;

/**
 * A class representing the PPM Graph displayed for Managers
 */
public class Graph {
    private int upperXNum;
    private int lowerXNum;
    private boolean allYears;
    private Integer currentYear;
    private String xAxisLabel;
    private boolean virusDisplayed;
    private boolean contaminantDisplayed;
    private final ArrayList<Float> allVirusNumList;
    private final ArrayList<Float> currentVirusNumList;

    private final ArrayList<Float> allContamNumList;
    private final ArrayList<Float> currentContamNumList;

    private final ArrayList<Integer> allMonthList;
    private final ArrayList<Integer> currentMonthList;

    private final ArrayList<Integer> allYearList;
    private final ArrayList<Integer> currentYearList;

    /**
     * The constructor for a Graph
     * @param reports An iterable list of reports whose data is to be displayed
     */
    public Graph(Iterable<Report> reports) {
        allYearList = new ArrayList<>();
        allMonthList = new ArrayList<>();
        allContamNumList = new ArrayList<>();
        allVirusNumList = new ArrayList<>();
        for (Report r : reports) {
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

    /**
     * Setting a new year to be displayed on the graph
     * @param newYear The year desired
     */
    public void setCurrentYear(int newYear) {
        if ((newYear < 2000) || (newYear > 2020)) {
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

    /**
     * Setting the graph to show all years from 2000 - 2020
     */
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

    /**
     * Change whether virus is displayed or not
     * @param b Boolean value of whether virus is displayed
     */
    public void setVirusDisplayed(Boolean b) {
        virusDisplayed = b;
        if (allYears) {
            setShowAll();
        } else {
            setCurrentYear(currentYear);
        }
    }

    /**
     * Change whether contaminant is displayed or not
     * @param b Boolean value of whether contaminant is displayed
     */
    public void setContaminantDisplayed(Boolean b) {
        contaminantDisplayed = b;
        if (allYears) {
            setShowAll();
        } else {
            setCurrentYear(currentYear);
        }
    }

    /**
     * Getter for current month list
     * @return ArrayList of current month data
     */
    public ArrayList<Integer> getCurrentMonthList() {
        return currentMonthList;
    }
    /**
     * Getter for current year list
     * @return ArrayList of current year data
     */
    public ArrayList<Integer> getCurrentYearList() {
        return currentYearList;
    }
    /**
     * Getter for current virus data list
     * @return ArrayList of current virus data
     */
    public ArrayList<Float> getCurrentVirusNumList() {
        return currentVirusNumList;
    }
    /**
     * Getter for current contaminant data list
     * @return ArrayList of current contaminant data
     */
    public ArrayList<Float> getCurrentContamNumList() {
        return currentContamNumList;
    }
    /**
     * Getter for boolean of whether all years are displayed
     * @return Boolean value, true if all years are displayed, false if one year is displayed
     */
    public boolean getAllYears() {
        return allYears;
    }
    /**
     * Getter for the upper value to be displayed on the x-axis
     * @return and int that is the upper x-axis value
     */
    public int getUpperXNum() {
        return upperXNum;
    }
    /**
     * Getter for the lower value to be displayed on the x-axis
     * @return and int that is the lower x-axis value
     */
    public int getLowerXNum() {
        return lowerXNum;
    }
    /**
     * Getter for the label to be displayed on the x-axis
     * @return a string to be used as the x-axis label
     */
    public String getxAxisLabel() {
        return xAxisLabel;
    }
    /**
     * Getter for boolean of whether virus data is displayed
     * @return Boolean value, true if virus data is displayed, false if it is not
     */
    public boolean getVirusDisplayed() {
        return virusDisplayed;
    }
    /**
     * Getter for boolean of whether contaminant data is displayed
     * @return Boolean value, true if contaminant data is displayed, false if it is not
     */
    public boolean getContaminantDisplayed() {
        return contaminantDisplayed;
    }
    /**
     * Getter for all virus data list
     * @return ArrayList of all virus data
     */
    public ArrayList<Float> getAllVirusNumList() {
        return allVirusNumList;
    }
    /**
     * Getter for all contaminant data list
     * @return ArrayList of all contaminant data
     */
    public ArrayList<Float> getAllContamNumList() {
        return allContamNumList;
    }
    /**
     * Getter for all month data list
     * @return ArrayList of all months
     */
    public ArrayList<Integer> getAllMonthList() {
        return allMonthList;
    }
    /**
     * Getter for all year data list
     * @return ArrayList of all year
     */
    public ArrayList<Integer> getAllYearList() {
        return allYearList;
    }

}