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
import src.model.PurityCondition;
import src.model.User;
import src.model.UserType;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ji Won Lee on 10/11/2016.
 */
public class waterQualityReportScreenController {
    private WaterzMainFXApplication mainApplication;
    private AnchorPane anchorLayout;

    /* references to the widgets in the fxml file */

    @FXML private Button submitBttn;
    @FXML private Button waterSourceReportBttn;
    @FXML private Button cancelBttn;
    @FXML private TextField workerName;
    @FXML private TextField waterLocation;
    @FXML private TextField virusPPM;
    @FXML private TextField contaminantPPM;
    @FXML private ComboBox<PurityCondition> waterOverallCondition;

    @FXML protected void submitBttnAction() {
        String userInputName = workerName.getText();
        String userInputLocation = waterLocation.getText();
        String userInputVirusPPM = virusPPM.getText();
        String userInputContaminantPPM = contaminantPPM.getText();
        PurityCondition userInputOverallCondition = waterOverallCondition.getValue();

        boolean alreadyExists = new File("purityReports.csv").exists();
        if (!alreadyExists) {
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
        }

        try {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("purityReports.csv", true);

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
                Date dateObject = new Date();
                String dateString = dateFormat.format(dateObject);

                fileWriter.append("Report #" + 1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
                fileWriter.append(", ");
                fileWriter.append(userInputName);
                fileWriter.append(", ");
                fileWriter.append(dateString);
                fileWriter.append(", ");
                fileWriter.append(userInputLocation);
                fileWriter.append(", ");
                fileWriter.append(userInputVirusPPM);
                fileWriter.append(", ");
                fileWriter.append(userInputContaminantPPM);
                fileWriter.append(", ");
                fileWriter.append(userInputOverallCondition.toString());
                fileWriter.append("\n");

                //create a new report? should we have a report class?
                //newReport = new Report(...);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                }  catch (IOException ioe) {
                    System.out.println("Error while flushing");
                    ioe.printStackTrace();
                }
            }


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            MapScreenController msc = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            msc.setMainApp(mainApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @FXML protected void waterSourceReportBttnAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/waterSourceReportScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            waterSourceReportScreenController controller = fxmlLoader.getController();

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
        waterOverallCondition.getItems().removeAll(waterOverallCondition.getItems());
        waterOverallCondition.getItems().addAll(PurityCondition.SAFE,
                PurityCondition.TREATABLE, PurityCondition.UNSAFE);
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