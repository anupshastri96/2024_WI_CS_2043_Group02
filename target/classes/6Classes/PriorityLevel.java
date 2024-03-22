public enum PriorityLevel {
    HIGH("High", "Highest priority"),
    MEDIUM("Medium", "Medium priority"),
    LOW("Low", "Lowest priority");

    private final String name;
    private final String description;

    PriorityLevel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}