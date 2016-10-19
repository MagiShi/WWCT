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
import src.model.SourceType;
import src.model.User;
import src.model.UserType;
import src.model.WaterCondition;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @FXML private ComboBox<SourceType> waterType;
    @FXML private ComboBox<WaterCondition> waterCondition;
    @FXML private TextField latitudeInput;
    @FXML private TextField longitudeInput;

    @FXML protected void submitBttnAction() {
        String userInputName = reporterName.getText();
        String userInputLocation = waterLocation.getText();
        SourceType userInputWaterType = waterType.getValue();
        WaterCondition userInputWaterCondition = waterCondition.getValue();

        //making sure latitude input and longitude input are int/decimal values
        try {
            double lat = Double.valueOf(latitudeInput.getText());
            if (lat < -90 || lat > 90) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid latitude");
                alert.setHeaderText("Invalid input for latitude");
                alert.setContentText("Please input valid latitude in integer/decimal value between -90 and 90.");
                alert.showAndWait();
                latitudeInput.clear();
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid latitude");
            alert.setHeaderText("Invalid input for latitude");
            alert.setContentText("Please input latitude in integer/decimal value.");
            alert.showAndWait();
            latitudeInput.clear();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty latitude field");
            alert.setHeaderText("No input for latitude");
            alert.setContentText("Please input a latitude value.");
            alert.showAndWait();
            latitudeInput.clear();
        }
        try {
            Double longit = Double.valueOf(longitudeInput.getText());
            if (longit < -90 || longit > 90) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid longitude");
                alert.setHeaderText("Invalid input for longitude");
                alert.setContentText("Please input valid longitude in integer/decimal value between -180 and 180.");
                alert.showAndWait();
                latitudeInput.clear();
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid longitude");
            alert.setHeaderText("Invalid input for longitude");
            alert.setContentText("Please input longitude in integer/decimal value.");
            alert.showAndWait();
            longitudeInput.clear();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty longitude field");
            alert.setHeaderText("No input for longitude");
            alert.setContentText("Please input a longitude value.");
            alert.showAndWait();
            longitudeInput.clear();
        }

        boolean alreadyExists = new File("sourceReports.csv").exists();
        if (!alreadyExists) {
            BufferedWriter writer = null;
            try {
                File newFile = new File("sourceReports.csv");
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
                fileWriter = new FileWriter("sourceReports.csv", true);

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
                fileWriter.append(latitudeInput.getText());
                fileWriter.append(",");
                fileWriter.append(longitudeInput.getText());
                fileWriter.append(",");
                fileWriter.append(userInputWaterType.toString());
                fileWriter.append(", ");
                fileWriter.append(userInputWaterCondition.toString());
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
        waterType.getItems().addAll(SourceType.BOTTLED, SourceType.WELL,
                SourceType.STREAM, SourceType.LAKE, SourceType.SPRING, SourceType.OTHER);
        waterCondition.getItems().removeAll(waterCondition.getItems());
        waterCondition.getItems().addAll(WaterCondition.WASTE, WaterCondition.CLEAR,
                WaterCondition.MUDDY, WaterCondition.POTABLE);
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