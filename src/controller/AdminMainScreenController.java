package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;

public class AdminMainScreenController {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private User currentUser;


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
        //logout stuff :)
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
    @FXML protected void handleLogButtonAction() {

    }

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