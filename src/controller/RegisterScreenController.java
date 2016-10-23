package src.controller;

/**
 * Created by Ji Won Lee on 9/30/2016.
 */
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import src.model.UserType;

import java.io.*;

public class RegisterScreenController {
    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    /* references to the widgets in the fxml file */

    @FXML private Button registerButton;
    @FXML private Button cancelButton;
    @FXML private TextField nameInput;
    @FXML private TextField usernameInput;
    @FXML private TextField passwordInput;
    @FXML private TextField passwordInput2;
    @FXML private ComboBox<UserType> userTypeInput;

    ObservableList<UserType> observableList = FXCollections.observableArrayList(UserType.values());

    private BorderPane borderLayout;
    private AnchorPane anchorLayout;


    @FXML
    protected void handleRegisterButtonAction() throws IOException{
        String userInputUsername = usernameInput.getText();
        String userInputPassword = passwordInput.getText();
        String userInputPassword2 = passwordInput2.getText();
        String userNameInput = nameInput.getText();
        UserType userInputUserType = userTypeInput.getValue();
        UserManager uM = UserManager.getInstance();
        if (uM.userExists(userInputUsername)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Taken");
            alert.setHeaderText("Username Taken");
            alert.setContentText("Account with this username already exists. Please enter a different username.");
            alert.showAndWait();
            usernameInput.clear();
        } else {
            if (uM.passwordsMatch(userInputPassword,userInputPassword2)) {
                uM.addUser(userInputUsername, userInputPassword, userNameInput, userInputUserType);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

                anchorLayout = fxmlLoader.load();
                MapScreenController msc = fxmlLoader.getController();

                Scene scene2 = new Scene(anchorLayout);
                mainApplication.getMainScreen().setScene(scene2);


                msc.setUser(uM.getCurrentUser());
                msc.setMainApp(mainApplication);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password does not match");
                alert.setHeaderText("Password does not match");
                alert.setContentText("Input for password and confirm password does not match.");
                alert.showAndWait();
                passwordInput.clear();
                passwordInput2.clear();
            }
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
