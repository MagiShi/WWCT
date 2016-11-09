package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.*;

import java.io.*;
import java.util.ArrayList;

import javafx.scene.chart.XYChart;

/**
 * Created by Dain on 10/16/2016.
 */
public class ManagerSourceDetailScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private WaterSource currentSource;

    private Location currLoc;
    private boolean allDisplayed = true;
    private boolean virusSelected = false;
    private boolean contaminantSelected = false;

    @FXML
    private Button backButton;
    @FXML
    private Label sourceLocation;
    @FXML
    private Label waterType;
    @FXML
    private Label waterCondition;
    @FXML
    private Label sourceCreator;
    @FXML
    private Label creationDate;
    @FXML
    private ListView<String> reportsList;
    @FXML
    private ScatterChart<Double, Double> historicalGraph;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private TextField yearField;
    @FXML
    private CheckBox virusCheckBox;
    @FXML
    private CheckBox contaminantCheckBox;
    @FXML
    private TextField reportNumField;

    private Graph graph;


    private User currentUser;
    private int displayedYear = 0;
    public void setUser(User newUser) {
        currentUser = newUser;
    }


    public void setLocation (Location loc) { currLoc = loc; }

    private ArrayList<WaterSource> allReports = new ArrayList<WaterSource>();

    private ArrayList<WaterSource> thisSource = new ArrayList<WaterSource>();

    private ArrayList<Report> currentReports = new ArrayList<>();

    private ArrayList<String> reportStrings = new ArrayList<>();



    @FXML protected void backButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            ManagerMapScreenController msc = fxmlLoader.getController();

            msc.setApp(mainApplication);
            msc.setState(mainApplication.getMainScreen());
            msc.setUpMapView(mainApplication.getMainScreen());

            msc.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            msc.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void addReportButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/waterQualityReportScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            waterQualityReportScreenController controller = fxmlLoader.getController();

            controller.setUser(currentUser);
            controller.setLocation(currLoc);

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     @FXML protected void handleVirusCheckbox() {
         if (virusCheckBox.isSelected()) {
             graph.setVirusDisplayed(true);
         } else {
             graph.setVirusDisplayed(false);
         }
         showGraph();
     }
    @FXML protected void handleContaminantCheckbox() {
        if (contaminantCheckBox.isSelected()) {
            graph.setContaminantDisplayed(true);
        } else {
            graph.setContaminantDisplayed(false);
        }
        showGraph();
    }

    //Handles show 20 Year Graph
    @FXML protected void showAllAction() {
        graph.setShowAll();
        showGraph();
    }

    /**
     * Displays Graph
     */
    public void showGraph() {
        xAxis.setUpperBound(graph.getUpperXNum());
        xAxis.setLowerBound(graph.getLowerXNum());
        xAxis.setLabel(graph.getxAxisLabel());
        historicalGraph.getData().clear();
        XYChart.Series series = new XYChart.Series();
        ArrayList<Float> virusList = graph.getCurrentVirusNumList();
        ArrayList<Float> contamList = graph.getCurrentContamNumList();
        ArrayList<Integer> dateList;
        if (graph.getAllYears()) {
            dateList = graph.getCurrentYearList();
        } else {
            dateList = graph.getCurrentMonthList();
        }
        series.setName("Virus PPM");
        if (graph.getVirusDisplayed()) {
            for (int i = 0; i < virusList.size() && i < dateList.size(); i++) {
                float ppm = virusList.get(i);
                int date = dateList.get(i);
                series.getData().add(new XYChart.Data(date, ppm));
            }
        }
        historicalGraph.getData().add(series);
        series = new XYChart.Series();
        if (graph.getContaminantDisplayed()) {
            for (int i = 0; i < contamList.size() && i < dateList.size(); i++) {
                System.out.println("Adding Contam " + i);
                float ppm = contamList.get(i);
                int date = dateList.get(i);
                series.getData().add(new XYChart.Data(date, ppm));
            }
        }
        series.setName("Contaminant PPM");
        historicalGraph.getData().add(series);
    }


    //Handle specific year graph
    @FXML protected void chooseYearAction() {
        String thisYear = yearField.getText();
        parseYear(thisYear);
    }

    private void parseYear(String thisYear) {
        try {
            Integer newYear = new Integer(thisYear);
            try {
                graph.setCurrentYear(newYear);
                showGraph();
            } catch(IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Year");
                alert.setHeaderText("Invalid input for Year");
                alert.setContentText("Please input valid year integer between 2000 and 2020.");
                alert.showAndWait();
                yearField.clear();
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Year");
            alert.setHeaderText("Invalid input for year");
            alert.setContentText("Please input year as an integer value.");
            alert.showAndWait();
            yearField.clear();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty year field");
            alert.setHeaderText("No input for year.");
            alert.setContentText("Please input a year value.");
            alert.showAndWait();
            yearField.clear();
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the constructor and
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        User newUser = new User("manager", "pass", "name", "MANAGER", "a email", "a address", "Not Banned");
        currentUser = newUser;
        boolean alreadyExists = new File("sourceReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                String line = "";//make into water source
                while (((line = br.readLine()) != null)) {
                    String[] data = line.split(",");
                    String reportNum = data[0];
                    String name = data[1];
                    String dateTime = data[2];
                    String locationName = data[3];
                    Double lat = Double.parseDouble(data[4]);
                    Double longit = Double.parseDouble(data[5]);
                    String type = data[6];
                    String sourceCondition = data[7];

                    WaterSource wc = new WaterSource(name, dateTime,locationName, lat, longit, type, sourceCondition);

                    allReports.add(wc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String line = "Report #7739,Alice,10/18/16 22:39,Lewis,33,-88,WELL,POTABLE,";
        String[] linebroken = line.split(",");
        Location l = new Location(Double.valueOf(linebroken[4]),
                Double.valueOf(linebroken[5]),
                "Marker " + 0,
                "<h2> "  + linebroken[0] +
                        "</h2> <br> Reporter: " + linebroken[1] +
                        "<br> Date: " + linebroken[2] +
                        "<br> Water Type: " + linebroken[6] +
                        "<br> Water Condition: " + linebroken[7]);
        setCurrentSource(l);
    }

    public void setCurrentSource(Location newSource) {
        currLoc = newSource;

        for (WaterSource source: allReports) {
            if (source.getLatitude().equals(currLoc.getLatitude()) && source.getLongitude().equals(currLoc.getLongitude())) {
                currentSource = source;
                thisSource.add(currentSource);
            }
        }

        sourceLocation.setText(currentSource.getLocation());
        waterType.setText(currentSource.getType());
        waterCondition.setText((currentSource.getSourceCondition()));
        sourceCreator.setText((currentSource.getUser()));
        creationDate.setText(currentSource.getDate());
        loadReports();
    }

    @FXML protected void handleRemoveReportButtonAction() {
        System.out.println("DELETING REPORT!!!");
        boolean found = false;
        String reportNum = reportNumField.getText();
        if (reportNum.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Number Entered");
            alert.setHeaderText("Please Enter a Number");
            alert.showAndWait();
        } else {
            for (int i = 0; i < currentReports.size(); i++) {
                String report = reportStrings.get(i);
                String[] reportData = report.split(",");
                String[] numArray = reportData[0].split("#");
                if (numArray[1].equals(reportNum)) {
                    reportStrings.remove(i);
                    i--;
                    found = true;
                }
            }
            if (found) {
                BufferedWriter writer = null;
                try {
                    File newFile = new File("purityReports.csv");
                    writer = new BufferedWriter(new FileWriter(newFile));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        writer.flush();
                        writer.close();
                    } catch (IOException ioe) {
                        System.out.println("Error while flushing, creating new.");
                        ioe.printStackTrace();
                    }
                }
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("purityReports.csv", true);
                    while (reportStrings.size() > 0) {
                        fileWriter.append(reportStrings.remove(0));
                        fileWriter.append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException ioe) {
                        System.out.println("Error while flushing");
                        ioe.printStackTrace();
                    }
                }
                loadReports();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Report Number not found");
                alert.setHeaderText("The Report Number was not found.");
                alert.showAndWait();
                reportNumField.clear();
            }
        }
    }

    private void loadReports() {
        reportsList.getItems().clear();
        reportStrings.clear();
        currentReports.clear();
        boolean alreadyExists = new File("purityReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("purityReports.csv"))) {
                String line = "";
                while (((line = br.readLine()) != null)) {
                    String[] data = line.split(",");
                    for (int i = 0; i < data.length; i++) {
                        System.out.print(data[i] + ", ");
                    }
                    System.out.println();
                    Double lat = new Double(data[7]);
                    Double longit = new Double(data[8]);
                    if (currentSource.getLatitude().equals(lat) && currentSource.getLongitude().equals(longit)) {
                        reportsList.getItems().add(line);
                        System.out.println("Added");
                        Report r = new Report(data[1], data[2], Float.parseFloat(data[4]), Float.parseFloat(data[5]), data[6]);
                        currentReports.add(r);
                    }
                    reportStrings.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        graph = new Graph(currentReports);
        showGraph();
    }


    /**
     * Setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(WaterzMainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;

    }

}
