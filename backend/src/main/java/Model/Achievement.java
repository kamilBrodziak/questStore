package Model;

public class Achievement {
    private int id, experience, tier;
    private String name, description;

    public Achievement(int id, String name, String description, int experience, int tier) {
        this.id = id;
        this.experience = experience;
        this.tier = tier;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public int getTier() {
        return tier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}