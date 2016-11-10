package src.model;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;

/**
 * Created by Maggie on 11/9/2016.
 */
public class Registration {

    String userInputUsername;
    String userInputPassword;
    String matchPassword;
    UserType userInputUserType;
    String userNameInput;

    public Registration(String userInputUsername, String userInputPassword,
                 String matchPassword, UserType userInputUserType, String userNameInput) {
        this.userInputUsername = userInputUsername;
        this.userInputPassword = userInputPassword;
        this.matchPassword = matchPassword;
        this.userInputUserType = userInputUserType;
        this.userNameInput = userNameInput;
    }

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


    public boolean checkOriginal() {

        if (userInputUsername.equals(null)) {
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

    public boolean checkPassword() {
        if (matchPassword.equals("") || userInputPassword.equals("")) {
            return false;
        }
        return matchPassword.equals(userInputPassword);
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
        User currentUser = new User(userInputUsername,userInputPassword,
                userNameInput , userInputUserType.toString(),
                "[set email]", "[set address]", "Not Banned");

        return currentUser;
    }


}
