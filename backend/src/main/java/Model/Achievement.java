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

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}