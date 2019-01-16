package Model;

public class Achievement {
    private int id, experience, tier, creatorId, modifierId;
    private String name, description;

    public Achievement(int id, String name, String description, int experience, int tier, int creatorId, int modifierId) {
        this.id = id;
        this.experience = experience;
        this.tier = tier;
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
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

    public int getCreatorId() {
        return creatorId;
    }

    public int getModifierId() {
        return modifierId;
    }
}