package DAO;

import Model.Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RanksDAO {

    private DataBaseConnector dataBaseConnector;

    public RanksDAO(){
        this.dataBaseConnector = new DataBaseConnector();
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

    public Rank createRank(int level, int experienceRequired) throws Exception{
        Connection c = null;
        PreparedStatement pstmt = null;
        try{
            c = dataBaseConnector.connect();

            String sql = "INSERT INTO ranks (level, experienceRequired) VALUES (?, ?)";
            pstmt = c.prepareStatement(sql);

            pstmt.setInt(1, level);
            pstmt.setInt(2, experienceRequired);

            pstmt.executeUpdate(sql);

            Rank rank =  new Rank(level, experienceRequired);

            return rank;
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }finally{
            pstmt.close();
            c.close();
        }
        return null;
    }

    public Rank getRank(int experienceRequired) throws Exception {
        Connection c = null;
        PreparedStatement pstmt = null;
        c = dataBaseConnector.connect();

        String sql = "SELECT * FROM ranks WHERE experienceRequired >? ORDER BY experienceRequired LIMIT 1;";
        pstmt = c.prepareStatement(sql);

        pstmt.setInt(1, experienceRequired);

        ResultSet rs = pstmt.executeQuery();

        Rank rank =  new Rank(rs.getInt("id"),rs.getInt("level"), rs.getInt("experienceRequired"));
        pstmt.close();
        c.close();
        return rank;
    }
}
