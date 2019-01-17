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
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Achievement> achievementList = new ArrayList<>();

        while(rs.next()) {
            Achievement achievement = new Achievement(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("experience"),
                    rs.getInt("tier"), rs.getInt("id_creator"), rs.getInt("id_modifier"));
            achievementList.add(achievement);
        }

        return achievementList;
    }

    public Achievement getAchievement(String tableName, int id) throws Exception {
        String query = "SELECT * FROM ? WHERE id=?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        queryAttr.add(id + "");
        ResultSet rs = dbCon.query(query, queryAttr);

        if(rs.next()) {
            return new Achievement(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("experience"),
                    rs.getInt("tier"), rs.getInt("id_creator"), rs.getInt("id_modifier"));
        }

        return null;
    }

    public void addAchievement(Achievement achievement) throws Exception{
        String query = "INSERT INTO achievements(name, description, experience, tier, id_creator) VALUES(?, ?," +
                " ?::integer, ?::integer, ?::integer);";
        String[] queryAttr = {achievement.getName(), achievement.getDescription(), achievement.getExperience() + "",
            achievement.getTier() + "", achievement.getCreatorId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateAchievement(Achievement achievement) throws Exception{
        String query = "UPDATE achievements SET name=?, description=?, experience=?, tier=?, id_modifier=? WHERE id=?;";
        String[] queryAttr = {achievement.getName(), achievement.getDescription(), achievement.getExperience() + "",
            achievement.getTier() + "", achievement.getModifierId() + "", achievement.getId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteAchievement(int id) throws Exception {
        String query = "DELETE achievements WHERE id=?;";
        String[] queryAttr = {id + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void completeAchievement(int studentId, int achievementId) throws Exception {
        String query = "INSERT INTO achievements_completed(id_student, id_achievement) VALUES(?::integer, ?::integer);";
        String[] queryAttr = {studentId + "", achievementId + ""};
        dbCon.updateSQL(query, queryAttr);
    }
}
