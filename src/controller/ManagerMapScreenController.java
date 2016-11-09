package src.controller;

import com.lynden.gmapsfx.javascript.object.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.fxapp.WaterzMainFXApplication;
import src.model.User;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;

import javafx.fxml.Initializable;

import src.model.Facade;
import src.model.Location;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public final class ManagerMapScreenController implements Initializable, MapComponentInitializedListener {

    /** a link back to the main application class */
    private WaterzMainFXApplication mainApplication;

    private AnchorPane anchorLayout;

    private User currentUser;

    private String username;
    //private ArrayList<WaterSource> water = new ArrayList<WaterSource>();

    Location currLoc;


    /* references to the widgets in the fxml file */

    @FXML
    private Button logoutButton;
    @FXML
    private Button viewReportsButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button submitButton;
    @FXML
    private BorderPane mapViewer;
    @FXML
    private Label sourceSelectedLabel;

    /** a gui view provided by the GMapFX library */
    private GoogleMapView mapView;
    /** the actual javascript interface for the google map itself */
    private GoogleMap map;
    /** a reference back to the main application object in case we need something or to transition to another window */
    private WaterzMainFXApplication theApp;
    /**remember stage for dialogs */
    private Stage mainStage;

    public ManagerMapScreenController(WaterzMainFXApplication app, Stage stage) {
        theApp = app;
        mainStage = stage;
        setUpMapView(stage);
    }

    public ManagerMapScreenController() {
    }

    protected void setApp(WaterzMainFXApplication app) {
        theApp = app;
    }

    protected void setState (Stage stage) {
        mainStage = stage;
    }

    protected void setLocation(Location loc) {currLoc = loc; }

    @FXML protected void handleSubmitButtonAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/waterSourceReportScreen.fxml"));

            anchorLayout = fxmlLoader.load();
            waterSourceReportScreenController controller = fxmlLoader.getController();
            controller.setUser(currentUser);
            Facade fac = Facade.getInstance();
            controller.setFacade(fac);
            Scene scene2 = new Scene(anchorLayout);
            mainApplication.getMainScreen().setScene(scene2);

            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleViewReportDetailButtonAction() {
        if (currLoc != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerSourceDetailScreen.fxml"));

                anchorLayout = fxmlLoader.load();
                ManagerSourceDetailScreenController controller = fxmlLoader.getController();
                controller.setUser(currentUser);
                Scene scene2 = new Scene(anchorLayout);
                mainApplication.getMainScreen().setScene(scene2);
                controller.setCurrentSource(currLoc);
                //controller.setCurrentSource(water.get(0));
                controller.setMainApp(mainApplication);

            } catch (Exception e) {
                e.printStackTrace();
            }
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ManagerProfile.fxml"));

            anchorLayout = fxmlLoader.load();
            ManagerProfileScreenController controller = fxmlLoader.getController();
            Scene scene2 = new Scene(anchorLayout);
            controller.setUser(currentUser);
            mainApplication.getMainScreen().setScene(scene2);
            controller.setMainApp(mainApplication);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    /**
     * Construct the google map, set up the different parts of the layout
     *
     * @param stage the stage to put the map scene into
     */
    protected void setUpMapView(Stage stage) {
        //construct the view
        mapView = new GoogleMapView();
        //we cannot do anything until the map is constructed, so we need a callback
        //this is provided by the listener.  this class implements the MapComponentInitializedListener
        //interface
        mapView.addMapInializedListener(this);
        //put the map into the center of the border layout
        mapViewer.setCenter(mapView);
        //put the map into the scene
        Scene scene = new Scene(mapViewer);
        stage.setScene(scene);
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

        /** now we communicate with the model to get all the locations for markers */

        Facade fac = Facade.getInstance();
        List<Location> locations = fac.getLocations();

        //create pins
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
                        currLoc = l;
                        sourceSelectedLabel.setText("Source Selected");
                        window.open(map, marker);});
            map.addMarker(marker);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        boolean alreadyExists = new File("sourceReports.csv").exists();
//        if (alreadyExists) {
//            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
//                String line;
//                while (((line = br.readLine()) != null)) {
//                    String[] data = line.split(",");
//                    WaterSource source = new WaterSource(data[1], data[2], data[3], Double.valueOf(data[4]), Double.valueOf(data[5]), data[6], data[7]);
//                    //water.add(source);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Manager called");
    }
}