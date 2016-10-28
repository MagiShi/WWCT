package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;
import src.model.Location;
import src.model.WaterSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dain on 10/16/2016.
 */
public class ManagerSourceDetailScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private WaterSource currentSource;

    private Location currLoc;

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

    private User currentUser;
    public void setUser(User newUser) {
        currentUser = newUser;
    }


    public void setLocation (Location loc) { currLoc = loc; }

    private ArrayList<WaterSource> allReports = new ArrayList<WaterSource>();

    private ArrayList<WaterSource> thisSource = new ArrayList<WaterSource>();




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
        alreadyExists = new File("purityReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("purityReports.csv"))) {
                String line = "";
                while (((line = br.readLine()) != null)) {
                    reportsList.getItems().add(line);
                    System.out.println("Added");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
