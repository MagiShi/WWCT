package src.model;

public enum WaterCondition {
    WASTE("WASTE"), CLEAR("CLEAR"), MUDDY("MUDDY"), POTABLE("POTABLE");

    private final String condition;

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
