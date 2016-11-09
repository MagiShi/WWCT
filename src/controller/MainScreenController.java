package src.controller;

/**
 * Created by Maggie on 9/20/2016.
 */

import src.fxapp.WaterzMainFXApplication;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;

/**
 * The controller for the root/main window
 *
 */
public class MainScreenController {

    /**
     * allow for calling back to the main application code if necessary
     * @param main   the reference to the FX Application instance
     * */
    public void setMainApp(WaterzMainFXApplication main) {
        /* reference back to mainApplication if needed */
        WaterzMainFXApplication mainApplication = main;
    }

    /**
     * Close menu item event handler
     */
    @FXML
    private void handleCloseMenu() {
        System.exit(0);

    }

    /**
     * About menu item event handler
     */
    @FXML
    private void handleAboutMenu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WaterZ");
        alert.setHeaderText("Login Screen");
        alert.setContentText("Please login");

        alert.showAndWait();

    }

}

