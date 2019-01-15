package Model;

public class Admin {

    private int id;
    private int loginsID;
    private String name;
    private String surname;
    private String email;

    public Admin(int id, int loginsID, String name, String surname, String email) {
        this.id = id;
        this.loginsID = loginsID;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Admin(int loginsID, String name, String surname, String email) {
        this.loginsID = loginsID;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoginsID() {
        return loginsID;
    }

    public void setLoginsID(int loginsID) {
        this.loginsID = loginsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
