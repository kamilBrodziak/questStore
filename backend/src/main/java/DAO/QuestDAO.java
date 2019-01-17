package DAO;

import Model.Quest;

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
                    rs.getInt("id_creator"), rs.getInt("id_modifier"));
            questList.add(quest);
        }

        return questList;
    }

    public Quest getQuest(String tableName, int id) throws Exception {
        String query = "SELECT * FROM ? WHERE id=?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        queryAttr.add(id + "");
        ResultSet rs = dbCon.query(query, queryAttr);

        if(rs.next()) {
            return new Quest(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("reward"),
                    rs.getInt("id_creator"), rs.getInt("id_modifier"));
        }

        return null;
    }

    public void addQuest(Quest quest) throws Exception{
        String query = "INSERT INTO quests(name, description, reward, id_creator) VALUES(?, ?, ?::integer, ?::integer);";
        String[] queryAttr = {quest.getName(), quest.getDescription(), quest.getReward() + "", quest.getCreatorId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateQuest(Quest quest) throws Exception{
        String query = "UPDATE quests SET name=?, description=?, reward=?, id_modifier=? WHERE id=?;";
        String[] queryAttr = {quest.getName(), quest.getDescription(), quest.getReward() + "",
            quest.getModifierId() + "", quest.getId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteQuest(int id) throws Exception {
        String query = "DELETE quests WHERE id=?;";
        String[] queryAttr = {id + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void completeQuest(int studentId, int questId) throws Exception {
        String query = "INSERT INTO quests_completed(id_student, id_quest) VALUES(?::integer, ?::integer);";
        String[] queryAttr = {studentId + "", questId + ""};
        dbCon.updateSQL(query, queryAttr);
    }
}
