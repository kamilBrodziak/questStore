package DAO;

import Model.Level;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LevelDAO {
    private DataBaseConnector dbCon = null;
    public LevelDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<Level> getLevels(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Level> levelList = new ArrayList<>();

        while(rs.next()) {
            Level artifact = new Level(rs.getInt("id"), rs.getInt("id_user"),
                    rs.getInt("level"), rs.getInt("experience"),
                    rs.getInt("money"));
            levelList.add(artifact);
        }

        return levelList;
    }

    public Level getLevel(String tableName, int userID) throws Exception{
        String query = "SELECT * FROM ? WHERE id_user=?";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        queryAttr.add(userID + "");
        ResultSet rs = dbCon.query(query, queryAttr);
        if(rs.next()) {
            return new Level(rs.getInt("id"), rs.getInt("id_user"),
                    rs.getInt("level"), rs.getInt("experience"),
                    rs.getInt("money"));
        }
        return null;
    }

    public void addLevel(Level level) throws Exception{
        String query = "INSERT INTO levels(level, experience, money) VALUES(?::integer, ?::integer, ?::integer);";
        String[] queryAttr = {level.getLevel() + "", level.getExperience() + "", level.getExperience() + ""};

        dbCon.updateSQL(query, queryAttr);
    }

    public void updateLevel(Level level) throws Exception{
        String query = "UPDATE levels SET level=?, experience=?, money=? WHERE id_user=?;";
        String[] queryAttr = {level.getLevel() + "", level.getExperience() + "", level.getMoney() + "", level.getUserID() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteLevel(int id) throws Exception {
        String query = "DELETE levels WHERE id=?;";
        String[] queryAttr = {id + ""};
        dbCon.updateSQL(query, queryAttr);
    }

}
