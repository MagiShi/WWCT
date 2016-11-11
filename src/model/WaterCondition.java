package src.model;

/**
 * A enum class representing the water condition for a water source
 */
public enum WaterCondition {
    WASTE("WASTE"), CLEAR("CLEAR"), MUDDY("MUDDY"), POTABLE("POTABLE");

    private final String condition;

    WaterCondition(String condition) {
        this.condition = condition;
    }

    /**
     * gets the water condition
     * @return  String of the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * what to return in string form if called
     * @return condition    String of the water condition
     */
    public String toString() {
        return condition;
    }
}
