package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ReportsListScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    @FXML
    private Button backButton;
    @FXML
    private ListView<String> reportsList;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SourceDetailScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            SourceDetailScreenController controller = fxmlLoader.getController();

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
//        System.out.println(thisSource);
//        for (int i = 0; i < thisSource.size(); i++) {
//            System.out.println(i);
//            String line = thisSource.get(i).toString();
//            reportsList.getItems().add(line);
//        }
//        for (WaterSource ws: thisSource) {
//            System.out.println("in loop");
//            String line = "";
//            line = ws.toString();
//            reportsList.getItems().add(line);
//        }
//        boolean alreadyExists = new File("sourceReports.csv").exists();
//        if (alreadyExists) {
//            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
//                String line = "";
//                while (((line = br.readLine()) != null)) {
//                    reportsList.getItems().add(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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
