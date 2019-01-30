package Model;

public class Session {
    private int id;
    private int loginID;

    public Session(int id, int loginID) {
        this.id = id;
        this.loginID = loginID;
    }

    public int getId() {
        return id;
    }

    public int getLoginID() {
        return loginID;
    }
}
