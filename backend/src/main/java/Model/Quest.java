package Model;

public class Quest {
    private int id;
    private String name, description;
    private int reward, creatorId, modifierId;

    public Quest(int id, String name, String description, int reward, int creatorId, int modifierId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getReward() {
        return reward;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getModifierId() {
        return modifierId;
    }
}
