package Model;

import java.util.Date;

public class StudentGroup {

    private String name;
    private Date creationDate;

    public StudentGroup(String name, Date creationDate){
        this.name = name;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
