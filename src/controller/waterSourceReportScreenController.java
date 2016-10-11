package src.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.model.User;
import src.model.UserType;

import java.io.*;
/**
 * Created by Ji Won Lee on 10/11/2016.
 */
public class waterSourceReportScreenController {
    private WaterzMainFXApplication mainApplication;
    private AnchorPane anchorLayout;

    /* references to the widgets in the fxml file */

    @FXML private Button submitBttn;
    @FXML private Button waterPurityReportBttn;
    @FXML private Button cancelBttn;
    @FXML private TextField reporterName;
    @FXML private TextField waterLocation;
    @FXML private ComboBox waterType;
    @FXML private ComboBox waterCondition;

    @FXML protected void submitBttnAction() {
        /**
         * TODO
         */
    }
    @FXML protected void waterPurityReportBttnAction() {
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
    @FXML protected void cancelBttnAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            MapScreenController controller = fxmlLoader.getController();

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
        waterType.getItems().removeAll(waterType.getItems());
        waterType.getItems().addAll("Bottled", "Well", "Stream", "Lake", "Spring", "Other");
        waterCondition.getItems().removeAll(waterCondition.getItems());
        waterCondition.getItems().addAll("Waste", "Treatable-Clear", "Treatable-Muddy", "Potable");
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
