package src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Facade for the architectural facade pattern
 */
public final class Facade {

    private static final Facade instance = new Facade();

    /**
     * Getter method for the "instance"
     * @return a new Facade object
     */
    public static Facade getInstance() { return instance; }

    //this is our simple model
    private final List<Location> locations = new ArrayList<>();

    private Facade() {
        //read data from source report;
        File file = new File("sourceReports.csv");
        boolean alreadyExists = file.exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                String line;
                String[] linebroken;
                int i = 0;
                line = br.readLine();
                while (line != null) {
                    linebroken = line.split(",");
                    Location l = new Location(Double.valueOf(linebroken[4]),
                            Double.valueOf(linebroken[5]),
                            "Marker " + i,
                            "<h2> "  + linebroken[0] +
                                    "</h2> <br> Reporter: " + linebroken[1] +
                                    "<br> Date: " + linebroken[2] +
                                    "<br> Water Type: " + linebroken[6] +
                                    "<br> Water Condition: " + linebroken[7]);
                    locations.add(l);
                    i++;
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter method for locations
     * @return a List of Locations
     */
    public List<Location> getLocations() { return locations; }

    /**
     * Save data into the passed-in file as text
     * @param file the File to which we save
     */
    public void saveModelToText(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.saveToText(file);
    }

    /**
     * Load data from the passed-in file as text
     * @param file the File from which we load
     */
    public void loadModelFromText(File file) {
        PersistenceManager pm = new PersistenceManager(locations);
        pm.loadFromText(file);
    }

//    /**
//     * Save data into the passed-in file as binary
//     * @param file the File to which we save
//     */
//    public void saveModelToBinary(File file) {
//        PersistenceManager pm = new PersistenceManager(locations);
//        pm.saveToBinary(file);
//    }

//    /**
//     * Load data from the passed-in file as binary
//     * @param file the File from which we load
//     */
//    public void loadModelFromBinary(File file) {
//        PersistenceManager pm = new PersistenceManager(locations);
//        pm.loadFromBinary(file);
//    }

//
//    /**
//     * Load data from the passed-in file as json
//     * @param file the File from which we load
//     */
//    public void loadModelFromJson(File file) {
//        PersistenceManager pm = new PersistenceManager(locations);
//        pm.loadFromJsonfile(file);
//    }
//
//    /**
//     * Save data into the passed-in file as json
//     * @param file the File to which we save
//     */
//    public void saveModelToJson(File file) {
//        PersistenceManager pm = new PersistenceManager(locations);
//        pm.saveToJson(file);
//    }

    /**
     * Add a location to the list of locations
     * @param loc the Location to add to the List
     */
    public void addLocation(Location loc) {
        locations.add(loc);
    }
//    public void addLocations() {
//        locations.add(new Location(34.043, -88.043, "New Marker", "Some new data"));
//    }
}
