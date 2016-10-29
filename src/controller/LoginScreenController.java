package src.controller;

/**
 * Created by Dain on 9/20/2016.
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import src.fxapp.WaterzMainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private String username;
    private String password;

    String type = "";
    User currentUser;

    WorkerMapScreenController mapController = new WorkerMapScreenController();
    ManagerMapScreenController mapControllerManager = new ManagerMapScreenController();




    @FXML protected void handleLoginButtonAction() throws IOException{


        try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
            String line = "";
            String[] info = null;
            boolean usernameFound = false;
            while (!usernameFound && ((line = br.readLine()) != null)) {
                info = line.split(",");
                if (info[1].equals(usernameInput.getText())) {
                    usernameFound = true;
                    username = info[1];
                }
            }
            if (usernameFound == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong Credentials");
                alert.setHeaderText("Wrong Credentials");
                alert.setContentText("Username does not exist. Please try again.");
                alert.showAndWait();
                usernameInput.clear();
                passwordInput.clear();
            } else if (info[2].equals(passwordInput.getText())){
                if (info[2].equals(passwordInput.getText()) && info[3].equals("WORKER")) {
                    password = info[2];
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/WorkerMapScreen.fxml"));

                        anchorLayout = fxmlLoader.load();
                        WorkerMapScreenController msc = fxmlLoader.getController();

                        msc.setApp(mainApplication);
                        msc.setState(mainApplication.getMainScreen());
                        msc.setUpMapView(mainApplication.getMainScreen());

                        User currentUser = new User(info[1], info[2], info[0], info[3], info[4], info[5], info[6]);
                        msc.setUser(currentUser);
                        Scene scene2 = new Scene(anchorLayout);
                        mainApplication.getMainScreen().setScene(scene2);
                        msc.setMainApp(mainApplication);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(info[3].equals("USER")) {
                    password = info[2];
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UserMapScreen.fxml"));

                        anchorLayout = fxmlLoader.load();
                        UserMapScreenController msc = fxmlLoader.getController();

                        msc.setApp(mainApplication);
                        msc.setState(mainApplication.getMainScreen());
                        msc.setUpMapView(mainApplication.getMainScreen());

                        User currentUser = new User(info[1], info[2], info[0], info[3], info[4], info[5], info[6]);
                        msc.setUser(currentUser);
                        Scene scene2 = new Scene(anchorLayout);
                        mainApplication.getMainScreen().setScene(scene2);
                        msc.setMainApp(mainApplication);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (info[3].equals("MANAGER")){
                    password = info[2];
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerMapScreen.fxml"));

                        anchorLayout = fxmlLoader.load();
                        ManagerMapScreenController msc = fxmlLoader.getController();

                        msc.setApp(mainApplication);
                        msc.setState(mainApplication.getMainScreen());
                        msc.setUpMapView(mainApplication.getMainScreen());

                        User currentUser = new User(info[1], info[2], info[0], info[3], info[4], info[5], info[6]);
                        msc.setUser(currentUser);
                        Scene scene2 = new Scene(anchorLayout);
                        mainApplication.getMainScreen().setScene(scene2);
                        msc.setMainApp(mainApplication);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Wrong Credentials");
                    alert.setHeaderText("Wrong Credentials");
                    alert.setContentText("Wrong password. Please try again.");
                    alert.showAndWait();
                    usernameInput.clear();
                    passwordInput.clear();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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
        //load animation here?
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

