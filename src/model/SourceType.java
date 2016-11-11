package src.model;

/**
 * A enum class representing the source type for a water source
 */
public enum SourceType {
    BOTTLED("BOTTLED"), WELL("WELL"), STREAM("STREAM"), LAKE("LAKE"),
    SPRING("SPRING"), OTHER("OTHER");

    private final String type;

    SourceType(String type) {
        this.type = type;
    }

    /**
     * gets the water source type
     * @return type     The String of the source type
     */
    public String getType() {
        return type;
    }

    /**
     * what to return in string form if called
     * @return type    String of the water type
     */
    public String toString() {
        return type;
    }
}
