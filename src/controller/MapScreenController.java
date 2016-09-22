package src.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import src.fxapp.WaterzMainFXApplication;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.control.*;


public class MapScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

        @FXML
        private Button logoutButton;

        @FXML protected void handleLogoutButtonAction() {
            //logout stuff :)
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
    }
}