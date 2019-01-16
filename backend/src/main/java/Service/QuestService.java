package Service;

import DAO.*;
import Model.*;

import java.util.List;

public class QuestService {
    private QuestDAO questDAO;
    private LevelDAO levelDAO;
    private ComponentUnlockDAO componentUnlockDAO;
    private RanksDAO ranksDAO;

    public QuestService(DataBaseConnector dbConnector) {
        this.questDAO = new QuestDAO(dbConnector);
        this.levelDAO = new LevelDAO(dbConnector);
        this.componentUnlockDAO = new ComponentUnlockDAO(dbConnector);
        this.ranksDAO = new RanksDAO();
    }

    public boolean markQuest(int studentId, int questId) throws Exception{
        componentUnlockDAO.completeComponent("quests_completed", "id_quest", studentId, questId);
        return true;
    }

    public boolean approveQuest(int studentQuestId) throws Exception {
        ComponentsCompleted component = componentUnlockDAO.getComponent("quests_completed", "id_quest", studentQuestId);
        componentUnlockDAO.approveComponent(studentQuestId, "quests_completed");
        Quest quest = questDAO.getQuest("quests", component.getComponentId());
        Level level = levelDAO.getLevel("levels", component.getStudentId());
        level.setExperience(level.getExperience() + quest.getReward());
        level.setMoney(level.getMoney() + quest.getReward());
        level.setLevel(ranksDAO.getRank(level.getExperience()).getLevel());
        levelDAO.updateLevel(level);
        return true;
    }

    public List<ComponentJoin> getQuestsToApproveView() throws Exception {
        return componentUnlockDAO.getStudentToApproveList("quests_completed", "quests", "id_quest");
    }

    public List<Artifact> getStudentQuestList(int studentID) throws Exception {
        return componentUnlockDAO.getStudentApprovedList("quests_completed", "quests", "id_quest", studentID, Quest.class);
    }

    public List<ComponentsCompleted> getAllQuestsView() throws Exception {
        return componentUnlockDAO.getComponents("quests_completed", "id_quest");
    }

    public void addQuest(String name, String description, int reward, int creatorId) throws Exception{
        questDAO.addQuest(new Quest(0, name, description, reward, creatorId, 0));
    }

    public void updateQuest(int id, String name, String description, int reward, int modified_by) throws Exception{
        Quest quest = questDAO.getQuest("quests", id);
        quest.setName(name);
        quest.setDescription(description);
        quest.setReward(reward);
        quest.setModifierId(modified_by);
        questDAO.updateQuest(quest);
    }

    public void deleteQuest(int id) throws Exception{
        questDAO.deleteQuest(id);
    }
}
