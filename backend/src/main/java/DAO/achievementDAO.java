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
                    rs.getInt("tier"));
            achievementList.add(achievement);
        }

        return achievementList;
    }

    public void addAchievement(Achievement achievement) throws Exception{
        Connection connection = dbCon.connect();

        String query = "INSERT INTO achievements(name, description, experience, tier) VALUES(?, ?, ?::integer, ?::integer);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, achievement.getName());
        pstmt.setString(2, achievement.getDescription());
        pstmt.setString(3, achievement.getExperience() + "");
        pstmt.setString(4, achievement.getTier() + "");

        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void updateAchievement(Achievement achievement) throws Exception{
        Connection connection = dbCon.connect();
        String query = "UPDATE achievements SET name=?, description=?, experience=?, tier=? WHERE id=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, achievement.getName());
        pstmt.setString(2, achievement.getDescription());
        pstmt.setString(3, achievement.getExperience() + "");
        pstmt.setString(4, achievement.getTier() + "");
        pstmt.setString(5, achievement.getId() + "");

        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void deleteAchievement(int id) throws Exception {
        Connection connection = dbCon.connect();
        String query = "DELETE achievements WHERE id=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, id + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void completeAchievement(int studentId, int achievementId) throws Exception {
        Connection connection = dbCon.connect();
        String query = "INSERT INTO achievements_completed(id_student, id_achievement) VALUES(?::integer, ?::integer);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, studentId + "");
        pstmt.setString(2, achievementId + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }
}
