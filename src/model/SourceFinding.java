package src.model;

import java.io.*;

/**
 * Created by surinapuri on 11/16/16.
 */
public class SourceFinding {
    private String userInputName;
    private String userInputLocation;
    private SourceType userInputWaterType;
    private WaterCondition userInputWaterCondition;
    private double longit;
    private double lat;
    private final int MINNUM = -90;
    private final int MAXNUM = 90;

    public SourceFinding (String userInputName, String userInputLocation, SourceType userInputWaterType, WaterCondition userInputWaterCondition, double longit, double lat) {
        this.userInputName = userInputName;
        this.userInputLocation = userInputLocation;
        this.userInputWaterType = userInputWaterType;
        this.userInputWaterCondition = userInputWaterCondition;
        this.longit = longit;
        this.lat = lat;
    }

    public void addToFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("dummySourceReport.csv");
            fileWriter.append(userInputName);
            fileWriter.append(",");
            fileWriter.append(userInputLocation);
            fileWriter.append(",");
            fileWriter.append(userInputWaterType.toString());
            fileWriter.append(",");
            fileWriter.append(userInputWaterCondition.toString());
            fileWriter.append(",");
            fileWriter.append(Double.toString(lat));
            fileWriter.append(",");
            fileWriter.append(Double.toString(lat));

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            }  catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * @return
     */
    public boolean valid() {
        if (userInputName == null) {
            return false;
        }

        boolean add = true;
        try (BufferedReader br = new BufferedReader(new FileReader("dummySourceReport.csv"))) {
            String line;
            line = br.readLine();
            while (line != null) {
                String[] info = line.split(",");
                double l = Double.parseDouble(info[4]);
                double l2 = Double.parseDouble(info[5]);
                if (l < MINNUM || l > MAXNUM) {
                    add = false;
                    System .out.println(add);
                }
                if (l2 < MINNUM || l2 > MAXNUM) {
                    add = false;
                }
                String name = info[1];
                String location = info[3];
                if (name == null || location == null) {
                    add = false;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }


}
