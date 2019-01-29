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
        String[] queryAttr = {tableName};
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Quest> questList = new ArrayList<>();

        while(rs.next()) {
            Quest quest = new Quest(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("reward"),
                    rs.getInt("id_creator"), rs.getInt("id_modifier"), rs.getInt("quest_type"));
            questList.add(quest);
        }

        return questList;
    }

    public Quest getQuest(String tableName, int id) throws Exception {
        String query = "SELECT * FROM ? WHERE id=?;";
        String[] queryAttr = {tableName, Integer.toString(id)};
        ResultSet rs = dbCon.query(query, queryAttr);

        if(rs.next()) {
            return new Quest(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"), rs.getInt("reward"),
                    rs.getInt("id_creator"), rs.getInt("id_modifier"), rs.getInt("quest_type"));
        }

        return null;
    }

    public void addQuest(Quest quest) throws Exception{
        String query = "INSERT INTO quests(name, description, reward, id_creator, quest_type) " +
                "VALUES(?, ?, ?::integer, ?::integer, ?::integer);";
        String[] queryAttr = {quest.getName(), quest.getDescription(), Integer.toString(quest.getReward()),
                Integer.toString(quest.getCreatorId()), Integer.toString(quest.getQuestType())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateQuest(Quest quest) throws Exception{
        String query = "UPDATE quests SET name=?, description=?, reward=?, id_modifier=?, quest_type=? WHERE id=?;";
        String[] queryAttr = {quest.getName(), quest.getDescription(), Integer.toString(quest.getReward()),
            Integer.toString(quest.getModifierId()), Integer.toString(quest.getQuestType()),
                Integer.toString(quest.getId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteQuest(int id) throws Exception {
        String query = "DELETE quests WHERE id=?;";
        String[] queryAttr = {Integer.toString(id)};
        dbCon.updateSQL(query, queryAttr);
    }
}
