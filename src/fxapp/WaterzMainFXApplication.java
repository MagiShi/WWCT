package src.fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.controller.MainScreenController;
import src.controller.LoginScreenController;
import src.controller.MapScreenController;
import src.controller.WelcomeScreenController;

import java.io.IOException;

public class WaterzMainFXApplication extends Application {

    private Stage mainScreen;
    private Stage welcomeScene;
    private Scene loginScene;
    private Scene mapScene;

    private BorderPane borderLayout;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        initRootLayout(mainScreen);
        showWelcome();
    }

    public Stage getMainScreen() {
        return mainScreen;
    }



    private void initRootLayout(Stage mainScreen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WaterzMainFXApplication.class.getResource("../view/MainScreen.fxml"));
            borderLayout = loader.load();
            MainScreenController controller = loader.getController();
            controller.setMainApp(this);

            mainScreen.setTitle("To Change Later");

            Scene scene = new Scene(borderLayout);
            mainScreen.setScene(scene);
            mainScreen.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showWelcome() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WaterzMainFXApplication.class.getResource("../view/WelcomeScreen.fxml"));
            AnchorPane welcomeScreen = loader.load();

            borderLayout.setCenter(welcomeScreen);

            WelcomeScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
