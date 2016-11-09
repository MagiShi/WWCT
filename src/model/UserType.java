package src.model;

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
