package src.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import src.model.User;

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
    @FXML private PasswordField passwordInput;

    private BorderPane borderLayout;
    private AnchorPane anchorLayout;

    protected String type = "";
    protected User currentUser;

    WorkerMapScreenController mapController = new WorkerMapScreenController();
    ManagerMapScreenController mapControllerManager = new ManagerMapScreenController();




    @FXML protected void handleLoginButtonAction() {
        try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
            String line;
            String[] info = null;
            boolean usernameFound = false;
            line = br.readLine();
            while (!usernameFound && (line != null)) {
                info = line.split(",");
                if (info[1].equals(usernameInput.getText())) {
                    usernameFound = true;
                }
                line = br.readLine();
            }
            if (!usernameFound) {
                soundAlert();
            } else if (info[2].equals(passwordInput.getText())){
                login(info);
            } else {
                soundAlert();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login(String[] info) {
        User currentUser = new User(info[1], info[2], info[0], info[3], info[4], info[5], info[6]);
        if (info[2].equals(passwordInput.getText()) && "WORKER".equals(info[3])) {
            changeToWorkerScreen(currentUser);
        } else if("USER".equals(info[3])) {
            changeToUserScreen(currentUser);
        } else if ("MANAGER".equals(info[3])){
            changeToManagerScreen(currentUser);
        } else if ("ADMINISTRATOR".equals(info[3])){
            changeToAdminScreen(currentUser);
        } else {
            soundAlert();
        }
    }

    private void soundAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong Credentials");
        alert.setHeaderText("Wrong Credentials");
        alert.setContentText("Username does not exist. Please try again.");
        alert.showAndWait();
        usernameInput.clear();
        passwordInput.clear();
    }

    private void changeToUserScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UserMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            UserMapScreenController msc = fxmlLoader.getController();

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

    private void changeToWorkerScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            WorkerMapScreenController msc = fxmlLoader.getController();

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

    private void changeToManagerScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
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

    private void changeToAdminScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AdminMainScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            AdminMainScreenController msc = fxmlLoader.getController();

            msc.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            msc.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleCancelButtonAction() {
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
     * Setup the main application link so we can call methods there
     *
     * @param mainFXApplication  a reference (link) to our main class
     */
    public void setMainApp(WaterzMainFXApplication mainFXApplication) {
        mainApplication = mainFXApplication;

    }
}

