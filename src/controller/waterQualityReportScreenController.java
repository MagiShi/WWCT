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
import src.model.Location;
import src.model.PurityCondition;
import src.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * controller that is linked to the waterQualityReportScreen fxml.
 * It handles what happens when actions are taken for the view
 * i.e. what happens when buttons are clicked etc.
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

    private User currentUser;
    private Location currLoc;
    private String userInputName;
    private String userInputLocation;
    private String userInputVirusPPM;
    private String userInputContaminantPPM;
    private PurityCondition userInputOverallCondition;
    private final int RANDMAXNUM = 9999;

    //private ArrayList<WaterSource> thisSource = new ArrayList<>();

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
     * Set the location that the controller's linked screen is displaying
     * @param loc   The location being set
     */
    public void setLocation (Location loc) { currLoc = loc; }

    @FXML protected void submitBttnAction() {
        userInputName = workerName.getText();
        userInputLocation = waterLocation.getText();
        userInputVirusPPM = virusPPM.getText();
        userInputContaminantPPM = contaminantPPM.getText();
        userInputOverallCondition = waterOverallCondition.getValue();

        if (checkValidity() && verifyvPPM() && verifycPPM()) {
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
                        if (writer != null) {
                            writer.flush();
                            writer.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }

            try {
                writeToFile();
                if ("WORKER".equals(currentUser.getType())) {
                    changeToWorkerSource();
                } else {
                    changeToManagerSource();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkValidity()  {
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
            alert.setTitle("Empty Location field");
            alert.setContentText("Please input a location value.");
            alert.showAndWait();
            valid = false;
        }
        if (userInputOverallCondition == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Condition field");
            alert.setContentText("Please input a Condition value.");
            alert.showAndWait();
            valid = false;
        }
        return valid;
    }

    private boolean verifyvPPM() {
        boolean valid = true;
        try {
            if (userInputVirusPPM != null) {
                double vPPM = Double.valueOf(userInputVirusPPM);
                if (vPPM < 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Virus PPM");
                    alert.setContentText("Invalid Input for Virus PPM");
                    alert.showAndWait();
                    virusPPM.clear();
                    valid = false;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Empty Virus PPM field");
                alert.setContentText("Please input a Virus PPM value.");
                alert.showAndWait();
                virusPPM.clear();
                valid = false;
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Virus PPM");
            alert.setContentText("Please input virusPPM in integer/decimal value.");
            alert.showAndWait();
            virusPPM.clear();
            valid = false;
        }
        return valid;
    }

    private boolean verifycPPM() {
        boolean valid = true;
        try {
            if (userInputContaminantPPM != null) {

                double cPPM = Double.valueOf(userInputContaminantPPM);
                if (cPPM < 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Contaminant PPM");
                    alert.setContentText("Invalid Input for Contaminant PPM");
                    alert.showAndWait();
                    contaminantPPM.clear();
                    valid = false;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Empty Contaminant PPM field");
                alert.setContentText("Please input a Contaminant PPM value.");
                alert.showAndWait();
                contaminantPPM.clear();
                valid = false;
            }
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Contaminant PPM");
            alert.setContentText("Please input Contaminant PPM in integer/decimal value.");
            alert.showAndWait();
            contaminantPPM.clear();
            valid = false;
        }
        return valid;
    }

    private void writeToFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("purityReports.csv", true);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
            Date dateObject = new Date();
            String dateString = dateFormat.format(dateObject);

            String latitude = String.valueOf(currLoc.getLatitude());
            String longitude = String.valueOf(currLoc.getLongitude());

            fileWriter.append("Report #" + 1000)
                    .append(Integer.toString((int)(Math.random() * ((RANDMAXNUM - 1000) + 1)))).append(", ");
            fileWriter.append(userInputName).append(", ");
            fileWriter.append(dateString).append(", ");
            fileWriter.append(userInputLocation).append(", ");
            fileWriter.append(userInputVirusPPM).append(", ");
            fileWriter.append(userInputContaminantPPM).append(", ");
            fileWriter.append(userInputOverallCondition.toString()).append(", ");
            fileWriter.append(latitude).append(", ");
            fileWriter.append(longitude).append("\n");

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

    @FXML protected void cancelBttnAction() {
        try {
            if ("MANAGER".equals(currentUser.getType())) {
                changeToManagerSource();
            } else {
                changeToWorkerSource();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToWorkerSource() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerSourceDetailScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            WorkerSourceDetailScreenController controller = fxmlLoader.getController();

            controller.setUser(currentUser);
            controller.setCurrentSource(currLoc);
            controller.setLocation(currLoc);

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToManagerSource()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerSourceDetailScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            ManagerSourceDetailScreenController controller = fxmlLoader.getController();

            controller.setUser(currentUser);
            controller.setCurrentSource(currLoc);
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