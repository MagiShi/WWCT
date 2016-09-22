package src.fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.controller.MainScreenController;
import src.controller.LoginScreenController;

import java.io.IOException;

public class WaterzMainFXApplication extends Application {

    private Stage mainScreen;
    private Scene loginScene;
    private Scene mapScene;

    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        initRootLayout(mainScreen);
        showLogin(mainScreen);


    }

    public Stage getMainScreen() {
        return mainScreen;
    }

    private void initRootLayout(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WaterzMainFXApplication.class.getResource("../view/MainScreen.fxml"));
            rootLayout = loader.load();

            MainScreenController controller = loader.getController();
            controller.setMainApp(this);

            mainScreen.setTitle("To Change Later");

            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showLogin(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WaterzMainFXApplication.class.getResource("../view/LoginScreen.fxml"));
            AnchorPane loginScreen = loader.load();

            rootLayout.setCenter(loginScreen);

            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
