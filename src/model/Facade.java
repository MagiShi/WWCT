package src.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robertwaters on 10/7/16.
 */
public class Facade {

    private static final Facade instance = new Facade();
    public static Facade getInstance() { return instance; }

    //this is our simple model
    private List<Location> locations = new ArrayList<>();



    private Facade() {
        //read data from source report;
        boolean alreadyExists = new File("sourceReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                String line = "";
                String[] linebroken;
                int i = 0;
                while (((line = br.readLine()) != null)) {
                    linebroken = line.split(",");
                    Location l = new Location(Double.valueOf(linebroken[4]),
                            Double.valueOf(linebroken[5]),
                            "Marker " + i,
                            "<h2>ReportNumber "  + linebroken[0] +
                                    "</h2> <br> Reporter: " + linebroken[1] +
                                    "<br> Date: " + linebroken[2] +
                                    "<br> Water Type: " + linebroken[6] +
                                    "<br> Water Condition: " + linebroken[7]);
                    locations.add(l);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Location> getLocations() { return locations; }

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
        locations.add(new Location(34.043, -88.043, "New Marker", "Some new data"));
    }
}
