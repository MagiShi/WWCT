/**
 * Created by Maggie on 9/20/2016.
 */
public class User {
    private String userID;
    private String password;

    User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
}
