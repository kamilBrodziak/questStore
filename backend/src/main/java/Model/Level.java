package Model;

public class Level {
    private int id;
    private int userID;
    private int experience;
    private int money;
    private int level;

    public Level(int id, int userID, int experience, int money, int level) {
        this.id = id;
        this.userID = userID;
        this.experience = experience;
        this.money = money;
        this.level = level;
    }

    public int getID() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public int getExperience() {
        return experience;
    }

    public int getMoney() {
        return money;
    }

    public int getLevel() {
        return level;
    }
}
