package src.model;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The LogIn class represents an object for Log in
 */
public class LogIn {
    private final String userInputUsername;
    private final String userInputPassword;

    /**
     * A constructor to create a registration object
     * @param userInputUsername     The potential userID of the user
     * @param userInputPassword     The potential password of the user
     */
    public LogIn(String userInputUsername, String userInputPassword) {
        this.userInputUsername = userInputUsername;
        this.userInputPassword = userInputPassword;
    }

    /**
     * A method to log in a user. Returns a user object and checks
     * information with the database.csv file
     * @return currentUser      The new user that was created by the method
     */
    public User logIn() {
        User currentUser = null;
        if (userInputUsername.compareTo("") == 0 || userInputPassword.compareTo("") == 0) {
            return currentUser;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("database.csv"));
            String line;
            String[] info = null;
            boolean usernameFound = false;
            line = br.readLine();
            while (!usernameFound && (line != null)) {
                info = line.split(",");
                if (info[1].equals(userInputUsername)) {
                    usernameFound = true;
                }
                line = br.readLine();
            }
            if (!usernameFound) {
                return currentUser;
            } else if (info[2].equals(userInputPassword)){
                currentUser = new User(info[1], info[2],
                        info[0], info[3],
                        info[4], info[5], info[6]);
            } else {
                return currentUser;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }/*
    public User logIn(TextField usernameInput, PasswordField passwordInput) {
        User currentUser = null;
        if (userInputUsername.compareTo("") == 0 || userInputPassword.compareTo("") == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Blank input");
            alert.setHeaderText("Blank input");
            alert.setContentText("You did not put in a input for username, password or both.");
            alert.showAndWait();
            usernameInput.clear();
            passwordInput.clear();
            return currentUser;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("database.csv"));
            String line;
            String[] info = null;
            boolean usernameFound = false;
            line = br.readLine();
            while (!usernameFound && (line != null)) {
                info = line.split(",");
                if (info[1].equals(userInputUsername)) {
                    usernameFound = true;
                }
                line = br.readLine();
            }
            if (!usernameFound) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong Credentials");
                alert.setHeaderText("Wrong Credentials");
                alert.setContentText("Username does not exist. Please try again.");
                alert.showAndWait();
                usernameInput.clear();
                passwordInput.clear();
            } else if (info[2].equals(userInputPassword)){
                currentUser = new User(info[1], info[2],
                        info[0], info[3],
                        info[4], info[5], info[6]);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Wrong Credentials");
                alert.setHeaderText("Wrong Credentials");
                alert.setContentText("Wrong password. Please try again.");
                alert.showAndWait();
                usernameInput.clear();
                passwordInput.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }*/
}
