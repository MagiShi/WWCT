package src.model;

public enum PurityCondition {
    SAFE("SAFE"), TREATABLE("TREATABLE"), UNSAFE("UNSAFE");

    public final String condition;

    PurityCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
    public String toString() {
        return condition;
    }
}
