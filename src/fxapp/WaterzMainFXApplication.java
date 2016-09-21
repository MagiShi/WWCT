package src.fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WaterzMainFXApplication extends Application {

    private Stage mainScreen;

    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        initRootLayout(mainScreen);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }


    }
