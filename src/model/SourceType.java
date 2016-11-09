package src.model;

public enum SourceType {
    BOTTLED("BOTTLED"), WELL("WELL"), STREAM("STREAM"), LAKE("LAKE"),
    SPRING("SPRING"), OTHER("OTHER");

    private final String type;

    SourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public String toString() {
        return type;
    }
}
