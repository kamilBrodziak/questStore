package DAO;

import Model.Rank;

import java.sql.*;

public class RanksDAOPostgreSQL implements RanksDAO{

    private DataBaseConnector dataBaseConnector;

    public RanksDAOPostgreSQL(){
        this.dataBaseConnector = new DataBaseConnector();
    }

    public void addRanks(Rank rank) throws SQLException {
        String query = "INSERT INTO ranks (level, experienceRequired) VALUES (?, ?);";
        String[] queryAttr = {Integer.toString(rank.getLevel()), Integer.toString(rank.getExperienceRequired())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void editRanks(Rank rank) throws SQLException{
        String query = "UPDATE ranks SET level = ?, experienceRequired = ? WHERE id = ?;";
        String[] queryAttr = {Integer.toString(rank.getLevel()), Integer.toString(rank.getExperienceRequired()),
                Integer.toString(rank.getId())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public void deleteRanks(Rank rank) throws SQLException{
        String query = "DELETE FROM ranks WHERE id = ?;";
        String[] queryAttr = {Integer.toString(rank.getId())};

        dataBaseConnector.updateSQL(query, queryAttr);
    }

    public Rank createRank(int level, int experienceRequired) throws SQLException{
        String query = "INSERT INTO ranks (level, experienceRequired) VALUES (?, ?);";
        String[] queryAttr = {Integer.toString(level), Integer.toString(experienceRequired)};

        dataBaseConnector.updateSQL(query, queryAttr);

        return new Rank(level, experienceRequired);
    }

    public Rank getRank(int experienceRequired) throws SQLException {
        String query = "SELECT * FROM ranks WHERE experienceRequired >? ORDER BY experienceRequired LIMIT 1;";
        String[] queryAttr = {Integer.toString(experienceRequired)};

        ResultSet rs = dataBaseConnector.query(query, queryAttr);

        if(rs.next()){
            return new Rank(rs.getInt("level"), rs.getInt("experienceRequired"));
        }
        return null;
    }
}
