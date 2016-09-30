package src.model;

/**
 * Created by Maggie on 9/30/2016.
 */
public enum UserType {
    USER("USER"), WORKER("WORKER"), ADMIN("ADMINISTRATOR"), MANAGER("MANAGER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public String toString() {
        return type;
    }
}
