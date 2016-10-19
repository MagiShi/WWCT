package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;
import src.model.WaterSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dain on 10/16/2016.
 */
public class SourceDetailScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;


    @FXML
    private Button backButton;
    @FXML
    private ListView<String> reportsList;
    @FXML
    private Label waterType;
    @FXML
    private Label waterCondition;
    @FXML
    private Label sourceCreator;
    @FXML
    private Label creationDate;

    private User currentUser;
    public void setUser(User newUser) {
        currentUser = newUser;
    }


    @FXML protected void backButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            MapScreenController msc = fxmlLoader.getController();

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

    @FXML protected void viewReportListButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ReportsList.fxml"));

            anchorLayout = fxmlLoader.load();
            ReportsListScreenController controller = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void addReportButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/waterQualityReportScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            waterQualityReportScreenController controller = fxmlLoader.getController();

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
    }

    public void setCurrentSource(String description) {
        String[] info = description.split("<");
        String[] userSplit = info[3].split(" ");
        String user = userSplit[3];
        String[] timeSplit = info[4].split(" ");
        String time = timeSplit[3];
        String[] typeSplit = info[5].split(" ");
        String type = typeSplit[3];
        String[] conditSplit = info[6].split(" ");
        String condition = conditSplit[4];
        sourceCreator.setText(user);
        creationDate.setText(time);
        waterCondition.setText(condition);
        waterType.setText(type);
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
