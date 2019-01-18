package Model;

public class Quest {
    private int id;
    private String name, description;
    private int reward, creatorId, modifierId, questType;

    public Quest(int id, String name, String description, int reward, int creatorId, int modifierId, int questType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
        this.questType = questType;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    public int getQuestType() {
        return questType;
    }

    public void setQuestType(int questType) {
        this.questType = questType;
    }
}
