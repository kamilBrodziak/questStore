package DAO;

import Model.Student;

import java.util.List;

public interface StudentDAO {
    void addStudent(Student student) throws Exception;
    void updateStudent(Student student) throws Exception;
    void deleteStudent(int id) throws Exception;
    List<Student> getStudents() throws Exception;
    Student getStudent(int id) throws Exception;
}
