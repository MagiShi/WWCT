package src.controller;

/**
 * Created by Ji Won Lee on 9/30/2016.
 */
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
    @FXML private ComboBox userTypeInput;

    private BorderPane borderLayout;
    private AnchorPane anchorLayout;


    @FXML
    protected void handleRegisterButtonAction() throws IOException{
        String userInputUsername = usernameInput.getText();
        String userInputPassword = passwordInput.getText();
        String userInputUserType = userTypeInput.getValue().toString();
        boolean usernameOriginal = true;
        //check if username exists
        boolean alreadyExists = new File("database.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
                String line = "";
                while (usernameOriginal && ((line = br.readLine()) != null)) {
                    String[] info = line.split(",");
                    if (info[1].equals(userInputUsername)) {
                        usernameOriginal = false;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File newFile = new File("database.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        }
        if (usernameOriginal) {
            try {
                if (passwordInput2.getText().equals(userInputPassword)) {
                    //Store userinfo into csv file//
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("database.csv");
                        fileWriter.append(nameInput.getText());
                        fileWriter.append(",");
                        fileWriter.append(userInputUsername);
                        fileWriter.append(",");
                        fileWriter.append(userInputPassword);
                        fileWriter.append(",");
                        fileWriter.append(userInputUserType);
                        fileWriter.append("\n");
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        try {
                            fileWriter.flush();
                            fileWriter.close();
                        }  catch (IOException ioe) {
                            System.out.println("Error while flushing");
                            ioe.printStackTrace();
                        }
                    }


                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MapScreen.fxml"));

                    anchorLayout = fxmlLoader.load();
                    MapScreenController msc = fxmlLoader.getController();

                    Scene scene2 = new Scene(anchorLayout);
                    mainApplication.getMainScreen().setScene(scene2);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Taken");
            alert.setHeaderText("Username Taken");
            alert.setContentText("Account with this username already exists. Please enter a different username.");
            alert.showAndWait();
            usernameInput.clear();
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
        userTypeInput.getItems().addAll("Administrator", "Manager", "Worker");
        userTypeInput.getSelectionModel().select("Worker");
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
