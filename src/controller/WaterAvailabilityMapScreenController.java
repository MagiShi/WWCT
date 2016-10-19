package src.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;

import src.fxapp.MainFXApplication;
import src.fxapp.WaterzMainFXApplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import netscape.javascript.JSObject;
import src.model.Facade;
import src.model.Location;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Ji Won Lee on 10/18/2016.
 */
public class WaterAvailabilityMapScreenController implements Initializable, MapComponentInitializedListener {
    /** a gui view provided by the GMapFX library */
    private GoogleMapView mapView;

    /** the actual javascript interface for the google map itself */
    private GoogleMap map;

    /** a reference back to the main application object in case we need something or to transition to another window */
    private MainFXApplication theApp;

    /**remember stage for dialogs */
    private Stage mainStage;

    /**
     * Make a new constructor
     * @param app  reference to the FX application
     * @param stage the stage we want this map to be displayed in
     */
    public WaterAvailabilityMapScreenController(MainFXApplication app, Stage stage) {
        theApp = app;
        mainStage = stage;
        setUpMapView(stage);
    }

    /**
     * Construct the google map, set up the different parts of the layout
     *
     * @param stage the stage to put the map scene into
     */
    private void setUpMapView(Stage stage) {
        //construct the view
        mapView = new GoogleMapView();
        //we cannot do anything until the map is constructed, so we need a callback
        //this is provided by the listener.  this class implements the MapComponentInitializedListener
        //interface
        mapView.addMapInializedListener(this);

        //Create the top level layout
        BorderPane bp = new BorderPane();

        //put the menu into the top of the border layout
        bp.setTop(makeMenuBar());


        //put the map into the center of the border layout
        bp.setCenter(mapView);

        //put the map into the scene
        Scene scene = new Scene(bp);
        stage.setScene(scene);
    }

    /**
     * constructs the menubar and all the subitems
     *
     * @return the menubar after it is constructed
     */
    private Node makeMenuBar() {
        MenuBar mb = new MenuBar();
        // --- Menu File
        Menu menuFile = new Menu("File");
        addFileOptions(menuFile);

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        addEditOptions(menuEdit);

        // --- Menu View
        Menu menuView = new Menu("View");
        addViewOptions(menuView);

        mb.getMenus().addAll(menuFile, menuEdit, menuView);

        return mb;
    }

    /**
     * helper to add all the submenu items for the View menu
     *
     * @param menuView  a reference to the top level View menu
     */
    private void addViewOptions(Menu menuView) {

    }

    /**
     * helper to add all the submenu items for the Edit menu
     *
     * @param menuEdit a reference to the top level Edit menu
     */
    private void addEditOptions(Menu menuEdit) {

    }

    /**
     * helper to construct the file menu
     *
     * @param menuFile reference to the top level File menu
     */
    private void addFileOptions(Menu menuFile) {

        MenuItem openText = new MenuItem("Open Text");
        openText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Open Text File");
                File file  = fc.showOpenDialog(mainStage);
                if (file != null)
                    Facade.getInstance().loadModelFromText(file);
            }
        });

        MenuItem openBinary = new MenuItem("Open Binary");
        openBinary.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Open Binary File");
                File file  = fc.showOpenDialog(mainStage);
                if (file != null)
                    Facade.getInstance().loadModelFromBinary(file);
            }
        });

        MenuItem openJson = new MenuItem("Open JSON");
        openJson.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Open JSON File");
                File file  = fc.showOpenDialog(mainStage);
                if (file != null)
                    Facade.getInstance().loadModelFromJson(file);
            }
        });

        MenuItem saveText = new MenuItem("Save Text");
        saveText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save Text File");
                File file  = fc.showSaveDialog(mainStage);
                if (file != null)
                    Facade.getInstance().saveModelToText(file);
            }
        });

        MenuItem saveBinary = new MenuItem("Save Binary");
        saveBinary.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save Binary File");
                File file  = fc.showSaveDialog(mainStage);
                if (file != null)
                    Facade.getInstance().saveModelToBinary(file);
            }
        });

        MenuItem saveJson = new MenuItem("Save JSON");
        saveJson.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save JSON File");
                File file  = fc.showSaveDialog(mainStage);
                if (file != null)
                    Facade.getInstance().saveModelToJson(file);
            }
        });

        MenuItem close = new MenuItem("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                theApp.closeMapView();
            }
        });


        menuFile.getItems().addAll(openText, openBinary, openJson, new SeparatorMenuItem(),
                saveText, saveBinary, saveJson, new SeparatorMenuItem(),
                close);

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

        for (Location l : locations) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(l.getLatitude(), l.getLongitude());

            markerOptions.position(loc)
                    .visible(Boolean.TRUE)
                    .title(l.getTitle());

            Marker marker = new Marker(markerOptions);

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(l.getDescription());

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);

        }

    }   //  borderLayout.setCenter(mapView);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
