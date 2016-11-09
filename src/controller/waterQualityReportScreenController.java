package src.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.model.Location;
import src.model.PurityCondition;
import src.model.User;
import src.model.WaterSource;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private User currentUser;

    private Location currLoc;

    private ArrayList<WaterSource> thisSource = new ArrayList<WaterSource>();

    public void setUser(User newUser) {
        currentUser = newUser;
    }

    public void setLocation (Location loc) { currLoc = loc; }

    public void setThisSource (ArrayList<WaterSource> list) {thisSource = list;}


    @FXML protected void submitBttnAction() {
        String userInputName = workerName.getText();
        String userInputLocation = waterLocation.getText();
        String userInputVirusPPM = virusPPM.getText();
        String userInputContaminantPPM = contaminantPPM.getText();
        PurityCondition userInputOverallCondition = waterOverallCondition.getValue();
        double vPPM;
        double cPPM;
        boolean valid = true;
        //making sure latitude input and longitude input are int/decimal values
        if (userInputName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty User Name field");
            alert.setContentText("Please input your name.");
            alert.showAndWait();
            valid = false;
        }
        if (userInputLocation.equals("")) {
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
        try {
            vPPM = Double.valueOf(userInputVirusPPM);
            if (vPPM < 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Virus PPM");
                alert.setContentText("Invalid Input for Virus PPM");
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
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Virus PPM field");
            alert.setContentText("Please input a Virus PPM value.");
            alert.showAndWait();
            virusPPM.clear();
            valid = false;
        }
        try {
            cPPM = Double.valueOf(userInputContaminantPPM);
            if (cPPM < 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Contaminant PPM");
                alert.setContentText("Invalid Input for Contaminant PPM");
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
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Contaminant PPM field");
            alert.setContentText("Please input a Contaminant PPM value.");
            alert.showAndWait();
            contaminantPPM.clear();
            valid = false;
        }
        if (valid) {
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

                    String latitude = String.valueOf(currLoc.getLatitude());
                    String longitude = String.valueOf(currLoc.getLongitude());

                    fileWriter.append("Report #" + 1000 + (int) (Math.random() * ((9999 - 1000) + 1)));
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
                    fileWriter.append(", ");
                    fileWriter.append(latitude);
                    fileWriter.append(", ");
                    fileWriter.append(longitude);
                    fileWriter.append("\n");

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
                        System.out.println("Error while flushing");
                        ioe.printStackTrace();
                    }
                }
                if (currentUser.getType().equals("WORKER")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerSourceDetailScreen.fxml"));

                    anchorLayout = fxmlLoader.load();
                    WorkerSourceDetailScreenController controller = fxmlLoader.getController();

                    controller.setUser(currentUser);
                    controller.setCurrentSource(currLoc);
                    controller.setLocation(currLoc);

                    Scene scene2 = new Scene(anchorLayout);
                    mainApplication.getMainScreen().setScene(scene2);

                    controller.setMainApp(mainApplication);
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerSourceDetailScreen.fxml"));

                    anchorLayout = fxmlLoader.load();
                    ManagerSourceDetailScreenController controller = fxmlLoader.getController();

                    controller.setUser(currentUser);
                    controller.setCurrentSource(currLoc);
                    controller.setLocation(currLoc);

                    Scene scene2 = new Scene(anchorLayout);
                    mainApplication.getMainScreen().setScene(scene2);

                    controller.setMainApp(mainApplication);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML protected void cancelBttnAction() {
        try {
            if (currentUser.getType().equals("MANAGER")) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerSourceDetailScreen.fxml"));

                anchorLayout = fxmlLoader.load();
                ManagerSourceDetailScreenController controller = fxmlLoader.getController();

                controller.setUser(currentUser);
                controller.setCurrentSource(currLoc);
                controller.setLocation(currLoc);
                Scene scene2 = new Scene(anchorLayout);
                mainApplication.getMainScreen().setScene(scene2);

                controller.setMainApp(mainApplication);
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerSourceDetailScreen.fxml"));

                anchorLayout = fxmlLoader.load();
                WorkerSourceDetailScreenController controller = fxmlLoader.getController();

                controller.setUser(currentUser);
                controller.setCurrentSource(currLoc);
                controller.setLocation(currLoc);
                Scene scene2 = new Scene(anchorLayout);
                mainApplication.getMainScreen().setScene(scene2);

                controller.setMainApp(mainApplication);
            }

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