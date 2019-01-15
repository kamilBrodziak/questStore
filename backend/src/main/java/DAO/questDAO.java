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

    public List<Quest> getQuests(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Quest> questList = new ArrayList<>();

        while(rs.next()) {
            Quest quest = new Quest(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("reward"),
                    rs.getInt("creator_id"), rs.getInt("modified_by"));
            questList.add(quest);
        }

        return questList;
    }

    public void addQuest(Quest quest) throws Exception{
        Connection connection = dbCon.connect();

        String query = "INSERT INTO quests(name, description, reward, creator_id) VALUES(?, ?, ?::integer, ?::integer);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, quest.getName());
        pstmt.setString(2, quest.getDescription());
        pstmt.setString(3, quest.getReward() + "");
        pstmt.setString(4, quest.getCreatorId() + "");

        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void updateQuest(Quest quest) throws Exception{
        Connection connection = dbCon.connect();
        String query = "UPDATE quests SET name=?, description=?, reward=?, modified_by=? WHERE id_quest=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, quest.getName());
        pstmt.setString(2, quest.getDescription());
        pstmt.setString(3, quest.getReward() + "");
        pstmt.setString(4, quest.getModifierId() + "");
        pstmt.setString(6, quest.getId() + "");

        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void deleteQuest(int id) throws Exception {
        Connection connection = dbCon.connect();
        String query = "DELETE quests WHERE id_quest=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, id + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }

    public void completeQuest(int studentId, int questId) throws Exception {
        Connection connection = dbCon.connect();
        String query = "INSERT INTO quests_completed(id_student, id_quest) VALUES(?::integer, ?::integer);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, studentId + "");
        pstmt.setString(2, questId + "");
        pstmt.executeUpdate();
        pstmt.close();
        connection.commit();
        connection.close();
    }
}
