package src.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Cassie on 10/23/2016.
 */
public class UserManager {
    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<User> workers = new ArrayList<>();
    private ArrayList<User> managers = new ArrayList<>();
    private ArrayList<User> admins = new ArrayList<>();
    private User currentUser;
    private static final UserManager instance = new UserManager();

    public UserManager() {
        boolean alreadyExists = new File("database.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
                String line = "";
                String[] info = null;
                while ((line = br.readLine()) != null) {
                    info = line.split(",");
                    User newUser = new User(info[1], info[2], info[0], info[3], info[4], info[5], info[6]);
                    allUsers.add(newUser);
                    if (info[3].equals("WORKER")) {
                        workers.add(newUser);
                    } else if (info[3].equals("MANAGER")) {
                        managers.add(newUser);
                    } else if (info[3].equals("ADMINISTRATOR")) {
                        admins.add(newUser);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public static UserManager getInstance() {
        return instance;
    }
    public boolean userExists(String thisUser) {
        for (int i = 0; i < allUsers.size(); i++) {
            User u = allUsers.get(i);
            if (u.getUserID().equals(thisUser)) {
                return true;
            }
        }
        return false;
    }
    public boolean passwordCorrect(String thisUser, String password) {
        for (int i = 0; i < allUsers.size(); i++) {
            User u = allUsers.get(i);
            if (u.getUserID().equals(thisUser) && u.getPassword().equals(password)) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }

}
