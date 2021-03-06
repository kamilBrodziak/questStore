package Model;

public class Artifact {
    private int id;
    private String name;
    private String description;
    private int price;
    private int creatorId;
    private int modifierId;

    public Artifact(int id, String name, String description, int price, int creatorId, int modifierId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.creatorId = creatorId;
        this.modifierId = modifierId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public int getModifierId() {
        return modifierId;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }
}
