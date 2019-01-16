package DAO;



import Model.Achievement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class achievementDAO {
    private DataBaseConnector dbCon = null;

    public achievementDAO(DataBaseConnector dbCon) {
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
                    rs.getInt("tier"), rs.getInt("creator_id"), rs.getInt("modified_by"));
            achievementList.add(achievement);
        }

        return achievementList;
    }

    public void addAchievement(Achievement achievement) throws Exception{
        String query = "INSERT INTO achievements(name, description, experience, tier, creator_id) VALUES(?, ?," +
                " ?::integer, ?::integer, ?::integer);";
        String[] queryAttr = {achievement.getName(), achievement.getDescription(), achievement.getExperience() + "",
            achievement.getTier() + "", achievement.getCreatorId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateAchievement(Achievement achievement, int modifierId) throws Exception{
        String query = "UPDATE achievements SET name=?, description=?, experience=?, tier=?, modified_by=? WHERE id=?;";
        String[] queryAttr = {achievement.getName(), achievement.getDescription(), achievement.getExperience() + "",
            achievement.getTier() + "", modifierId + "", achievement.getId() + ""};
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
