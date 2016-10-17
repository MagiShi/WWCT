package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.fxapp.WaterzMainFXApplication;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import src.model.User;
import src.model.WaterSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import model.Facade;
import model.Location;

import netscape.javascript.JSObject;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MapScreenController implements Initializable, MapComponentInitializedListener {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;
    private AnchorPane anchorLayout;
    private User currentUser;

    private String username;
    private ArrayList<WaterSource> water = new ArrayList<WaterSource>();

    /** a gui view provided by the GMapFX library */
    private GoogleMapView mapView;
    /** the actual javascript interface for the google map itself */
    private GoogleMap map;


    /* references to the widgets in the fxml file */

    @FXML
    private Button logoutButton;
    @FXML
    private Button viewReportsButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button submitButton;


    @FXML protected void handleSubmitButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/waterSourceReportScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            waterSourceReportScreenController controller = fxmlLoader.getController();

            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewReportDetailButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/SourceDetailScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            SourceDetailScreenController controller = fxmlLoader.getController();
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setCurrentSource(water.get(3));
            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleLogoutButtonAction() {
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

    @FXML protected void handleProfileButtonAction() {
        //logout stuff :)
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Profile.fxml"));

            anchorLayout = fxmlLoader.load();
            ProfileScreenController controller = fxmlLoader.getController();
            Scene scene2 = new Scene(anchorLayout);
            controller.setUser(currentUser);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mapInitialized() {
        mapView.addMapInializedListener(this);

        //Set the initial properties of the map

        MapOptions options = new MapOptions();

        //set up the center location for the map
        LatLong center = new LatLong(34, -88);

        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);


        /** now we communciate with the model to get all the locations for markers */
        Facade fc = Facade.getInstance();
        List<Location> locations = fc.getLocations();

        for (Location l: locations) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(l.getLatitude(), l.getLongitude());

            markerOptions.position( loc )
                    .visible(Boolean.TRUE)
                    .title(l.getTitle());

            Marker marker = new Marker( markerOptions );

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(l.getDescription() );

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);});

            map.addMarker(marker);

        }

        //  borderLayout.setCenter(mapView);

    }


        /**
         * Initializes the controller class. This method is automatically called
         * after the constructor and
         * after the fxml file has been loaded.
         */
        @FXML
        private void initialize() {
            boolean alreadyExists = new File("sourceReports.csv").exists();
            if (alreadyExists) {
                try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                    String line = "";
                    while (((line = br.readLine()) != null)) {
                        String[] data = line.split(",");
                        WaterSource source = new WaterSource(data[1], data[2], data[3], data[4], data[5]);
                        water.add(source);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
        public void setUser(User newUser) {
            currentUser = newUser;
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