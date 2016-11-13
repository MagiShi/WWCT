package src.model;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class with methods that can be used to ban/unban users and delete accounts.
 */
public class StatusChange {

    private final String username;
    private final List<String> allUsers = new ArrayList<>();

    /**
     * A constructor to create a Status Change object.
     * @param username the String username of the user who will be banned/unbanned/deleteds
     */
    public StatusChange(String username) {
        this.username = username;

        try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
            String line = br.readLine();
            while (line != null) {
                allUsers.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to change the ban status of the user.
     * @param action String representation of the action to
     *               take- either "ban" or "unban"
     * @return boolean to indicate whether the user
     * was found (true) or not (false)
     */
    public boolean changeBanStatus(String action) {
        boolean found = false;
        for (int i = 0; (i < allUsers.size()) && !found; i++) {
            String user = allUsers.get(i);
            String[] userData = user.split(",");
            if (userData[1].equals(username)) {
                allUsers.remove(i);
                if ("ban".equals(action)) {
                    userData[6] = "BANNED";
                } else {
                    userData[6] = "Not banned";
                }
                user = userData[0] + "," + userData[1] + "," + userData[2] + "," + userData[3]
                        + "," + userData[4] + "," + userData[5] + "," + userData[6];
                allUsers.add(i, user);
                found = true;
                rewriteDatabase();
            }
        }
        return found;
    }

    /**
     * A method to delete the account.
     * @return boolean to indicate whether the user
     * was found (true) or not (false).
     */
    public boolean deleteUser() {
        boolean found = false;
        for (int i = 0; (i < allUsers.size()) && !found; i++) {
            String user = allUsers.get(i);
            String[] userData = user.split(",");
            if (userData[1].equals(username)) {
                allUsers.remove(i);
                i--;
                found = true;
                rewriteDatabase();
            }
        }
        return found;
    }


    private void rewriteDatabase() {
        BufferedWriter writer = null;
        try {
            File newFile = new File("database.csv");
            writer = new BufferedWriter(new FileWriter(newFile));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ioe) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Rewrite Error");
                alert.setHeaderText("Error while flushing: " + ioe.toString());
                alert.showAndWait();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("database.csv", true);
            while (!allUsers.isEmpty()) {
                fileWriter.append(allUsers.remove(0));
                fileWriter.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException ioe) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File Rewrite Error");
                alert.setHeaderText("Error while flushing: " + ioe.toString());
                alert.showAndWait();
            }
        }
    }


}
