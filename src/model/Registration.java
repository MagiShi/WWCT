package src.model;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Report class represents an object for Refistration
 */
public class Registration {

    private final String userInputUsername;
    private final String userInputPassword;
    private final String matchPassword;
    private final UserType userInputUserType;
    private final String userNameInput;

    /**
     * A constructor to create a registration object
     * @param userInputUsername     The potential userID of the user
     * @param userInputPassword     The potential password of the user
     * @param matchPassword         The password confirmation
     * @param userInputUserType     The potential user type of the user
     * @param userNameInput         The potential name of the user
     */
    public Registration(String userInputUsername, String userInputPassword,
                 String matchPassword, UserType userInputUserType, String userNameInput) {
        this.userInputUsername = userInputUsername;
        this.userInputPassword = userInputPassword;
        this.matchPassword = matchPassword;
        this.userInputUserType = userInputUserType;
        this.userNameInput = userNameInput;
    }

    /**
     * A method to register a new user. Returns a user object and writes
     * the information onto the database.csv file
     * @param passwordInput     The passwordInput field of the fxml sheet
     * @param passwordInput2    The confirm password field of the fxml sheet
     * @param usernameInput     The username input field of the fxml sheet
     * @return currentUser      The new user that was created by the method
     */
    public User register(PasswordField passwordInput, PasswordField passwordInput2, TextField usernameInput) {
        User currentUser = null;
        if (checkOriginal()) {
//            try {
                if (checkPassword()) {
                    addToFile();
                    currentUser = createUser();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password does not match");
                    alert.setHeaderText("Password does not match");
                    alert.setContentText("Input for password and confirm password does not match.");
                    alert.showAndWait();
                    passwordInput.clear();
                    passwordInput2.clear();
                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Username Taken");
            alert.setHeaderText("Username Taken");
            alert.setContentText("Account with this username already exists. Please enter a different username.");
            alert.showAndWait();
            usernameInput.clear();
        }
        return currentUser;
    }

    /**
     * checks if userInputUsername is not already in the database
     * @return boolean of whether or not the potential username is original
     */
    public boolean checkOriginal() {
        if (userInputUsername == null) {
            return false;
        }

        boolean usernameOriginal = true;
        //check if username exists
        boolean alreadyExists = new File("database.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("database.csv"))) {
                String line;
                line = br.readLine();
                while (usernameOriginal && (line != null)) {
                    String[] info = line.split(",");
                    if (info[1].equals(userInputUsername)) {
                        usernameOriginal = false;
                    }
                    line = br.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
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
                    ioe.printStackTrace();
                }
            }
        }
        return usernameOriginal;
    }

    /**
     * checks userInputPassword and matchPassword match
     * @return boolean of whether or not the two passwords match
     */
    public boolean checkPassword() {
        return !("".equals(matchPassword) || "".equals(userInputPassword)) && matchPassword.equals(userInputPassword);
    }

    private void addToFile() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("database.csv", true);
            fileWriter.append(userNameInput);
            fileWriter.append(",");
            fileWriter.append(userInputUsername);
            fileWriter.append(",");
            fileWriter.append(userInputPassword);
            fileWriter.append(",");
            fileWriter.append(userInputUserType.toString());
            fileWriter.append(",");
            fileWriter.append("[set email]");
            fileWriter.append(",");
            fileWriter.append("[set address]");
            fileWriter.append(",");
            fileWriter.append("Not Banned");
            fileWriter.append("\n");

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

    private User createUser() {

        return new User(userInputUsername, userInputPassword,
                userNameInput, userInputUserType.toString(),
                "[set email]", "[set address]", "Not Banned");
    }


}
