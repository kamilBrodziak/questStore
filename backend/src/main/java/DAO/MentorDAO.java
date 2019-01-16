package DAO;

import Model.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO {

    private DataBaseConnector dataBaseConnector;

    public MentorDAO(){
        this.dataBaseConnector = new DataBaseConnector();
    }

    public void addMentor(Mentor mentor) throws Exception{
        Connection c = null;
        Statement stmt = null;
        PreparedStatement insertStatement = null;
        try {
            c = dataBaseConnector.connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            insertStatement = c.prepareStatement("INSERT INTO mentors (name, surname, email, city, beginWork, logins_id) VALUES (?, ?, ?, ?, ?, ?)");

            insertStatement.setString(1, mentor.getName());
            insertStatement.setString(2, mentor.getSurname());
            insertStatement.setString(3, mentor.getEmail());
            insertStatement.setString(4, mentor.getCity());
            insertStatement.setString(5, mentor.getBeginWork());
            insertStatement.setInt(6, mentor.getLoginsID());

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

    public void editMentor(Mentor mentor) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try {
            c = dataBaseConnector.connect();

            String sql = "UPDATE mentors SET name = ?, surname = ?, email = ?, city = ?, beginWork = ?, logins_id = ? WHERE id = ?";

            pstmt = c.prepareStatement(sql);

            pstmt.setString(1, mentor.getName());
            pstmt.setString(2, mentor.getSurname());
            pstmt.setString(3, mentor.getEmail());
            pstmt.setString(4, mentor.getCity());
            pstmt.setString(5, mentor.getBeginWork());
            pstmt.setInt(6, mentor.getLoginsID());

            pstmt.executeUpdate(sql);
            c.commit();

        } catch ( Exception e ) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }

    }

    public void deleteMentor(Mentor mentor) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try{
            c = dataBaseConnector.connect();

            String sql = "DELETE FROM mentors WHERE id = ?";

            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, mentor.getId());

            pstmt.executeUpdate(sql);
            c.commit();
        }catch(Exception e){
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }
    }

    public List<Mentor> selectAllMentors() throws Exception{
        List<Mentor> result = new ArrayList<Mentor>();
        Statement stmt = null;
        Connection c = null;
        ResultSet rs = null;
        try {
            c = dataBaseConnector.connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            rs = stmt.executeQuery( "SELECT name, surname, email, city, begin_work FROM mentors");
            while ( rs.next() ) {
                String  name = rs.getString("name");
                String surname  = rs.getString("surname");
                String  email = rs.getString("email");
                String city = rs.getString("city");
                String  beginWork = rs.getString("begin_work");

                result.add(new Mentor(name, surname, email, city, beginWork));
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

    public Mentor selectOneMentor(int id) throws Exception{
        Connection c = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Mentor mentor = null;
        try {
            c = dataBaseConnector.connect();

            String sql = "SELECT name, surname, email, city, begin_work FROM mentors WHERE id = ?";
            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, id);

            while (rs.next()) {
                String  name = rs.getString("name");
                String surname  = rs.getString("surname");
                String  email = rs.getString("email");
                String city = rs.getString("city");
                String  beginWork = rs.getString("begin_work");

                mentor = new Mentor(name, surname, email, city, beginWork);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
            System.exit(0);
        }finally{
            rs.close();
            pstmt.close();
            c.close();
        }
        return mentor;
    }
}
