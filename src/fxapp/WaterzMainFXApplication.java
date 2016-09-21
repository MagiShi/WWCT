package src.fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        FXMLLoader loader = new FXMLLoader();

    }

    public static void main(String[] args) {
        launch(args);
    }


    }
