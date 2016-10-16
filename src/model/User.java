/**
 * Created by Maggie on 9/20/2016.
 */

package src.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class User {
    private String userID;
    private String password;

    private String name;
    private String address;
    private String email;
    private boolean banned;

    private ObjectProperty<UserType> type = new SimpleObjectProperty<UserType>();

    public void setUserType (UserType userType) { type.set(userType);}



    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        banned = false;
    }

    public User(String userID, String password, String newName, String Type, String Email, String newAddress, String banStatus) {
        this.userID = userID;
        this.password = password;
        this.name = newName;
        this.address = newAddress;
        this.email = Email;
        if (banStatus.equals("Not Banned")) {
            banned = false;
        } else {
            banned = true;
        }
    }

    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public String getType() { return type.toString(); }

    public void changeName(String newName) {
        name = newName;
    }

    public void changeEmail(String newEmail) {
        email = newEmail;
    }

    public void changeAddress(String newAddress) {
        address = newAddress;
    }

    public void changeStatus() {
        banned = !banned;
    }

    public String getStatus() {
        if (banned) {
            return "Banned";
        } else {
            return "Not Banned";
        }
    }
}