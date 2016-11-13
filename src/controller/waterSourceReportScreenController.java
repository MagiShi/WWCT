package src.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import src.model.Facade;
import src.model.Location;
import src.model.SourceType;
import src.model.User;
import src.model.WaterCondition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * controller that is linked to the waterSourceReportScreen fxml.
 * It handles what happens when actions are taken for the view
 * i.e. what happens when buttons are clicked etc.
 */
public class waterSourceReportScreenController {
    private WaterzMainFXApplication mainApplication;
    private AnchorPane anchorLayout;

    /* references to the widgets in the fxml file */

    @FXML private Button submitButton;
    @FXML private Button waterPurityReportButton;
    @FXML private Button cancelButton;
    @FXML private TextField reporterName;
    @FXML private TextField waterLocation;
    @FXML private ComboBox<SourceType> waterType;
    @FXML private ComboBox<WaterCondition> waterCondition;
    @FXML private TextField latitudeInput;
    @FXML private TextField longitudeInput;

    private User currentUser;
    private Facade fc;
    private String userInputName;
    private String userInputLocation;
    private SourceType userInputWaterType;
    private WaterCondition userInputWaterCondition;
    private double longit;
    private double lat;
    private final int MINNUM = -90;
    private final int MAXNUM = 90;


    /**
     * Lets the app know which user is currently logged on.
     * This is important for displaying user info in the profile screen,
     * but the method is needed for all screens
     * because the current user needs to be continuously "held onto."
     *
     * @param newUser the User instance holding the current User's data
     */
    public void setUser(User newUser) {
        currentUser = newUser;
    }


    /**
     * sets the facade object in the controller
     * @param fc    the facade being set
     */
    public void setFacade(Facade fc) {this.fc = fc;}

    @FXML protected void submitButtonAction() {
        userInputName = reporterName.getText();
        userInputLocation = waterLocation.getText();
        userInputWaterType = waterType.getValue();
        userInputWaterCondition = waterCondition.getValue();

        lat = getLat();
        longit = getLongit();

        if (checkValidity()) {
            boolean alreadyExists = new File("sourceReports.csv").exists();
            if (!alreadyExists) {
                handleExists();
            }
            writeToFile();
            switch (currentUser.getType()) {
                case "MANAGER":
                    changeToManagerMap();
                    break;
                case "WORKER":
                    changeToWorkerMap();
                    break;
                default:
                    changeToUserMap();
                    break;
            }
        }
    }

    private boolean checkValidity() {
        boolean valid = true;
        if ("".equals(userInputName)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty User Name field");
            alert.setContentText("Please input your name.");
            alert.showAndWait();
            valid = false;
        }
        if ("".equals(userInputLocation)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty User Name field");
            alert.setContentText("Please input a location.");
            alert.showAndWait();
            valid = false;
        }
        if (userInputWaterCondition == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty User Name field");
            alert.setContentText("Please input a Water Condition.");
            alert.showAndWait();
            valid = false;
        }
        if (userInputWaterType == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty User Name field");
            alert.setContentText("Please input a Water Type.");
            alert.showAndWait();
            valid = false;
        }
        return valid;
    }

    private double getLat() {

        double lat = 0;
        try {
            if (latitudeInput != null) {
                lat = Double.valueOf(latitudeInput.getText());
                if ((lat < MINNUM) || (lat > MAXNUM)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid latitude");
                    alert.setHeaderText("Invalid input for latitude");
                    alert.setContentText("Please input valid latitude in integer/decimal value between -90 and 90.");
                    alert.showAndWait();
                    latitudeInput.clear();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Empty latitude field");
                alert.setHeaderText("No input for latitude");
                alert.setContentText("Please input a latitude value.");
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
        }
        return lat;
    }

    private double getLongit() {
        double longit = 0;
        try {
            if (longitudeInput != null) {
                longit = Double.valueOf(longitudeInput.getText());
                if ((longit < MINNUM) || (longit > MAXNUM)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid longitude");
                    alert.setHeaderText("Invalid input for longitude");
                    alert.setContentText("Please input valid longitude in integer/decimal value between -180 and 180.");
                    alert.showAndWait();
                    latitudeInput.clear();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Empty longitude field");
                alert.setHeaderText("No input for longitude");
                alert.setContentText("Please input a longitude value.");
                alert.showAndWait();
                longitudeInput.clear();
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid longitude");
            alert.setHeaderText("Invalid input for longitude");
            alert.setContentText("Please input longitude in integer/decimal value.");
            alert.showAndWait();
            longitudeInput.clear();
        }
        return longit;
    }

    private void handleExists() {
        BufferedWriter writer = null;
        try {
            File newFile = new File("sourceReports.csv");
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
                ioe.printStackTrace();
            }
        }
    }

    private void writeToFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("sourceReports.csv", true);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
            Date dateObject = new Date();
            String dateString = dateFormat.format(dateObject);

            int RANDMAXNUM = 9999;
            int num = 1000 + (int) (Math.random() * ((RANDMAXNUM - 1000) + 1));

            fileWriter.append("Report #").append(Integer.toString(num)).append(", ");
            fileWriter.append(userInputName).append(", ");
            fileWriter.append(dateString).append(", ");
            fileWriter.append(userInputLocation).append(", ");
            fileWriter.append(latitudeInput.getText()).append(",");
            fileWriter.append(longitudeInput.getText()).append(",");
            fileWriter.append(userInputWaterType.toString()).append(", ");
            fileWriter.append(userInputWaterCondition.toString()).append("\n");

            Location loc = new Location(lat, longit,"Marker","<h2> " + num + "</h2> <br> Reporter: "
                    + userInputName + "<br> Date: " + dateString + "<br> Water Type: "
                    + userInputWaterType + "<br> Water Condition: " + userInputWaterCondition);

            fc.addLocation(loc);

            //create a new report? should we have a report class?
            //newReport = new Report(...);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


    @FXML protected void cancelButtonAction() {
        switch (currentUser.getType()) {
            case "MANAGER":
                changeToManagerMap();
                break;
            case "WORKER":
                changeToWorkerMap();
                break;
            default:
                changeToUserMap();
                break;
        }
    }

    private void changeToManagerMap() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            ManagerMapScreenController msc = fxmlLoader.getController();
            msc.setUser(currentUser);

            msc.setApp(mainApplication);
            msc.setState(mainApplication.getMainScreen());
            msc.setUpMapView(mainApplication.getMainScreen());

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            msc.setMainApp(mainApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToWorkerMap() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            WorkerMapScreenController msc = fxmlLoader.getController();
            msc.setUser(currentUser);

            msc.setApp(mainApplication);
            msc.setState(mainApplication.getMainScreen());
            msc.setUpMapView(mainApplication.getMainScreen());

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            msc.setMainApp(mainApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToUserMap() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UserMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            UserMapScreenController msc = fxmlLoader.getController();
            msc.setUser(currentUser);

            msc.setApp(mainApplication);
            msc.setState(mainApplication.getMainScreen());
            msc.setUpMapView(mainApplication.getMainScreen());

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            msc.setMainApp(mainApplication);
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