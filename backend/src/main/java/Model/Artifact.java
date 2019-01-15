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
        this.id = id;
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
}
