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
import src.model.User;

public class MapScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private User user;

    private String username;

    /* references to the widgets in the fxml file */

    @FXML
    private Button logoutButton;
    @FXML
    private Button viewReportsButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button submitButton;

    @FXML protected void handleSubmitButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ReportTypeScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            ReportTypeScreenController controller = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewReportsButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ReportTypeScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            ReportTypeScreenController controller = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleLogoutButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WelcomeScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            WelcomeScreenController controller = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleProfileButtonAction() {
        //logout stuff :)
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Profile.fxml"));

            anchorLayout = fxmlLoader.load();
            ProfileScreenController controller = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            controller.update(username);
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
            //put maps and data here maybe?
        }


    protected void passLogin(String usernameInput) {
        username = usernameInput;
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