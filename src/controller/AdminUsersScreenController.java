package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import src.fxapp.WaterzMainFXApplication;
import src.model.StatusChange;
import src.model.User;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

public class AdminUsersScreenController {

    private WaterzMainFXApplication mainApplication;

    @FXML private ListView<String> usersList;
    @FXML private TextField userIDField;
    @FXML private Button deleteUserButton;
    @FXML private Button unbanUserButton;
    @FXML private Button banUserButton;
    @FXML private Button backButton;
    @FXML private Button logoutButton;



    private final Collection<String> allUsers = new ArrayList<>();
    private StatusChange sc;
    private User currentUser;

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




    @FXML protected void backButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AdminMainScreen.fxml"));

            AnchorPane anchorLayout = fxmlLoader.load();
            AdminMainScreenController msc = fxmlLoader.getController();

            msc.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
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
        loadDatabase();
    }

    private void loadDatabase() {
        allUsers.clear();
        usersList.getItems().clear();
        boolean alreadyExists = new File("database.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
                String line = br.readLine();
                while (line != null) {
                    usersList.getItems().add(line);
                    allUsers.add(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML protected void handleBanUserButtonAction() {
        String username = userIDField.getText();
        if ("".equals(username)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No ID Entered");
            alert.setHeaderText("Please Enter an ID");
            alert.showAndWait();
        } else if (username.equals(currentUser.getUserID())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Ban That User");
            alert.setContentText("You cannot ban yourself.");
            alert.showAndWait();
        } else {
            sc = new StatusChange(username);
            if (!sc.changeBanStatus("ban")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User ID not found");
                alert.setHeaderText("The User ID was not found.");
                alert.showAndWait();
            }
            userIDField.clear();
            loadDatabase();
        }
    }


    @FXML protected void handleUnbanUserButtonAction() {
        String username = userIDField.getText();
        if ("".equals(username)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No ID Entered");
            alert.setHeaderText("Please Enter an ID");
            alert.showAndWait();
        } else if (username.equals(currentUser.getUserID())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Unban That User");
            alert.setContentText("You cannot unban yourself.");
            alert.showAndWait();
        } else {
            sc = new StatusChange(username);
            if (!sc.changeBanStatus("unban")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User ID not found");
                alert.setHeaderText("The User ID was not found.");
                alert.showAndWait();
            }
            userIDField.clear();
            loadDatabase();
        }
    }


    @FXML protected void handleDeleteUserButtonAction(){
        String username = userIDField.getText();
        if ("".equals(username)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No ID Entered");
            alert.setHeaderText("Please Enter an ID");
            alert.showAndWait();
        } else if (username.equals(currentUser.getUserID())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Delete That User");
            alert.setContentText("You cannot delete yourself.");
            alert.showAndWait();
        } else {
            sc = new StatusChange(username);
            if (!sc.deleteUser()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User ID not found");
                alert.setHeaderText("The User ID was not found.");
                alert.showAndWait();
            }
            userIDField.clear();
            loadDatabase();
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
