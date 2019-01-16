package Model;

public class ComponentJoin {
    private int id;
    private String name, description, firstName, lastName;

    public ComponentJoin(int id, String name, String description, String firstName, String lastName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
