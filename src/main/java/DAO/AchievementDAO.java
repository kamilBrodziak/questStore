package DAO;

import Model.Achievement;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AchievementDAO {
    private DataBaseConnector dbCon = null;

    public AchievementDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<Achievement> getAchievements(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        String[] queryAttr = {tableName};
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Achievement> achievementList = new ArrayList<>();

        while (rs.next()) {
            Achievement achievement = new Achievement(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("experience"),
                    rs.getInt("tier"), rs.getInt("id_creator"), rs.getInt("id_modifier"));
            achievementList.add(achievement);
        }

        return achievementList;
    }

    public Achievement getAchievement(String tableName, int id) throws Exception {
        String query = "SELECT * FROM ? WHERE id=?;";
        String[] queryAttr = {tableName, Integer.toString(id)};
        ResultSet rs = dbCon.query(query, queryAttr);

        if (rs.next()) {
            return new Achievement(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("experience"),
                    rs.getInt("tier"), rs.getInt("id_creator"), rs.getInt("id_modifier"));
        }

        return null;
    }

    public void addAchievement(Achievement achievement) throws Exception {
        String query = "INSERT INTO achievements(name, description, experience, tier, id_creator) VALUES(?, ?," +
                " ?::integer, ?::integer, ?::integer);";
        String[] queryAttr = {achievement.getName(), achievement.getDescription(),
                Integer.toString(achievement.getExperience()),
                Integer.toString(achievement.getTier()), Integer.toString(achievement.getCreatorId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateAchievement(Achievement achievement) throws Exception {
        String query = "UPDATE achievements SET name=?, description=?, experience=?, tier=?, id_modifier=? WHERE id=?;";
        String[] queryAttr = {achievement.getName(), achievement.getDescription(),
                Integer.toString(achievement.getExperience()), Integer.toString(achievement.getTier()),
                Integer.toString(achievement.getModifierId()), Integer.toString(achievement.getId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteAchievement(int id) throws Exception {
        String query = "DELETE achievements WHERE id=?;";
        String[] queryAttr = {Integer.toString(id)};
        dbCon.updateSQL(query, queryAttr);
    }
}