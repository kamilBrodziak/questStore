package DAO;



import Model.Achievement;
import Model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO {
    private DataBaseConnector dbCon = null;

    public QuestDAO(DataBaseConnector dbCon) {
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
        String query = "INSERT INTO quests(name, description, reward, creator_id) VALUES(?, ?, ?::integer, ?::integer);";
        String[] queryAttr = {quest.getName(), quest.getDescription(), quest.getReward() + "", quest.getCreatorId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateQuest(Quest quest) throws Exception{
        String query = "UPDATE quests SET name=?, description=?, reward=?, modified_by=? WHERE id_quest=?;";
        String[] queryAttr = {quest.getName(), quest.getDescription(), quest.getReward() + "",
            quest.getModifierId() + "", quest.getId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteQuest(int id) throws Exception {
        String query = "DELETE quests WHERE id_quest=?;";
        String[] queryAttr = {id + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void completeQuest(int studentId, int questId) throws Exception {
        String query = "INSERT INTO quests_completed(id_student, id_quest) VALUES(?::integer, ?::integer);";
        String[] queryAttr = {studentId + "", questId + ""};
        dbCon.updateSQL(query, queryAttr);
    }
}
