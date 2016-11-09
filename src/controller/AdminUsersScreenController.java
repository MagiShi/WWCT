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
import src.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AdminUsersScreenController {

    private WaterzMainFXApplication mainApplication;

    @FXML private ListView<String> usersList;
    @FXML private TextField userIDField;
    @FXML private Button deleteUserButton;
    @FXML private Button unbanUserButton;
    @FXML private Button banUserButton;
    @FXML private Button backButton;
    @FXML private Button logoutButton;



    private final List<String> allUsers = new ArrayList<>();

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
            banUser(username);
        }
    }

    private void banUser(String username) {
        boolean found = false;
        for (int i = 0; i < allUsers.size(); i++) {
            String user = allUsers.get(i);
            String[] userData = user.split(",");
            if (userData[1].equals(username)) {
                allUsers.remove(i);
                userData[6] = "BANNED";
                user = userData[0] + "," + userData[1] + "," + userData[2] + "," + userData[3]
                        + "," + userData[4] + "," + userData[5] + "," + userData[6];
                allUsers.add(i, user);
                found = true;
            }
        }
        if (found) {
            rewriteDatabase();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User ID not found");
            alert.setHeaderText("The User ID was not found.");
            alert.showAndWait();
            userIDField.clear();
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
            unbanUser(username);
        }
    }

    private void unbanUser(String username) {
        boolean found = false;
        for (int i = 0; i < allUsers.size(); i++) {
            String user = allUsers.get(i);
            String[] userData = user.split(",");
            if (userData[1].equals(username)) {
                allUsers.remove(i);
                userData[6] = "Not Banned";
                user = userData[0] + "," + userData[1] + "," + userData[2] + "," + userData[3]
                        + "," + userData[4] + "," + userData[5] + "," + userData[6];
                allUsers.add(i, user);
                found = true;
            }
        }
        if (found) {
            rewriteDatabase();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User ID not found");
            alert.setHeaderText("The User ID was not found.");
            alert.showAndWait();
            userIDField.clear();
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
            deleteUser(username);
        }
    }

    private void deleteUser(String username) {
        boolean found = false;
        for (int i = 0; i < allUsers.size(); i++) {
            String user = allUsers.get(i);
            String[] userData = user.split(",");
            if (userData[1].equals(username)) {
                allUsers.remove(i);
                i--;
                found = true;
            }
        }
        if (found) {
            rewriteDatabase();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User ID not found");
            alert.setHeaderText("The User ID was not found.");
            alert.showAndWait();
            userIDField.clear();
        }
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

    private void rewriteDatabase() {
        BufferedWriter writer = null;
        try {
            File newFile = new File("database.csv");
            writer = new BufferedWriter(new FileWriter(newFile));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ioe) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Rewrite Error");
                alert.setHeaderText("Error while flushing: " + ioe.toString());
                alert.showAndWait();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("database.csv", true);
            while (!allUsers.isEmpty()) {
                fileWriter.append(allUsers.remove(0));
                fileWriter.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException ioe) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Rewrite Error");
                alert.setHeaderText("Error while flushing: " + ioe.toString());
                alert.showAndWait();
            }
        }
        loadDatabase();
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
