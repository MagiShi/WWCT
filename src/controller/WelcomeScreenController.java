package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;

/**
 * controller that is linked to the WelcomeScreen fxml.
 * It handles what happens when actions are taken for the view
 * i.e. what happens when buttons are clicked etc.
 */
public class WelcomeScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

    @FXML private Button welcomeLoginButton;
    @FXML private Button welcomeRegisterButton;

    private AnchorPane anchorLayout;


    @FXML protected void handleWelcomeLoginButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/LoginScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            LoginScreenController controller = fxmlLoader.getController();

            Scene scene = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene);

            controller.setMainApp(mainApplication);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleWelcomeRegisterButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/RegisterScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            RegisterScreenController controller = fxmlLoader.getController();

            Scene scene = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene);

            controller.setMainApp(mainApplication);


        } catch (Exception e) {
            e.printStackTrace();
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

