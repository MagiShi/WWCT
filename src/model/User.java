/**
 * Created by Maggie on 9/20/2016.
 */

package src.model;

import javafx.beans.property.ObjectProperty;

public class User {
    private String userID;
    private String password;

    private String name;
    private String address;
    private String email;

    private ObjectProperty<UserType> userType;

    private boolean banned;

    User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        banned = false;
    }

    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }

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

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }


}
