package Model;

public class Rank {

    private int id;
    private int level;
    private int experienceRequired;

    public Rank(int id, int level, int experienceRequired) {
        this.id = id;
        this.level = level;
        this.experienceRequired = experienceRequired;
    }

    public Rank(int level, int experienceRequired) {
        this.level = level;
        this.experienceRequired = experienceRequired;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(int experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
