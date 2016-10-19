package src.fxapp;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import src.controller.WaterAvailabilityMapScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.model.Facade;

public class MainFXApplication extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Map and Persistence Demo");
        /* Using the controller to set up the view because honestly, I could not
           get the .fxml way to work. */
        WaterAvailabilityMapScreenController controller = new WaterAvailabilityMapScreenController(this, stage);

        mainStage = stage;

        //at this point the controller has made the window, setup the map and set it as the scene for the stage
        stage.show();

    }

    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }

    /**
     * dummy method to simulate a callback from the map view
     */
    public void closeMapView() {
        Facade fc = Facade.getInstance();
        fc.addLocations();
        WaterAvailabilityMapScreenController controller = new WaterAvailabilityMapScreenController(this, mainStage);


    }

}
