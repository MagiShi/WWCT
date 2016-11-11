package src.model;

/**
 * A enum class representing the user type of the user
 */
public enum UserType {
    USER("USER"), WORKER("WORKER"), ADMIN("ADMINISTRATOR"), MANAGER("MANAGER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    /**
     * gets the user type
     * @return  String of the type
     */
    public String getType() {
        return type;
    }

    /**
     * what to return in string form if called
     * @return  String of the user type
     */
    public String toString() {
        return type;
    }
}
