package Model;

public class Quest {
    private int id_quest;
    private String name, description;
    private int reward, creatorId, modifierId;

    public Quest(int id_quest, String name, String description, int reward, int creatorId, int modifierId) {
        this.id_quest = id_quest;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
    }

    public int getId_quest() {
        return id_quest;
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
