public class MushroomDetails {
    private String name;
    private String description;
    private String height;
    private String diameter;
    private String colours;
    private String habit;
    private String nearbyTrees;
    private String sporePrint;
    private String poisonous;

    public MushroomDetails(String name, String description, String height, String diameter, String colours, String habit, String nearbyTrees, String sporePrint, String poisonous) {
        this.name = name;
        this.description = description;
        this.height = height;
        this.diameter = diameter;
        this.colours = colours;
        this.habit = habit;
        this.nearbyTrees = nearbyTrees;
        this.sporePrint = sporePrint;
        this.poisonous = poisonous;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getColours() {
        return colours;
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getNearbyTrees() {
        return nearbyTrees;
    }

    public void setNearbyTrees(String nearbyTrees) {
        this.nearbyTrees = nearbyTrees;
    }

    public String getSporePrint() {
        return sporePrint;
    }

    public void setSporePrint(String sporePrint) {
        this.sporePrint = sporePrint;
    }

    public String getPoisonous() {
        return poisonous;
    }

    public void setPoisonous(String poisonous) {
        this.poisonous = poisonous;
    }
}
