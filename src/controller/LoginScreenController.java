package src.controller;

/**
 * Created by Dain on 9/20/2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class LoginScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

    @FXML private Button loginButton;
    @FXML private Button cancelButton;

    private BorderPane rootLayout;


    @FXML protected void handleLoginButtonAction() throws IOException{
        //check login


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

            rootLayout = fxmlLoader.load();
            MapScreenController msc = fxmlLoader.getController();

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

