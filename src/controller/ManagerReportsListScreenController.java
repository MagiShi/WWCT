package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * Created by Dain on 10/12/2016.
 */
public class ManagerReportsListScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    @FXML
    private Button backButton;
    @FXML
    private ListView<String> reportsList;
    @FXML
    private Button viewQualityReport;

    private User currentUser;

    private Location currLoc;

    private ArrayList<WaterSource> thisSource = new ArrayList<WaterSource>();

    public void setUser(User newUser) {
        currentUser = newUser;
    }

    public void setLocation (Location loc) { currLoc = loc; }

    public void setThisSource (ArrayList<WaterSource> list) {thisSource = list;}


    @FXML protected void backButtonAction() {
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
        boolean alreadyExists = new File("purityReports.csv").exists();
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
    /**
     * Setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(WaterzMainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;

    }
}
