package DAO;

import Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private DataBaseConnector dataBaseConnector;

    public StudentDAO(){
        this.dataBaseConnector = new DataBaseConnector();
    }

    public void addStudent(Student student) throws Exception{
        Connection c = null;
        Statement stmt = null;
        PreparedStatement insertStatement = null;
        try {
            c = dataBaseConnector.connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            insertStatement = c.prepareStatement("INSERT INTO students (name, surname, email, logins_id) VALUES (?, ?, ?, ?)");

            insertStatement.setString(1, student.getName());
            insertStatement.setString(2, student.getSurname());
            insertStatement.setString(3, student.getEmail());
            insertStatement.setInt(4, student.getLoginsID());

            insertStatement.addBatch();
            insertStatement.executeBatch();
            c.commit();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );

        }finally{
            stmt.close();
            c.close();
        }
    }

    public void editStudent(Student student) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            c = dataBaseConnector.connect();

            String sql = "UPDATE student SET name = ?, surname = ?, email = ?, logins_id = ? WHERE id = ?";

            pstmt = c.prepareStatement(sql);

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSurname());
            pstmt.setString(3, student.getEmail());
            pstmt.setInt(4, student.getLoginsID());

            pstmt.executeUpdate(sql);
            c.commit();

        } catch ( Exception e ) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }

    }

    public void deleteStudent(Student student) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try{
            c = dataBaseConnector.connect();

            String sql = "DELETE FROM students WHERE id = ?";

            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, student.getId());

            pstmt.executeUpdate(sql);
            c.commit();
        }catch(Exception e){
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }
    }

    public List<Student> selectAllStudents() throws Exception{
        List<Student> result = new ArrayList<Student>();
        Statement stmt = null;
        Connection c = null;
        ResultSet rs = null;
        try {
            c = dataBaseConnector.connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT id, logins_id, name, surname, email FROM students");
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int logins_id = rs.getInt("logins_id");
                String  name = rs.getString("name");
                String surname  = rs.getString("surname");
                String  email = rs.getString("email");

                result.add(new Student(id, logins_id, name, surname, email));
            }
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
            System.exit(0);
        }finally{
            rs.close();
            stmt.close();
            c.close();
        }
        return result;
    }

    public Student selectOneStudent(int id) throws Exception{
        Connection c = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Student student = null;
        try {
            c = dataBaseConnector.connect();

            String sql = "SELECT id, logins_id, name, surname, email FROM students WHERE id = ?";
            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, id);

            while (rs.next()) {
                int logins_id = rs.getInt("logins_id");
                String  name = rs.getString("name");
                String surname  = rs.getString("surname");
                String  email = rs.getString("email");

                student = new Student(id, logins_id, name, surname, email);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
            System.exit(0);
        }finally{
            rs.close();
            pstmt.close();
            c.close();
        }
        return student;
    }
}
