package src.controller;

/**
 * Created by Cassie on 9/22/2016.
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import src.fxapp.WaterzMainFXApplication;

import java.io.IOException;

public class WelcomeScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

    @FXML private Button welcomeLoginButton;
    @FXML private Button welcomeRegisterButton;

    private BorderPane rootLayout;


    @FXML protected void handleWelcomeLoginButtonAction() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

            rootLayout = fxmlLoader.load();
            LoginScreenController lsc = fxmlLoader.getController();

            Scene scene2 = new Scene(rootLayout);
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
        //load animation here?
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

