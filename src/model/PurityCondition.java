package src.model;


/**
 * The different types of purity condition that can be listed in a purity report.
 */
public enum PurityCondition {
    SAFE("SAFE"), TREATABLE("TREATABLE"), UNSAFE("UNSAFE");

    private final String condition;

    PurityCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Returns the purity condition of the given water report.
     *
     * @return the String version of the purity condition enum.
     */
    public String getCondition() {
        return condition;
    }
    public String toString() {
        return condition;
    }
}
