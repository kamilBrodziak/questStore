package DAO;

import Model.Artifact;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO {
    private DataBaseConnector dbCon = null;
    public ArtifactDAO(DataBaseConnector dbCon) {
        this.dbCon = dbCon;
    }

    public List<Artifact> getArtifactList(String tableName) throws Exception {
        String query = "SELECT * FROM ?;";
        String[] queryAttr = {tableName};
        ResultSet rs = dbCon.query(query, queryAttr);
        List<Artifact> artifactList = new ArrayList<>();

        while(rs.next()) {
            Artifact artifact = new Artifact(rs.getInt("id"), rs.getString("name"),
                    rs.getString("description"), rs.getInt("price"),
                    rs.getInt("id_creator"), rs.getInt("id_modifier"));
            artifactList.add(artifact);
        }

        return artifactList;
    }

    public Artifact getArtifact(String tableName, int artifactId) throws Exception{
        String query = "SELECT * FROM ? WHERE id=?";
        String[] queryAttr = {tableName, Integer.toString(artifactId)};
        ResultSet rs = dbCon.query(query, queryAttr);
        if(rs.next()) {
            return new Artifact(rs.getInt("id"), rs.getString("name"),
                    rs.getString("description"), rs.getInt("price"),
                    rs.getInt("id_creator"), rs.getInt("id_modifier"));
        }
        return null;
    }

    public void addArtifact(Artifact artifact) throws Exception{
        String query = "INSERT INTO artifacts(name, description, id_creator) VALUES(?, ?, ?::integer, ?::integer);";
        String[] queryAttr = {artifact.getName(), artifact.getDescription(),
                Integer.toString(artifact.getPrice()), Integer.toString(artifact.getCreatorId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void updateArtifact(Artifact artifact) throws Exception{
        String query = "UPDATE artifacts SET name=?, description=?, price=?, id_modifier=? WHERE id=?;";
        String[] queryAttr = {artifact.getName(), artifact.getDescription(), Integer.toString(artifact.getPrice()),
                Integer.toString(artifact.getModifierId()), Integer.toString(artifact.getId())};
        dbCon.updateSQL(query, queryAttr);
    }

    public void deleteArtifact(int id) throws Exception {
        String query = "DELETE artifacts WHERE id_artifact=?;";
        String[] queryAttr = {Integer.toString(id)};
        dbCon.updateSQL(query, queryAttr);
    }
}
