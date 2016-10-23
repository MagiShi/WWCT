package src.model;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Cassie on 10/23/2016.
 */
public class SourceManager {
    private ArrayList<WaterSource> sources = new ArrayList<>();
    private static final SourceManager instance = new SourceManager();
    private WaterSource currentSource;

    public static SourceManager getInstance() {
        return instance;
    }

    public SourceManager() {
        boolean alreadyExists = new File("sourceReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("sourceReports.csv"))) {
                String line = "";
                String[] info = null;
                while ((line = br.readLine()) != null) {
                    info = line.split(",");
                    Double lat = new Double(info[4]);
                    Double longi = new Double(info[5]);
                    WaterSource source = new WaterSource(info[1], info[2], info[3], lat.doubleValue(), longi.doubleValue(), info[6], info[7]);
                    sources.add(source);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addSource(String location, SourceType type, WaterCondition condition, String lat, String longit, Facade fc) {
        UserManager uM = UserManager.getInstance();
        User user = uM.getCurrentUser();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
        Date dateObject = new Date();
        String dateString = dateFormat.format(dateObject);
        try {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("sourceReports.csv", true);


                int num = 1000 + (int) (Math.random() * ((9999 - 1000) + 1));

                fileWriter.append("Report #" + num);
                fileWriter.append(", ");
                fileWriter.append(user.getName());
                fileWriter.append(", ");
                fileWriter.append(dateString);
                fileWriter.append(", ");
                fileWriter.append(location);
                fileWriter.append(", ");
                fileWriter.append(lat);
                fileWriter.append(",");
                fileWriter.append(longit);
                fileWriter.append(",");
                fileWriter.append(type.toString());
                fileWriter.append(", ");
                fileWriter.append(condition.toString());
                fileWriter.append("\n");

                Location loc = new Location(Double.valueOf(lat),
                        Double.valueOf(longit),
                        "Marker",
                        "<h2> " + num +
                                "</h2> <br> Reporter: " + user.getName() +
                                "<br> Date: " + dateString +
                                "<br> Water Type: " + type +
                                "<br> Water Condition: " + condition);

                fc.addLocation(loc);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ioe) {
                    System.out.println("Error while flushing");
                    ioe.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WaterSource newSource = new WaterSource(user.getName(), dateString, location, Double.valueOf(lat), Double.valueOf(longit), type.toString(), condition.toString());
        sources.add(newSource);
    }
}
