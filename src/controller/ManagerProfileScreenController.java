package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;

public class ManagerProfileScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

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

    @FXML
    private Label username;
    @FXML
    private Label userType;
    @FXML
    private Label currentBanStatus;


    private User currentUser;

    public ManagerProfileScreenController() {
    }

    public void setUser(User newUser) {
        currentUser = newUser;
        username.setText(currentUser.getUserID());
        userType.setText(currentUser.getType());
        currentBanStatus.setText(currentUser.getStatus());
        currentName.setText(currentUser.getName());
        currentAddress.setText(currentUser.getAddress());
        currentEmail.setText(currentUser.getEmail());
    }

    @FXML protected void handleBackButtonAction() {
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerMapScreen.fxml"));

            AnchorPane anchorLayout = fxmlLoader.load();
                ManagerMapScreenController msc = fxmlLoader.getController();

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

    @FXML protected void handleSaveName() {
        currentName.setText(newName.getText());
        newName.clear();

    }

    @FXML protected void handleSaveEmail() {
        currentEmail.setText(newEmail.getText());
        newEmail.clear();

    }

    @FXML protected void handleSaveAddress() {
        currentAddress.setText(newAddress.getText());
        newAddress.clear();
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
