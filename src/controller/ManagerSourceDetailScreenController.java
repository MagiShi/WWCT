package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;

import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.Report;
import src.model.User;
import src.model.Location;
import src.model.WaterSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.chart.XYChart;

/**
 * Created by Da-In on 10/16/2016.
 */
public class ManagerSourceDetailScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private WaterSource currentSource;

    private Location currLoc;
    private boolean allDisplayed = true;

    @FXML private Button backButton;
    @FXML private Label sourceLocation;
    @FXML private Label waterType;
    @FXML private Label waterCondition;
    @FXML private Label sourceCreator;
    @FXML private Label creationDate;
    @FXML private ListView<String> reportsList;
    @FXML private ScatterChart<Double, Double> historicalGraph;
    @FXML private NumberAxis xAxis;
    @FXML private TextField yearField;
    @FXML private CheckBox virusCheckBox;
    @FXML private CheckBox contaminantCheckBox;
    @FXML private TextField reportNumField;

    @FXML private Label waterTypeLabel;
    @FXML private Label locationLabel;
    @FXML private Label waterConditionLabel;
    @FXML private Label sourceCreatorLabel;
    @FXML private Label creationDateLabel;
    @FXML private Button backButton3;
    @FXML private Button addReportButton;
    @FXML private Button backButton1;
    @FXML private Label title;



    private User currentUser;
    private int displayedYear = 0;
    public void setUser(User newUser) {
        currentUser = newUser;
    }


    public void setLocation (Location loc) { currLoc = loc; }

    private final List<WaterSource> allReports = new ArrayList<>();

    //private ArrayList<WaterSource> thisSource = new ArrayList<WaterSource>();

    private final List<Report> currentReports = new ArrayList<>();

    private final List<String> reportStrings = new ArrayList<>();



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
    @FXML protected void reloadGraph() {
        if (allDisplayed) {
            showAllGraph();
        } else {
            showYearGraph(displayedYear);
        }
    }
    @FXML protected void showAllAction() {
        showAllGraph();
    }
    @FXML protected void chooseYearAction() {
        String thisYear = yearField.getText();
        try {
            Integer newYear = new Integer(thisYear);
            if ((newYear < 2000) || (newYear > 2020)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Year");
                alert.setHeaderText("Invalid input for Year");
                alert.setContentText("Please input valid year integer between 2000 and 2020.");
                alert.showAndWait();
                yearField.clear();
            } else {
                showYearGraph(newYear);
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
        boolean alreadyExists = new File("sourceReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                String line;//make into water source
                while (((line = br.readLine()) != null)) {
                    String[] data = line.split(",");
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
    }

    public void setCurrentSource(Location newSource) {
        currLoc = newSource;

        //thisSource.add(currentSource);
        allReports.stream().filter(source -> source.getLatitude().equals(currLoc.getLatitude())
                && source.getLongitude().equals(currLoc.getLongitude())).forEach(source -> {
            currentSource = source;
            //thisSource.add(currentSource);
        });

        sourceLocation.setText(currentSource.getLocation());
        waterType.setText(currentSource.getType());
        waterCondition.setText((currentSource.getSourceCondition()));
        sourceCreator.setText((currentSource.getUser()));
        creationDate.setText(currentSource.getDate());
        loadReports();
    }

    private void showAllGraph() {
        xAxis.setUpperBound(2020);
        xAxis.setLowerBound(2000);
        xAxis.setLabel("Year");
        historicalGraph.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName("Virus PPM");
        if (virusCheckBox.isSelected()) {
            for (Report r : currentReports) {
                float ppm = r.getVirusPPM();
                int year = r.getYear();
                series.getData().add(new XYChart.Data(year, ppm));
            }
        }
        historicalGraph.getData().add(series);
        series = new XYChart.Series();
        if (contaminantCheckBox.isSelected()) {
            for (Report r : currentReports) {
                float ppm = r.getContaminantPPM();
                int year = r.getYear();
                series.getData().add(new XYChart.Data(year, ppm));
            }
        }
        series.setName("Contaminant PPM");
        historicalGraph.getData().add(series);
    }
    private void showYearGraph(Integer year) {
        displayedYear = year;
        allDisplayed = false;
        List<Report> yearReports = currentReports.stream().filter(r -> r.getYear() == year).collect(Collectors.toList());
        xAxis.setUpperBound(12);
        xAxis.setLowerBound(1);
        xAxis.setLabel("Month");
        historicalGraph.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName("Virus PPM");
        if (virusCheckBox.isSelected()) {
            for (Report r : yearReports) {
                float ppm = r.getVirusPPM();
                int month = r.getMonth();
                series.getData().add(new XYChart.Data(month, ppm));
            }
        }
        historicalGraph.getData().add(series);
        series = new XYChart.Series();
        if (contaminantCheckBox.isSelected()) {
            for (Report r : yearReports) {
                float ppm = r.getContaminantPPM();
                int month = r.getMonth();
                series.getData().add(new XYChart.Data(month, ppm));
            }
        }
        series.setName("Contaminant PPM");
        historicalGraph.getData().add(series);
    }

    @FXML protected void handleRemoveReportButtonAction() {
        String reportNum = reportNumField.getText();
        if ("".equals(reportNum)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Number Entered");
            alert.setHeaderText("Please Enter a Number");
            alert.showAndWait();
        } else {
            removeReport(reportNum);
        }
    }

    private void removeReport(String reportNum) {
        boolean found = false;
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
            rewriteReportsFile();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Report Number not found");
            alert.setHeaderText("The Report Number was not found.");
            alert.showAndWait();
            reportNumField.clear();
        }
    }

    private void rewriteReportsFile() {
        BufferedWriter writer = null;
        try {
            File newFile = new File("purityReports.csv");
            writer = new BufferedWriter(new FileWriter(newFile));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while flushing, creating new.");
                ioe.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("purityReports.csv", true);
            while (!reportStrings.isEmpty()) {
                fileWriter.append(reportStrings.remove(0));
                fileWriter.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while flushing");
                ioe.printStackTrace();
            }
        }
        loadReports();
    }

    private void loadReports() {
        reportsList.getItems().clear();
        reportStrings.clear();
        currentReports.clear();
        boolean alreadyExists = new File("purityReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("purityReports.csv"))) {
                String line;
                while (((line = br.readLine()) != null)) {
                    String[] data = line.split(",");

                    Double lat = new Double(data[7]);
                    Double longit = new Double(data[8]);
                    if (currentSource.getLatitude().equals(lat) && currentSource.getLongitude().equals(longit)) {
                        reportsList.getItems().add(line);
                        Report r = new Report(data[1], data[2], Float.parseFloat(data[4]),
                                            Float.parseFloat(data[5]), data[6]);
                        currentReports.add(r);
                    }
                    reportStrings.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showAllGraph();
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
