package DAO;

import Model.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class artifactDAO {
    private DataBaseConnector dbCon = null;
    public artifactDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<Artifact> getArtifact(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        List<String> queryAttr = new ArrayList<>();
        queryAttr.add(tableName);
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Artifact> artifactList = new ArrayList<>();

        while(rs.next()) {
            Artifact artifact = new Artifact(rs.getInt("id_artifact"), rs.getString("name"),
                    rs.getString("description"), rs.getInt("price"),
                    rs.getInt("creator_id"), rs.getInt("modified_by"));
            artifactList.add(artifact);
        }

        return artifactList;
    }

    public void addArtifact(Artifact artifact) throws Exception{
        String query = "INSERT INTO artifacts(name, description, creator_id) VALUES(?, ?, ?::integer, ?::integer);";
        String[] queryAttr = {artifact.getName(), artifact.getDescription(), artifact.getPrice() + "", artifact.getCreatorId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateArtifact(Artifact artifact) throws Exception{
        String query = "UPDATE artifacts SET name=?, description=?, price=?, modified_by=? WHERE id_artifact=?;";
        String[] queryAttr = {artifact.getName(), artifact.getDescription(), artifact.getPrice() + "",
                artifact.getModifierId() + "", artifact.getId() + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteArtifact(int id) throws Exception {
        String query = "DELETE artifacts WHERE id_artifact=?;";
        String[] queryAttr = {id + ""};
        dbCon.updateSQL(query, queryAttr);
    }

    public void unlockArtifact(int studentId, int artifactId) throws Exception {
        String query = "INSERT INTO artifacts_unlocked(id_student, id_artifact) VALUES(?::integer, ?::integer);";
        String[] queryAttr = {studentId + "", artifactId + ""};
        dbCon.updateSQL(query, queryAttr);
    }
}
