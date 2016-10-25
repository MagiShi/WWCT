package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;
import src.model.WaterSource;

/**
 * Created by Dain on 10/16/2016.
 */
public class WorkerSourceDetailScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private WaterSource currentSource;

    @FXML
    private Button backButton;

    @FXML
    private Button viewQuality;
    @FXML
    private ListView<String> reportsList;
    @FXML
    private Label sourceLocation;
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

    @FXML protected void viewReportListButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerReportsList.fxml"));

            anchorLayout = fxmlLoader.load();
            WorkerReportsListScreenController controller = fxmlLoader.getController();

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

    public void setCurrentSource(WaterSource newSource) {
        currentSource = newSource;
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
