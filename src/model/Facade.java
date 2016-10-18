package src.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Facade {

    private static final Facade instance = new Facade();
    public static Facade getInstance() { return instance; }

    //this is our simple model
    private List<WaterSource> locations = new ArrayList<>();



    private Facade() {
        //create List of existing WaterSources from data.
        try {
            Scanner scan = new Scanner(new File(getClass().getResource("/sourceReports.csv").toURI()));
            while (scan.hasNext())  {
                String sourceData = scan.next();
                String[] dataList = sourceData.split(",");
                String loc = dataList[3];
                boolean exists = false;
                for (WaterSource existing: locations) {
                    if (existing.getLocation().equals(loc)) {
                        exists = true;
                    }
                }

                if (!exists) {

                    String name = dataList[1];
                    String date = dataList[2];
                    String type = dataList[4];
                    String condition = dataList[5];

                    WaterSource ws = new WaterSource(name, date, loc, type, condition);
                    locations.add(ws);
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //make up some dummy data
//        for (int i = 0; i < 10; ++i) {
//            Location l = new Location(34.0 + (i/10.0), -88.0 - (i/10.0), "Marker " + i, "<h2>Test "  + i + "</h2> <br> some data");
//            locations.add(l);
//        }
    }

    public List<WaterSource> getLocations() { return locations; }

    public void saveModelToText(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.saveToText(file);
    }

    public void loadModelFromText(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.loadFromText(file);
    }

    public void saveModelToBinary(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.saveToBinary(file);
    }

    public void loadModelFromBinary(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.loadFromBinary(file);
    }

    public void loadModelFromJson(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.loadFromJsonfile(file);
    }

    public void saveModelToJson(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.saveToJson(file);
    }

    public void addLocations() {
        locations.add(new WaterSource("new user", "new date", "new loc", "new type", "new condition"));
    }
}
