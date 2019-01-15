package DAO;

import Model.Mentor;
import Model.Rank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private DataBaseConnector dataBaseConnector;

    public AdminDAO(){
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

    public void addRanks(Rank rank) throws Exception{
        Connection c = null;
        Statement stmt = null;
        PreparedStatement insertStatement = null;
        try {
            c = dataBaseConnector.connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            insertStatement = c.prepareStatement("INSERT INTO ranks (level, experienceRequired) VALUES (?, ?);");

            insertStatement.setInt(1, rank.getLevel());
            insertStatement.setInt(2, rank.getExperienceRequired());

            insertStatement.addBatch();
            insertStatement.executeBatch();
            c.commit();

        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );

        }finally{
            stmt.close();
            insertStatement.close();
            c.close();
        }
    }

    public void editRanks(Rank rank) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try{
            c = dataBaseConnector.connect();

            String sql = "UPDATE ranks SET level = ?, experienceRequired = ? WHERE id = ?";

            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, rank.getLevel());
            pstmt.setInt(2, rank.getExperienceRequired());
            pstmt.setInt(3, rank.getId());

            pstmt.executeUpdate(sql);
            c.commit();
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }
    }

    public void deleteRanks(Rank rank) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try{
            c = dataBaseConnector.connect();

            String sql = "DELETE FROM ranks WHERE id = ?";

            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, rank.getId());

            pstmt.executeUpdate(sql);
            c.commit();
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }
    }
}
