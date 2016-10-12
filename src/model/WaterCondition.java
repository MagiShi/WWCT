package src.model;

/**
 * Created by Dain on 10/12/2016.
 */
public enum WaterCondition {
    WASTE("WASTE"), CLEAR("CLEAR"), MUDDY("MUDDY"), POTABLE("POTABLE");

    public final String condition;

    WaterCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
    public String toString() {
        return condition;
    }
}
