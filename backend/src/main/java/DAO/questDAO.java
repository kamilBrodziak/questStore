package DAO;



import Model.Achievement;
import Model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class questDAO {
    private DataBaseConnector dbCon = null;

    public questDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<Quest> getQuests() throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add("quests");
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Quest> questList = new ArrayList<>();

        while(rs.next()) {
            Quest quest = new Quest(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("reward"),
                    rs.getInt("creator_id", rs.getInt("modified_by")));
            questList.add(quest);
        }

        return questList;
    }

    public void addQuest(Achievement achievement) throws Exception{
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

    public void updateQuest(Achievement achievement) throws Exception{
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

    public void deleteQuest(int id) throws Exception {
        Connection connection = dbCon.connect();
        String query = "DELETE achievements WHERE id=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, id + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }
}
