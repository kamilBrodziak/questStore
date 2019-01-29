package Service;

import DAO.*;
import Model.*;

import java.util.List;

public class AchievementService {
    private AchievementDAO achievementDAO;
    private LevelDAO levelDAO;
    private ComponentUnlockDAO componentUnlockDAO;
    private RanksDAO ranksDAO;

    public AchievementService(DataBaseConnector dbConnector) {
        this.achievementDAO = new AchievementDAO(dbConnector);
        this.levelDAO = new LevelDAO(dbConnector);
        this.componentUnlockDAO = new ComponentUnlockDAO(dbConnector);
        this.ranksDAO = new RanksDAOPostgreSQL();
    }

    public boolean markAchievement(int studentId, int questId) throws Exception{
        componentUnlockDAO.completeComponent("achievements_completed", "id_achievement", studentId, questId);
        return true;
    }

    public boolean approveAchievement(int studentAchievId) throws Exception {
        ComponentsCompleted component = componentUnlockDAO.getComponent("achievements_completed", "id_quest", studentAchievId);
        componentUnlockDAO.approveComponent(studentAchievId, "achievements_completed");
        Achievement achievement = achievementDAO.getAchievement("quests", component.getComponentId());
        Level level = levelDAO.getLevel("levels", component.getStudentId());
        level.setExperience(level.getExperience() + achievement.getExperience());
        level.setMoney(level.getMoney() + achievement.getExperience());
        level.setLevel(ranksDAO.getRank(level.getExperience()).getLevel());
        levelDAO.updateLevel(level);
        return true;
    }

    public List<ComponentJoin> getAchievementToApproveList() throws Exception {
        return componentUnlockDAO.getStudentToApproveList("achievements_completed", "achievements", "id_achievement");
    }

    public List<Artifact> getStudentAchievementList(int studentID) throws Exception {
        return componentUnlockDAO.getStudentApprovedList("achievements_completed", "achievements", "id_achievement", studentID, Achievement.class);
    }

    public List<ComponentsCompleted> getAllAchievementsList() throws Exception {
        return componentUnlockDAO.getComponents("achievements_completed", "id_quest");
    }

    public void addAchievement(String name, String description, int experience, int tier, int creatorId) throws Exception{
        achievementDAO.addAchievement(new Achievement(0, name, description, experience, tier, creatorId, 0));
    }

    public void updateAchievement(int id, String name, String description, int experience, int tier, int modified_by) throws Exception{
        Achievement achievement = achievementDAO.getAchievement("achievements", id);
        achievement.setName(name);
        achievement.setDescription(description);
        achievement.setExperience(experience);
        achievement.setTier(tier);
        achievement.setModifierId(modified_by);
        achievementDAO.updateAchievement(achievement);
    }

    public void deleteAchievement(int id) throws Exception{
        achievementDAO.deleteAchievement(id);
    }
}
