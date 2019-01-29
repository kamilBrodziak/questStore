package Model;

public class Student {

    private int id;
    private int loginsID;
    private String name;
    private String surname;
    private String email;

    public Student (int id, int loginsID, String name, String surname, String email){
        this.id = id;
        this.loginsID = loginsID;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public int getLoginsID() {
        return loginsID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLoginsID(int loginsID) {
        this.loginsID = loginsID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
