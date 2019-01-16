package Model;

import java.util.Date;

public class Mentor {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String city;
    private String beginWork;
    private int loginsID;

    public Mentor(int id, String name, String surname, String email, String city, String beginWork, int loginsID) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.beginWork = beginWork;
        this.loginsID = loginsID;
    }

    public Mentor(String name, String surname, String email, String city, String beginWork, int loginsID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.beginWork = beginWork;
        this.loginsID = loginsID;
    }

    public Mentor(String name, String surname, String email, String city, String beginWork) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.beginWork = beginWork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBeginWork() {
        return beginWork;
    }

    public void setBeginWork(String beginWork) {
        this.beginWork = beginWork;
    }

    public int getLoginsID() {
        return loginsID;
    }

    public void setLoginsID(int loginsID) {
        this.loginsID = loginsID;
    }
}
