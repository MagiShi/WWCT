package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.fxapp.WaterzMainFXApplication;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.control.*;


/**
 * Created by Maggie on 9/28/2016.
 */
public class ProfileScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    /* references to the widgets in the fxml file */

    @FXML
    private Button backButton;
    @FXML
    private Button saveName;
    @FXML
    private Button saveEmail;
    @FXML
    private Button saveAddress;

    @FXML
    private Label currentName;
    @FXML
    private Label currentEmail;
    @FXML
    private Label currentAddress;

    @FXML
    private TextField newName;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newAddress;

    @FXML protected void handleBackButtonAction() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

                anchorLayout = fxmlLoader.load();
                WelcomeScreenController controller = fxmlLoader.getController();

                Scene scene2 = new Scene(anchorLayout);
                mainApplication.getMainScreen().setScene(scene2);

                controller.setMainApp(mainApplication);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @FXML protected void handleSaveName() {
    }

    @FXML protected void handleSaveEmail() {
    }

    @FXML protected void handleSaveAddress() {
    }


        /**
         * Initializes the controller class. This method is automatically called
         * after the constructor and
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
            //put maps and data here maybe?
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
