
package src.model;

import java.util.Objects;

/**
 * An object representing a User object
 */
public class User {
    private final String userID;
    private final String password;

    private String name;
    private String address;
    private String email;
    private boolean banned;

    private String userType;

    private String[] userInfo;

    /**
     * A constructor to create a user
     * @param userID  The userID of the user
     * @param password  The password of the user
     */
    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        banned = false;
    }

    /**
     * A constructor to create a user
     * @param userID    The userID of the user
     * @param password  The password of the user
     * @param newName   The name of the user
     * @param userType  The user type of the user
     * @param Email     The email of the user
     * @param newAddress  The address of the user
     * @param banStatus Whether the user was banned or not
     */
    public User(String userID, String password, String newName, String userType,
                String Email, String newAddress, String banStatus) {
        this.userID = userID;
        this.password = password;
        this.name = newName;
        this.address = newAddress;
        this.email = Email;
        this.userType = userType;
        banned = !("Not Banned".equals(banStatus));
    }

    /**
     * gets the userID of the user
     * @return userID  The string of the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * gets the password of the user
     * @return password  The string of the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * gets the name of the user
     * @return name  The string of the name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the address of the user
     * @return address  The string of the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * gets the email address of the user
     * @return email  The string of the address
     */
    public String getEmail() {
        return email;
    }

    /**
     * gets the user type of the user
     * @return userType  The string of the type
     */
    public String getType() { return userType; }

    /**
     * sets the user type of the user
     * @param userType  the user type ro set to
     */
    public void setUserType (String userType) { this.userType = userType;}

    /**
     * sets the name of the user
     * @param newName  The name to set to
     */
    public void changeName(String newName) {
        name = newName;
    }

    /**
     * sets the email of the user
     * @param newEmail  The email to set to
     */
    public void changeEmail(String newEmail) {
        email = newEmail;
    }

    /**
     * sets the address of the user
     * @param newAddress  The address to set to
     */
    public void changeAddress(String newAddress) {
        address = newAddress;
    }

    /**
     * changes the van status of the user
     */
    public void changeStatus() {
        banned = !banned;
    }

    /**
     * gets if the user is banned or not banned
     * @return  The string of the status of the user
     */
    public String getStatus() {
        if (banned) {
            return "Banned";
        } else {
            return "Not Banned";
        }
    }
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof User)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        User c = (User) o;

        // Compare the data members and return accordingly
        return Objects.equals(name, c.getName()) && Objects.equals(userID, c.getUserID())
                && Objects.equals(password, c.getPassword()) && Objects.equals(userType, c.getType())
                && Objects.equals(email, c.getEmail()) && Objects.equals(address, c.getAddress())
                && Objects.equals(getStatus(), c.getStatus());
    }
}