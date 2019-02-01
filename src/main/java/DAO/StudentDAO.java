package DAO;

import Model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student) throws SQLException;
    void updateStudent(Student student) throws SQLException;
    void deleteStudent(int id) throws SQLException;
    List<Student> getStudents() throws SQLException;
    Student getStudent(int id) throws SQLException;
    Student getStudentByLoginID(int id) throws SQLException;
}
