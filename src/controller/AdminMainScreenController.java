package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;

/**
 * Controller linked to the Main Screen for Admin accounts.
 * It handles what happens when actions are taken for the view
 * i.e. what happens when buttons are clicked etc.
 */
public class AdminMainScreenController {

    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private User currentUser;

    @FXML private Button logoutButton;
    @FXML private Button profileButton;
    @FXML private Button logButton;
    @FXML private Button usersButton;


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

    @FXML protected void handleUsersButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AdminUsersScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            AdminUsersScreenController controller = fxmlLoader.getController();
            controller.setUser(currentUser);

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleProfileButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AdminProfile.fxml"));

            anchorLayout = fxmlLoader.load();
            AdminProfileScreenController controller = fxmlLoader.getController();
            Scene scene2 = new Scene(anchorLayout);
            controller.setUser(currentUser);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @FXML protected void handleLogButtonAction() {
//
//    }


    /**
     * Lets the app know which user is currently logged on.
     * This is important for displaying user info in the profile screen,
     * but the method is needed for all screens
     * because the current user needs to be continuously "held onto."
     *
     * @param newUser the User instance holding the current User's data
     */
    public void setUser(User newUser) {
        currentUser = newUser;
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