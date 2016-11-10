package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import src.model.Registration;
import src.model.User;
import src.model.UserType;
import src.model.Registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;


public class RegisterScreenController {
    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

    @FXML private Button registerButton;
    @FXML private Button cancelButton;
    @FXML private TextField nameInput;
    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private PasswordField passwordInput2;
    @FXML private ComboBox<UserType> userTypeInput;

    ObservableList<UserType> observableList = FXCollections.observableArrayList(UserType.values());

    private BorderPane borderLayout;
    private AnchorPane anchorLayout;

    @FXML
    protected void handleRegisterButtonAction() {
        String userInputUsername = usernameInput.getText();
        String userInputPassword = passwordInput.getText();
        String matchPassword = passwordInput2.getText();
        UserType userInputUserType = userTypeInput.getValue();
        String userNameInput = nameInput.getText();


        Registration reg = new Registration(userInputUsername, userInputPassword,
                matchPassword, userInputUserType, userNameInput);
        User currentUser = reg.register(passwordInput, passwordInput2, usernameInput);
        if (currentUser != null) {
            changeScreen(currentUser, userInputUserType);
        }
    }

    private void changeScreen(User currentUser, UserType userInputUserType) {
        switch (userInputUserType.toString()) {
            case "WORKER":
                changeToWorkerScreen(currentUser);
                break;
            case "USER":
                changeToUserScreen(currentUser);
                break;
            case "MANAGER":
                changeToManagerScreen(currentUser);
                break;
            case "ADMINISTRATOR":
                changeToAdminScreen(currentUser);
                break;
        }
    }

    private void changeToUserScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UserMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            UserMapScreenController controller = fxmlLoader.getController();

            controller.setApp(mainApplication);
            controller.setState(mainApplication.getMainScreen());
            controller.setUpMapView(mainApplication.getMainScreen());

            controller.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToWorkerScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            WorkerMapScreenController controller = fxmlLoader.getController();

            controller.setApp(mainApplication);
            controller.setState(mainApplication.getMainScreen());
            controller.setUpMapView(mainApplication.getMainScreen());

            controller.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToManagerScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerMapScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            ManagerMapScreenController controller = fxmlLoader.getController();

            controller.setApp(mainApplication);
            controller.setState(mainApplication.getMainScreen());
            controller.setUpMapView(mainApplication.getMainScreen());

            controller.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeToAdminScreen(User currentUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AdminMainScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            AdminMainScreenController controller = fxmlLoader.getController();

            controller.setUser(currentUser);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);

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
     * Initializes the controller class. This method is automatically called
     * after the constructor and
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        userTypeInput.getItems().removeAll(userTypeInput.getItems());
        userTypeInput.getItems().addAll(UserType.ADMIN, UserType.MANAGER, UserType.WORKER, UserType.USER);
        userTypeInput.getSelectionModel().select(UserType.USER);
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
