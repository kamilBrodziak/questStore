package Service;

import DAO.ArtifactDAO;
import DAO.ComponentUnlockDAO;
import DAO.DataBaseConnector;
import DAO.LevelDAO;
import Model.Artifact;
import Model.ComponentJoin;
import Model.ComponentsCompleted;
import Model.Level;

import java.util.List;

public class ArtifactService {
    private ArtifactDAO artDAO;
    private LevelDAO levelDAO;
    private ComponentUnlockDAO componentUnlockDAO;

    public ArtifactService(DataBaseConnector dbConnector) {
        this.artDAO = new ArtifactDAO(dbConnector);
        this.levelDAO = new LevelDAO(dbConnector);
        this.componentUnlockDAO = new ComponentUnlockDAO(dbConnector);

    }

    public boolean buyArtifact(int studentId, int artifactId) throws Exception{
        Level level = levelDAO.getLevel("levels", studentId);
        Artifact artifact = artDAO.getArtifact("artifacts", artifactId);

        if(level.getMoney() >= artifact.getPrice()) {
            level.setMoney(level.getMoney() - artifact.getPrice());
            levelDAO.updateLevel(level);
            componentUnlockDAO.completeComponent("artifacts_unlocked", "id_artifact", studentId, artifactId);
            return true;
        } else {
            return false;
        }
    }

    public boolean approveArtifact(int id) throws Exception{
        ComponentsCompleted component = componentUnlockDAO.getComponent("artifacts_unlocked",
                "id_artifact", id);
        component.setApproved(true);
        componentUnlockDAO.approveComponent(component, "artifacts");
        return true;
    }

    public List<ComponentJoin> getArtifactsToApproveView() throws Exception {
        return componentUnlockDAO.getStudentToApproveList("artifacts_unlocked", "artifacts", "id_artifact");
    }

    public List<Artifact> getStudentArtifactsList(int studentID) throws Exception {
        return componentUnlockDAO.getStudentApprovedList("artifacts_unclocked", "artifacts", "id_artifact", studentID, Artifact.class);
    }

    public List<ComponentsCompleted> getAllArtifactsView() throws Exception {
        return componentUnlockDAO.getComponents("artifacts_unlocked", "id_artifact");
    }

    public void addArtifact(String name, String description, int price, int creatorId) throws Exception{
        artDAO.addArtifact(new Artifact(0, name, description, price, creatorId, 0));
    }

    public void updateArtifact(String name, String description, int price, int creatorId, int modified_by) throws Exception{
        artDAO.updateArtifact(new Artifact(0, name, description, price, creatorId, modified_by));
    }

    public void deleteArtifact(int id) throws Exception{
        artDAO.deleteArtifact(id);
    }
}
