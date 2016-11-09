
package src.model;

public class User {
    private final String userID;
    private final String password;

    private String name;
    private String address;
    private String email;
    private boolean banned;

    private String userType;

    private String[] userInfo;


    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        banned = false;
    }

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
    public String getType() { return userType; }
    public void setUserType (String userType) { this.userType = userType;}

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