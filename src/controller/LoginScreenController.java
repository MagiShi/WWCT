package src.controller;

/**
 * Created by Dain on 9/20/2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.model.User;
import src.model.UserManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

    @FXML private Button loginButton;
    @FXML private Button cancelButton;
    @FXML private TextField usernameInput;
    @FXML private TextField passwordInput;

    private BorderPane borderLayout;
    private AnchorPane anchorLayout;

    private String username;
    private String password;

    MapScreenController mapController = new MapScreenController();


    @FXML protected void handleLoginButtonAction() throws IOException{
        UserManager uM = UserManager.getInstance();
        if (uM.userExists(usernameInput.getText())) {
            if (uM.passwordCorrect(usernameInput.getText(), passwordInput.getText())) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

                    anchorLayout = fxmlLoader.load();
                    MapScreenController msc = fxmlLoader.getController();

                    msc.setApp(mainApplication);
                    msc.setState(mainApplication.getMainScreen());
                    msc.setUpMapView(mainApplication.getMainScreen());


                    msc.setUser(uM.getCurrentUser());
                    Scene scene2 = new Scene(anchorLayout);
                    mainApplication.getMainScreen().setScene(scene2);
                    msc.setMainApp(mainApplication);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong Credentials");
                alert.setHeaderText("Wrong Credentials");
                alert.setContentText("Wrong password. Please try again.");
                alert.showAndWait();
                usernameInput.clear();
                passwordInput.clear();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Credentials");
            alert.setHeaderText("Wrong Credentials");
            alert.setContentText("Username does not exist. Please try again.");
            alert.showAndWait();
            usernameInput.clear();
            passwordInput.clear();
        }
    }

    @FXML
    protected void handleCancelButtonAction() throws IOException {
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

