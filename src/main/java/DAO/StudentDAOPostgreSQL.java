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
        String query = "INSERT INTO students (name, surname, email, id_logins) VALUES (?, ?, ?, ?::integer);";
        String[] queryAttr = {student.getName(), student.getSurname(), student.getEmail(),
                Integer.toString(student.getLoginsID())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateStudent(Student student) throws SQLException{
        String query = "UPDATE students SET name = ?, surname = ?, email = ?, id_logins = ?::integer WHERE id = ?::integer";
        String[] queryAttr = {student.getName(), student.getSurname(), student.getEmail(),
                Integer.toString(student.getLoginsID()), Integer.toString(student.getId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateEmail(Student student) throws SQLException {
        String query = "UPDATE students SET email = ? WHERE id = ?::integer";
        String[] queryAttr = {student.getEmail(), Integer.toString(student.getId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteStudent(int id) throws SQLException{
        String query = "DELETE FROM students WHERE id = ?::integer";
        String[] queryAttr = {Integer.toString(id)};
        dbCon.updateSQL(query, queryAttr);
    }

    public List<Student> getStudents() throws SQLException {
        String query = "SELECT * FROM students;";
        ResultSet rs = dbCon.query(query, null);
        List<Student> studentsList = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student(rs.getInt("id"), rs.getInt("id_logins"),
                    rs.getString("name"), rs.getString("surname"), rs.getString("email"));
            studentsList.add(student);
        }
        return studentsList;
    }

    public Student getStudent(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id=?::integer;";
        String[] queryAttr = {Integer.toString(id)};
        ResultSet rs = dbCon.query(query, queryAttr);

        if (rs.next()) {
            return new Student(rs.getInt("id"), rs.getInt("id_logins"),
                    rs.getString("name"), rs.getString("surname"), rs.getString("email"));
        }
        return null;
    }

    public Student getStudentByLoginID(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id_logins=?::integer;";
        String[] queryAttr = {Integer.toString(id)};
        ResultSet rs = dbCon.query(query, queryAttr);

        if (rs.next()) {
            return new Student(rs.getInt("id"), rs.getInt("id_logins"),
                    rs.getString("name"), rs.getString("surname"), rs.getString("email"));
        }
        return null;
    }

    public boolean checkIfEmailExists(String newEmail) throws SQLException {
        String query = "SELECT email FROM students;";
        String[] queryAttr = {};
        ResultSet rs = dbCon.query(query, queryAttr);
        int i = 0;
        while(rs.next()){
            String existEmail = rs.getString("email");

            if(existEmail.equalsIgnoreCase(newEmail)){
                i++;
            }
        }
        return i < 2;
    }
}
