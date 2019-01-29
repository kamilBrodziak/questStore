package Service;

import Model.StudentGroup;

import java.util.Date;

public class StudentGroupService {

    public StudentGroup createStudentGroup(String name, Date creationDate){
        StudentGroup studentGroup = new StudentGroup(name, creationDate);
        return studentGroup;
    }

}
