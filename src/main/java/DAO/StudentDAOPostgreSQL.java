package DAO;

import Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOPostgreSQL implements StudentDAO{

    private DataBaseConnector dbCon = null;

    public StudentDAOPostgreSQL(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (name, surname, email, logins_id) VALUES (?, ?, ?, ?::integer);";
        String[] queryAttr = {student.getName(), student.getSurname(), student.getEmail(),
                Integer.toString(student.getLoginsID())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateStudent(Student student) throws SQLException{
        String query = "UPDATE student SET name = ?, surname = ?, email = ?, logins_id = ? WHERE id = ?";
        String[] queryAttr = {student.getName(), student.getSurname(), student.getEmail(),
                Integer.toString(student.getLoginsID())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteStudent(int id) throws SQLException{
        String query = "DELETE FROM students WHERE id = ?";
        String[] queryAttr = {Integer.toString(id)};
        dbCon.updateSQL(query, queryAttr);
    }

    public List<Student> getStudents() throws SQLException {
        String query = "SELECT * FROM students;";
        ResultSet rs = dbCon.query(query, null);
        List<Student> studentsList = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student(rs.getInt("id"), rs.getInt("logins_id"),
                    rs.getString("name"), rs.getString("surname"), rs.getString("email"));
            studentsList.add(student);
        }
        return studentsList;
    }

    public Student getStudent(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id=?;";
        String[] queryAttr = {Integer.toString(id)};
        ResultSet rs = dbCon.query(query, queryAttr);

        if (rs.next()) {
            return new Student(rs.getInt("id"), rs.getInt("logins_id"),
                    rs.getString("name"), rs.getString("surname"), rs.getString("email"));
        }
        return null;
    }

    public Student getStudentByLoginID(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id_logins=?;";
        String[] queryAttr = {Integer.toString(id)};
        ResultSet rs = dbCon.query(query, queryAttr);

        if (rs.next()) {
            return new Student(rs.getInt("id"), rs.getInt("logins_id"),
                    rs.getString("name"), rs.getString("surname"), rs.getString("email"));
        }
        return null;
    }
}
