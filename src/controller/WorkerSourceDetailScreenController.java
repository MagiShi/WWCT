package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.Location;
import src.model.User;
import src.model.WaterSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * controller that is linked to the WorkerSourceDetailScreen fxml.
 * It handles what happens when actions are taken for the view
 * i.e. what happens when buttons are clicked etc.
 */
public class WorkerSourceDetailScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private WaterSource currentSource;

    private Location currLoc;

    @FXML private Button backButton;

    @FXML private ListView<String> reportsList;
    @FXML private Label sourceLocation;
    @FXML private Label waterType;
    @FXML private Label waterCondition;
    @FXML private Label sourceCreator;
    @FXML private Label creationDate;

    @FXML private Label waterTypeLabel;
    @FXML private Label locationLabel;
    @FXML private Label waterConditionLabel;
    @FXML private Label sourceCreatorLabel;
    @FXML private Label creationDateLabel;
    @FXML private Button backButton3;
    @FXML private Button addReportButton;

    private User currentUser;

    private final Collection<WaterSource> allReports = new ArrayList<>();

    /**
     * The method sets who is the user of the account currently used in the app.
     * @param newUser   the User (the current Worker) that is logged in.
     */
    public void setUser(User newUser) {
        currentUser = newUser;
    }

    /**
     * The method sets what is the water source.
     * @param loc   the User (the current Worker) that is logged in.
     */
    public void setLocation (Location loc) { currLoc = loc; }

    @FXML protected void backButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            WorkerMapScreenController msc = fxmlLoader.getController();

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
        File tempFile = new File("sourceReports.csv");
        boolean alreadyExists = tempFile.exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                String line;//make into water source
                line = br.readLine();
                while (line != null) {
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
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tempFile = new File("purityReports.csv");
        alreadyExists = tempFile.exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("purityReports.csv"))) {
                String line;
                line = br.readLine();
                while (line != null) {
                    reportsList.getItems().add(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The method sets what is the water source.
     * @param newSource   the User (the current Worker) that is logged in.
     */
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
