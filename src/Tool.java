/**
 * Supported tools.
 */
public enum Tool {
    CIRCLE("Circle"),
    OVAL("Oval"),
    RECTANGLE("Rectangle"),
    SQUARE("Square"),
    POLYGON("Polygon"),
    SELECT("Select");

    /**
     * The label of a tool.
     */
    public final String label;

    Tool(String label) {
        this.label = label;
    }
}
