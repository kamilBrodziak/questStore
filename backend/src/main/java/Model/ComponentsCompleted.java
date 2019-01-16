package Model;

import java.util.Date;

public class ComponentsCompleted {
    int id, componentId, studentId;
    Date date;
    boolean approved;

    public ComponentsCompleted(int id, int studentId, int componentId, Date date, boolean approved) {
        this.id = id;
        this.studentId = studentId;
        this.componentId = componentId;
        this.date = date;
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public int getComponentId() {
        return componentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
