package src.model;

/**
 * Created by Dain on 10/12/2016.
 */
public enum SourceType {
    BOTTLED("BOTTLED"), WELL("WELL"), STREAM("STREAM"), LAKE("LAKE"),
    SPRING("SPRING"), OTHER("OTHER");

    public final String type;

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
